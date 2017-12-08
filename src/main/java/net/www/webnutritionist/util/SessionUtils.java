package net.www.webnutritionist.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.www.webnutritionist.Constants;
import net.www.webnutritionist.model.CurrentProfile;
import net.www.webnutritionist.model.ShoppingCart;


public class SessionUtils {
	public static ShoppingCart getCurrentShoppingCart(HttpServletRequest req) {
		return (ShoppingCart) req.getSession().getAttribute(Constants.CURRENT_SHOPPING_CART);
	}

	public static boolean isCurrentShoppingCartCreated(HttpServletRequest req) {
		return req.getSession().getAttribute(Constants.CURRENT_SHOPPING_CART) != null;
	}

	public static void setCurrentShoppingCart(HttpServletRequest req, ShoppingCart shoppingCart) {
		req.getSession().setAttribute(Constants.CURRENT_SHOPPING_CART, shoppingCart);
	}

	public static void clearCurrentShoppingCart(HttpServletRequest req, HttpServletResponse resp) {
		req.getSession().removeAttribute(Constants.CURRENT_SHOPPING_CART);
		WebUtils.setCookie(Constants.Cookie.SHOPPING_CART.getName(), null, 0, resp);
	}

	public static Cookie findShoppingCartCookie(HttpServletRequest req) {
		return WebUtils.findCookie(req, Constants.Cookie.SHOPPING_CART.getName());
	}

	public static void updateCurrentShoppingCartCookie(String cookieValue, HttpServletResponse resp) {
		WebUtils.setCookie(Constants.Cookie.SHOPPING_CART.getName(), cookieValue,
				Constants.Cookie.SHOPPING_CART.getTtl(), resp);
	}

	public static CurrentProfile getCurrentProfile(HttpServletRequest req) {
		return (CurrentProfile) req.getSession().getAttribute(Constants.USER);
	}

	public static void setCurrentProfile(HttpServletRequest req, CurrentProfile currentAccount) {
		req.getSession().setAttribute(Constants.USER, currentAccount);
	}

	public static boolean isCurrentProfileCreated(HttpServletRequest req) {
		return getCurrentProfile(req) != null;
	}

	private SessionUtils() {
	}
}
