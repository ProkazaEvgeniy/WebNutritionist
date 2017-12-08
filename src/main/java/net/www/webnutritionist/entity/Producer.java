package net.www.webnutritionist.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "producer")
public class Producer extends AbstractEntity<Integer> {
	private static final long serialVersionUID = -4967160259057526492L;

	@Id
	@SequenceGenerator(name="PRODUCER_ID_GENERATOR", sequenceName="seq_producer", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PRODUCER_ID_GENERATOR")
	private Integer id;
	
	@Column
	private String name;
	
	@Column(name = "product_count")
	private Integer productCount;
	
	@OneToMany(mappedBy="producer", fetch=FetchType.EAGER)
	@Transient
	private List<Product> products;

	public Producer() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}
	
	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Product addProduct(Product product) {
		getProducts().add(product);
		product.setProducer(this);
		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setProducer(null);
		return product;
	}
	
	
}
