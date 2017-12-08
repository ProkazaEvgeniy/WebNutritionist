package net.www.webnutritionist.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import net.www.webnutritionist.component.DataBuilder;
import net.www.webnutritionist.entity.Order;
import net.www.webnutritionist.entity.OrderItem;
import net.www.webnutritionist.entity.Product;
import net.www.webnutritionist.entity.Profile;
import net.www.webnutritionist.exception.AccessDeniedException;
import net.www.webnutritionist.exception.InternalServerErrorException;
import net.www.webnutritionist.exception.ResourceNotFoundException;
import net.www.webnutritionist.form.ProductForm;
import net.www.webnutritionist.model.CurrentProfile;
import net.www.webnutritionist.model.ShoppingCart;
import net.www.webnutritionist.model.ShoppingCartItem;
import net.www.webnutritionist.repository.storage.OrderItemRepository;
import net.www.webnutritionist.repository.storage.OrderRepository;
import net.www.webnutritionist.repository.storage.ProductRepository;
import net.www.webnutritionist.service.CookieService;
import net.www.webnutritionist.service.NotificationManagerService;
import net.www.webnutritionist.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CookieService cookieService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private NotificationManagerService notificationManagerService;
	
	@Autowired
	protected DataBuilder dataBuilder;
	
	@Value("${application.host}")
	private String appHost;

	@Override
	@Transactional
	public void addProductToShoppingCart(ProductForm productForm, ShoppingCart shoppingCart) {
		Product product = productRepository.findOne(productForm.getId());
		if(product == null) {
			throw new InternalServerErrorException("Product not found by id=" + productForm.getId());
		}
		shoppingCart.addProduct(product, productForm.getCount());
	}
	
	@Override
	@Transactional
	public String serializeShoppingCart(ShoppingCart shoppingCart) {
		return cookieService.createShoppingCartCookie(shoppingCart.getItems());
	}
	
	@Override
	public void removeProductFromShoppingCart(ProductForm form, ShoppingCart shoppingCart) {
		shoppingCart.removeProduct(form.getId(), form.getCount());
	}

	@Override
	@Transactional
	public ShoppingCart deserializeShoppingCart(String cookieValue) {
		List<ProductForm> products = cookieService.parseShoppingCartCookie(cookieValue);
		ShoppingCart shoppingCart = new ShoppingCart();
		for(ProductForm productForm : products) {
			try {
				addProductToShoppingCart(productForm, shoppingCart);
			} catch (RuntimeException e) {
				LOGGER.error("Can't add product to ShoppingCart: productForm=" + productForm, e);
			}
		}
		return shoppingCart.getItems().isEmpty() ? null : shoppingCart;
	}
	
	@Override
	@Transactional
	public long makeOrder(ShoppingCart shoppingCart, final Profile profile) {
		if (shoppingCart == null || shoppingCart.getItems().isEmpty()) {
			throw new InternalServerErrorException("shoppingCart is null or empty");
		}
		final Order order = new Order(profile, new Timestamp(System.currentTimeMillis()));
		orderRepository.save(order);
		List<OrderItem> arrayList = new ArrayList<>();
		order.setOrderItems(arrayList);
		for (ShoppingCartItem item : shoppingCart.getItems()) {
			OrderItem orderItem = new OrderItem(order, item.getProduct(), item.getCount());
			orderItemRepository.save(orderItem);
			order.getOrderItems().add(orderItem);
		}
		final String orderLink = dataBuilder.buildNewOrderCreatedNotificationMessage(appHost, order);
		sendOrderMessageIfTransactionSuccess(profile, orderLink);
		return order.getId();
	}
	
	protected void sendOrderMessageIfTransactionSuccess(final Profile profile, final String orderLink) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				notificationManagerService.sendOrderMessage(profile, orderLink);
			}
		});
	}

	@Override
	public Order findOrderById(long id, CurrentProfile currentProfile) {
		Order order = orderRepository.findById(id);
		if (order == null) {
			throw new ResourceNotFoundException("Order not found by id: " + id);
		}
		if (!order.getProfile().getId().equals(currentProfile.getId())) {
			throw new AccessDeniedException("Account with id=" + currentProfile.getId() + " is not owner for order with id=" + id);
		}
		order.setOrderItems(orderItemRepository.findByOrderId(id));
		return order;
	}
	
	@Override
	public Order findOrderById(Long id) {
		Order order = orderRepository.findById(id);
		if (order == null) {
			throw new ResourceNotFoundException("Order not found by id: " + id);
		}
		order.setOrderItems(orderItemRepository.findByOrderId(id));
		return order;
	}
	
	@Override
	public List<Order> listOrderByProfileId(Long id) {
		return orderRepository.findByProfileId(id);
	}

	@Override
	public Page<Order> pageMyOrders(CurrentProfile currentProfile, int page, int limit) {
		return orderRepository.findByProfileId(currentProfile.getId(), new PageRequest(page - 1, limit));
	}

	@Override
	public int countMyOrders(CurrentProfile currentProfile) {
		return orderRepository.countByProfileId(currentProfile.getId());
	}
	
}
