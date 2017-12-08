package net.www.webnutritionist.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.www.webnutritionist.model.Quantity;

@Entity
@Table(name = "vegetable_selection")
public class VegetableSelection extends AbstractEntity<Long> implements Serializable, ProfileEntity {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="vegetable_selection_id_generator", sequenceName="seq_vegetable_selection", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="vegetable_selection_id_generator")
	private Long id;
	
	@Column(nullable = false, length = 50)
	@SafeHtml
	private String name;
	
	@Column(nullable=false)
	@JsonIgnore
	@Convert(converter = Quantity.PersistJPAConverter.class)
	private Quantity quantity;
	
	@Column(name = "total_vegetables")
	private Integer totalVegetables;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_profile", nullable = false)
	@JsonIgnore
	@Transient
	private Profile profile;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Quantity getQuantity() {
		return quantity;
	}

	public void setQuantity(Quantity quantity) {
		this.quantity = quantity;
	}

	public Integer getTotalVegetables() {
		return totalVegetables;
	}

	public void setTotalVegetables(Integer totalVegetables) {
		this.totalVegetables = totalVegetables;
	}
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((totalVegetables == null) ? 0 : totalVegetables.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		VegetableSelection other = (VegetableSelection) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (quantity != other.quantity)
			return false;
		if (totalVegetables == null) {
			if (other.totalVegetables != null)
				return false;
		} else if (!totalVegetables.equals(other.totalVegetables))
			return false;
		return true;
	}
}
