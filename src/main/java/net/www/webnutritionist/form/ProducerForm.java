package net.www.webnutritionist.form;

import javax.persistence.Column;

public class ProducerForm {

	private String name;
	
	@Column(name = "product_count")
	private String productCount;

	public ProducerForm() {
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
	
}
