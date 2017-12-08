package net.www.webnutritionist.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "order_item")
public class OrderItem extends AbstractEntity<Long> {
	private static final long serialVersionUID = -365373848626193474L;

	@Id
	@SequenceGenerator(name="ORDER_ITEM_ID_GENERATOR", sequenceName="seq_order_item", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ORDER_ITEM_ID_GENERATOR")
	private Long id;
		
	private Integer count;
	
	@ManyToOne
	@JoinColumn(name="id_order")
	private Order order;
		
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_product", nullable = false)
	private Product product;

	public OrderItem() {
		super();
	}

	public OrderItem(Order order, Product product, Integer count) {
		super();
		this.order = order;
		this.product = product;
		this.count = count;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return String.format("OrderItem [id=%s, order.id=%s, product=%s, count=%s]", getId(), order.getId(), product, count);
	}
}
