package net.www.webnutritionist.form;

import javax.persistence.Column;

public class CategoryForm {

	@Column
	private String name;
	
	@Column(name = "product_count")
	private String productCount;
	
	@Column
	private String url;

	public CategoryForm() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductCount() {
		return productCount;
	}

	public void setProductCount(String productCount) {
		this.productCount = productCount;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
