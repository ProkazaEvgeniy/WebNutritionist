package net.www.webnutritionist.controller;

import static net.www.webnutritionist.Constants.CURRENT_SHOPPING_CART;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.google.gson.JsonObject;

import net.www.webnutritionist.Constants;
import net.www.webnutritionist.entity.Order;
import net.www.webnutritionist.entity.Profile;
import net.www.webnutritionist.form.ProductForm;
import net.www.webnutritionist.model.CurrentProfile;
import net.www.webnutritionist.model.ShoppingCart;
import net.www.webnutritionist.service.EditProfileService;
import net.www.webnutritionist.service.OrderService;
import net.www.webnutritionist.util.SecurityUtil;
import net.www.webnutritionist.util.SessionUtils;

@Controller
@SessionAttributes({CURRENT_SHOPPING_CART})
public class ShoppingCartController {
	private static final String CURRENT_MESSAGE = "CURRENT_MESSAGE";
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private EditProfileService editProfileService;
	
	/*@Value("${messages.order.created.message}")
	private String orderCreatedMessage;*/
	
	@ModelAttribute(CURRENT_SHOPPING_CART)
    public ShoppingCart getCurrentSoppingCart() {
        return new ShoppingCart();

    }
	
	@RequestMapping(value = "/add-product", method = RequestMethod.POST)
	public @ResponseBody String addProduct(
			@ModelAttribute(CURRENT_SHOPPING_CART) ShoppingCart shoppingCart, 
			@ModelAttribute ProductForm productForm,
			HttpServletResponse resp) {
		orderService.addProductToShoppingCart(productForm, shoppingCart);
		String cookieValue = orderService.serializeShoppingCart(shoppingCart);
		SessionUtils.updateCurrentShoppingCartCookie(cookieValue, resp);
		return getJsonObject(shoppingCart);
	}
	
	@RequestMapping(value = "/remove-product", method = RequestMethod.POST)
	public @ResponseBody String removeProduct(
			@ModelAttribute(CURRENT_SHOPPING_CART) ShoppingCart shoppingCart, 
			@ModelAttribute ProductForm productForm,
			HttpServletRequest req, 
			HttpServletResponse resp){
		orderService.removeProductFromShoppingCart(productForm, shoppingCart);
		if (shoppingCart.getItems().isEmpty()) {
			SessionUtils.clearCurrentShoppingCart(req, resp);
		} else {
			String cookieValue = orderService.serializeShoppingCart(shoppingCart);
			SessionUtils.updateCurrentShoppingCartCookie(cookieValue, resp);
		}
		return getJsonObject(shoppingCart);
	}
	
	private String getJsonObject(ShoppingCart shoppingCart){
		JsonObject cardStatistics = new JsonObject();
		cardStatistics.addProperty("totalCount", shoppingCart.getTotalCount());
		cardStatistics.addProperty("totalCost", shoppingCart.getTotalCost());
		return cardStatistics.toString();
	}
	
	/*@RequestMapping(value = "/my-orders", method = RequestMethod.GET)
	public String myOrders(
			Model model, 
			HttpServletRequest req){
		CurrentProfile currentProfile = SecurityUtil.getCurrentProfile();
		Page<Order> page = orderService.listMyOrders(currentProfile, 1, Constants.ORDERS_PER_PAGE);
		model.addAttribute("orders", page.getContent());
		model.addAttribute("page", page);
		return "my-orders";
	}
	
	@RequestMapping(value = "/fragment/more/my-orders", method = RequestMethod.GET)
	public String myOrdersMore(
			Model model, 
			HttpServletRequest req){
		CurrentProfile currentProfile = SecurityUtil.getCurrentProfile();
		Page<Order> page = orderService.listMyOrders(currentProfile, getPage(req), Constants.ORDERS_PER_PAGE);
		model.addAttribute("orders", page.getContent());
		model.addAttribute("page", page);
		return "my-orders-tbody";
	}*/
	
	@RequestMapping(value = "/my-orders", method = RequestMethod.GET)
	public String myOrders(
			Model model, 
			HttpServletRequest req){
		CurrentProfile currentProfile = SecurityUtil.getCurrentProfile();
//		Page<Order> page = orderService.pageMyOrders(currentProfile, 1, Constants.ORDERS_PER_PAGE);
		List<Order> page = orderService.listOrderByProfileId(currentProfile.getId());
		model.addAttribute("orders", page);
		int orderCount = orderService.countMyOrders(currentProfile);
		model.addAttribute("pageCount", getPageCount(orderCount, Constants.ORDERS_PER_PAGE));
		return "my-orders";
	}
	
	@RequestMapping(value = "/fragment/more/my-orders", method = RequestMethod.GET)
	public String myOrdersMore(
			Model model, 
			HttpServletRequest req){
		CurrentProfile currentProfile = SecurityUtil.getCurrentProfile();
		Page<Order> page = orderService.pageMyOrders(currentProfile, getPage(req), Constants.ORDERS_PER_PAGE);
		model.addAttribute("orders", page.getContent());
		return "my-orders-tbody";
	}
	
	public final int getPage(HttpServletRequest request) {
		try {
			return Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
			return 1;
		}
	}
	
	public final int getPageCount(int totalCount, int itemsPerPage) {
		int res = totalCount / itemsPerPage;
		if(res * itemsPerPage != totalCount) {
			res++;
		}
		return res;
	}
	
	@RequestMapping(value = "/shopping-cart", method = RequestMethod.GET)
	public String getShoppingCart() {
		return gotoShoppingCartJSP();
	}

	private String gotoShoppingCartJSP() {
		return "shopping-cart";
	}
	
	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public String getMakeOrder(
			@ModelAttribute(CURRENT_SHOPPING_CART) ShoppingCart shoppingCart, 
			SessionStatus st, 
			HttpServletRequest req, 
			Model model) {
		String message = (String) req.getSession().getAttribute(CURRENT_MESSAGE);
		req.getSession().removeAttribute(CURRENT_MESSAGE);
		model.addAttribute(CURRENT_MESSAGE, message);
		CurrentProfile currentProfile = SecurityUtil.getCurrentProfile();
		Order order = orderService.findOrderById(Long.parseLong(req.getParameter("id")), currentProfile);
		model.addAttribute("order", order);
		st.setComplete();
		return "order";
	}
	
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public String setMakeOrder(
				@ModelAttribute(CURRENT_SHOPPING_CART) ShoppingCart shoppingCart,
				HttpServletRequest req, 
				HttpServletResponse resp,
				Model model) {
		if(SessionUtils.isCurrentShoppingCartCreated(req)) {
			CurrentProfile currentProfile = SecurityUtil.getCurrentProfile();
			Profile profile = editProfileService.findProfileById(currentProfile);
			long idOrder = orderService.makeOrder(shoppingCart, profile);
			SessionUtils.clearCurrentShoppingCart(req, resp);
			req.getSession().setAttribute(CURRENT_MESSAGE, "Order created successfully. Please wait for our reply.");
			return "redirect:/order?id=" + idOrder;
		} else {
			return "redirect:/products";
		}
	}
}
