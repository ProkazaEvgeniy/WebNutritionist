package net.www.webnutritionist.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "\"order\"")
public class Order extends AbstractEntity<Long> {
	private static final long serialVersionUID = 3026083684140455633L;

	@Id
	@SequenceGenerator(name="ORDER_ID_GENERATOR", sequenceName="seq_order", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ORDER_ID_GENERATOR")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="id_profile")
	private Profile profile;
	
	@Transient
	@OneToMany(mappedBy="order", fetch=FetchType.EAGER)
	private List<OrderItem> orderItems;
	
	private Timestamp created;

	public Order() {

	}

	public Order(Profile profile, Timestamp created) {
		super();
		this.profile = profile;
		this.created = created;
	}
	/*
	public Order(Long id, Timestamp created) {
		super();
		this.profile.setId(id);
		this.created = created;
	}
*/
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> arrayList) {
		this.orderItems = arrayList;
	}
	
	public OrderItem addOrderItem(OrderItem orderItem) {
		getOrderItems().add(orderItem);
		orderItem.setOrder(this);

		return orderItem;
	}

	public OrderItem removeOrderItem(OrderItem orderItem) {
		getOrderItems().remove(orderItem);
		orderItem.setOrder(null);

		return orderItem;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public BigDecimal getTotalCost() {
		BigDecimal cost = BigDecimal.ZERO;
		if (orderItems != null) {
			for (OrderItem item : orderItems) {
				cost = cost.add(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getCount())));
			}
		}
		return cost;
	}

	@Override
	public String toString() {
		return String.format("Order [id=%s, profile.id=%s, items=%s, created=%s]", getId(), profile.getId(), orderItems, created);
	}

}
