package net.www.webnutritionist.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.SafeHtml;

import net.www.webnutritionist.annotation.constraints.Adulthood;
import net.www.webnutritionist.entity.Profile;
import net.www.webnutritionist.entity.Spouse;


public class SpouseForm {
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name="birth_day")
	@Adulthood
	private Date birthDay;
	
	@NotNull
	@Size(max=50)
	@SafeHtml
	private String name;
	
	private List<Spouse> items = new ArrayList<>();
	
	private Profile profile;
	
	public SpouseForm() {}
	
	public SpouseForm(Spouse spouse) {
		super();
		this.name = spouse.getName();
		this.birthDay = spouse.getBirthDay();
	}
	
	public SpouseForm(List<Spouse> items) {
		super();
		this.items = items;
	}
	
	public SpouseForm(Profile profile) {
		super();
		this.profile = profile;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public List<Spouse> getItems() {
		return items;
	}

	public void setItems(List<Spouse> items) {
		this.items = items;
	}
}
