package net.www.webnutritionist;

public final class Constants {

	public static final String USER = "USER";
	
	public static final String ADMIN = "ADMIN";

	public static final String[] EMPTY_ARRAY = {};
	
	public static final String CURRENT_SHOPPING_CART = "CURRENT_SHOPPING_CART";
	
	public static final int MAX_PRODUCT_COUNT_PER_SHOPPING_CART = 10;
	
	public static final int MAX_PRODUCTS_PER_SHOPPING_CART = 20;
	
	public static final int ORDERS_PER_PAGE = 5;
	
	public static final String PROFILE_ACTIONS_HISTORY = "PROFILE_ACTIONS_HISTORY";
	
	public static final int MAX_PRODUCTS_PER_HTML_PAGE = 12;
	
	public static final String CATEGORY_LIST = "CATEGORY_LIST";

	public static final String PRODUCER_LIST = "PRODUCER_LIST";
	
	public static final String CURRENT_PROFILE = "CURRENT_PROFILE";
	
	public static final String SUCCESS_REDIRECT_URL_AFTER_SIGNIN = "SUCCESS_REDIRECT_URL_AFTER_SIGNIN";
	
	public static final String CURRENT_REQUEST_URL = "CURRENT_REQUEST_URL";
	
	public static final String FILE_PATH_JPEG = "D:\\dev-study_lesson\\practic\\webnutritionist\\src\\main\\webapp\\media\\vzhuh-captcha\\cat-1.jpg";

	public static final String FILE_PATH_NEW = "D:\\dev-study_lesson\\practic\\webnutritionist\\src\\main\\webapp\\media\\vzhuh-captcha-new\\res";
	
	public static final String MIMETYPE_JPEG = "image/jpeg";
	
	public static final String FORMAT_JPEG = "jpeg";
	
	public enum Cookie {
		//1 year ttl
		SHOPPING_CART("iSCC", 60 * 60 * 24 * 365);

		private final String name;
		private final int ttl;

		private Cookie(String name, int ttl) {
			this.name = name;
			this.ttl = ttl;
		}

		public String getName() {
			return name;
		}

		public int getTtl() {
			return ttl;
		}
	}
	
	public static enum UIImageType {

		AVATARS(110, 110, 400, 400),
		PRODUCT(110, 110, 400, 400);

		private final int smallWidth;
		private final int smallHeight;
		private final int largeWidth;
		private final int largeHeight;
		
		private UIImageType(int smallWidth, int smallHeight, int largeWidth, int largeHeight) {
			this.smallWidth = smallWidth;
			this.smallHeight = smallHeight;
			this.largeWidth = largeWidth;
			this.largeHeight = largeHeight;
		}
		public String getFolderName() {
			return name().toLowerCase();
		}
		public int getSmallWidth() {
			return smallWidth;
		}
		public int getSmallHeight() {
			return smallHeight;
		}
		public int getLargeWidth() {
			return largeWidth;
		}
		public int getLargeHeight() {
			return largeHeight;
		}
	}
	
	public static class UI {
		public static final int MAX_PROFILES_PER_PAGE = 4;
		public static final int MAX_PRODUCTS_PER_PAGE = 4;
		public static final int ORDERS_PER_PAGE = 5;
	}
}
