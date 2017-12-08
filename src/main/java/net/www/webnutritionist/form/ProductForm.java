package net.www.webnutritionist.form;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import net.www.webnutritionist.entity.Category;
import net.www.webnutritionist.entity.Producer;

public class ProductForm {
	
	@Column
	private Long id;
	
	private Integer count;
	
	@Column
	private String name;

	@Column
	private BigDecimal price;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_producer", nullable = false)
	private Producer producer;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_category", nullable = false)
	private Category category;
	
	@Column
	private String description;

	@Temporal(TemporalType.DATE)
	@Column(name="shelf_life")
	private Date shelfLife;

	@Column(name="small_photo")
	private String smallPhoto;

	@Column(name="large_photo")
	private String largePhoto;
	
	public ProductForm() {
	}
	public ProductForm(Long id, Integer count) {
		super();
		this.id = id;
		this.count = count;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}

	public Date getShelfLife() {
		return shelfLife;
	}

	public void setShelfLife(Date shelfLife) {
		this.shelfLife = shelfLife;
	}

	public String getSmallPhoto() {
		return smallPhoto;
	}

	public void setSmallPhoto(String smallPhoto) {
		this.smallPhoto = smallPhoto;
	}

	public String getLargePhoto() {
		return largePhoto;
	}

	public void setLargePhoto(String largePhoto) {
		this.largePhoto = largePhoto;
	}
	
	@Transient
	public String getProductPhoto(){
		if(largePhoto != null) {
			return largePhoto;
		} else {
			return "/static/img/product-placeholder.png";
		}
	}
	
	public void updateProfilePhotos(String largePhoto, String smallPhoto) {
		setLargePhoto(largePhoto);
		setSmallPhoto(smallPhoto);
	}
}
