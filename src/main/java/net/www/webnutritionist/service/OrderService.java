package net.www.webnutritionist.service;

import java.util.List;

import org.springframework.data.domain.Page;

import net.www.webnutritionist.entity.Order;
import net.www.webnutritionist.entity.Profile;
import net.www.webnutritionist.form.ProductForm;
import net.www.webnutritionist.model.CurrentProfile;
import net.www.webnutritionist.model.ShoppingCart;

public interface OrderService {

	void addProductToShoppingCart(ProductForm productForm, ShoppingCart shoppingCart);
	
	String serializeShoppingCart(ShoppingCart shoppingCart);
	
	ShoppingCart deserializeShoppingCart(String string);
	
	void removeProductFromShoppingCart(ProductForm productForm, ShoppingCart shoppingCart);
	
	long makeOrder(ShoppingCart shoppingCart, Profile profile);

	Order findOrderById(long id, CurrentProfile currentProfile);
	
	Order findOrderById(Long id);

	Page<Order> pageMyOrders(CurrentProfile currentProfile, int page, int limit);

	int countMyOrders(CurrentProfile currentProfile);
	
	List<Order> listOrderByProfileId(Long id);
}
