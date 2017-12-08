package net.www.webnutritionist.service;

import java.util.Collection;
import java.util.List;

import net.www.webnutritionist.form.ProductForm;
import net.www.webnutritionist.model.ShoppingCartItem;

public interface CookieService {

	String createShoppingCartCookie(Collection<ShoppingCartItem> items);
	
	List<ProductForm> parseShoppingCartCookie(String cookieValue);
}
