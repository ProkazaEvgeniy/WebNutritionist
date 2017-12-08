package net.www.webnutritionist.entity;

import java.io.Serializable;

import javax.persistence.Column;
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

import net.www.webnutritionist.util.DataUtil;

@Entity
@Table(name = "child")
public class Child extends AbstractFinishDateEntity<Long> implements Serializable, ProfileEntity, Comparable<Child> {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CHILD_ID_GENERATOR", sequenceName="SEQ_CHILD", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CHILD_ID_GENERATOR")
	private Long id;

	@Column(nullable = false, length = 50)
	@SafeHtml
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_profile", nullable = false)
	@JsonIgnore
	@Transient
	private Profile profile;

	public Child() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Profile getProfile() {
		return this.profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((getBirthDay() == null) ? 0 : getBirthDay().hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Child other = (Child) obj;
		if (getBirthDay() == null) {
			if (other.getBirthDay() != null)
				return false;
		} else if (!getBirthDay().equals(other.getBirthDay()))
			return false;
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
		return true;
	}
	
	@Override
	public int compareTo(Child o) {
		return  DataUtil.compareByFields(o.getBirthDay(), getBirthDay(), true);
	}
}