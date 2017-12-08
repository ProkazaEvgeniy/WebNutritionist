package net.www.webnutritionist.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.SafeHtml;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.www.webnutritionist.annotation.ProfileDataFieldGroup;
import net.www.webnutritionist.annotation.constraints.Adulthood;
import net.www.webnutritionist.annotation.constraints.EnglishLanguage;
import net.www.webnutritionist.annotation.constraints.Phone;
import net.www.webnutritionist.model.HeightPeople;
import net.www.webnutritionist.model.WeightCategory;

@Entity
@Table(name = "profile")
@Document(indexName="profile")
public class Profile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PROFILE_ID_GENERATOR", sequenceName="SEQ_PROFILE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROFILE_ID_GENERATOR")
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name="birth_day")
	@ProfileDataFieldGroup
	@Adulthood
	@NotNull
	private Date birthDay;

	@Column
	@ProfileDataFieldGroup
	@Size(max=100)
	@NotNull
	@SafeHtml
	//@EnglishLanguage(withNumbers=false, withSpechSymbols=false)
	private String city;

	@Column
	@ProfileDataFieldGroup
	@Size(max=60)
	@NotNull
	@SafeHtml
	//@EnglishLanguage(withNumbers=false, withSpechSymbols=false)
	private String country;
	
	@Column(name = "first_name", nullable = false, length = 50)
	private String firstName;
	
	
	@Column(name = "last_name", nullable = false, length = 50)
	private String lastName;
	
	@Column(name = "large_foto", length = 255)
	@JsonIgnore
	@Size(max=255)
	private String largeFoto;
	
	@Column(name = "small_foto", length = 255)
	@Size(max=255)
	private String smallFoto;
	
	@Column(length = 20)
	@JsonIgnore
	@ProfileDataFieldGroup
	@NotNull
	@Size(max=20)
	@Phone
	private String phone;
	
	@Column(length = 100)
	@JsonIgnore
	@ProfileDataFieldGroup
	@NotNull
	@Size(max=100)
	@Email
	@EnglishLanguage
	private String email;
	
	@Column(nullable = false, length = 100)
	private String uid;
	
	@Column(nullable = false, length = 100)
	@JsonIgnore
	private String password;
	
	@Column(nullable = false)
	@JsonIgnore
	private boolean completed;

	@Column(insertable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@Column
	@Convert(converter = HeightPeople.PersistJPAConverter.class)
	@ProfileDataFieldGroup
	@NotNull
	@JsonIgnore
	private HeightPeople height;

	@Column
	@Convert(converter = WeightCategory.PersistJPAConverter.class)
	@ProfileDataFieldGroup
	@NotNull
	@JsonIgnore
	private WeightCategory weight;
	
	@Column(insertable=false)
	private String role;
	
	@OneToMany(mappedBy="profile")
	private List<Child> childs;

	@OneToMany(mappedBy = "profile")
	private List<Spouse> spouses;

	public Profile() {
		super();
	}
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public Date getBirthDay() {
		return this.birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLargeFoto() {
		return this.largeFoto;
	}

	public void setLargeFoto(String largeFoto) {
		this.largeFoto = largeFoto;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSmallFoto() {
		return this.smallFoto;
	}

	public void setSmallFoto(String smallFoto) {
		this.smallFoto = smallFoto;
	}

	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public List<Child> getChilds() {
		return this.childs;
	}

	public void setChilds(List<Child> childs) {
		this.childs = childs;
	}

	public Child addChild(Child child) {
		getChilds().add(child);
		child.setProfile(this);

		return child;
	}

	public Child removeChild(Child child) {
		getChilds().remove(child);
		child.setProfile(null);

		return child;
	}

	public Spouse getSpouse(){
		Spouse s = null;
		for(Spouse spouse : getSpouses()){
			s = spouse;
		}
		if (s == null) {
			s = new Spouse();
		}
		return s;
	}
	
	public List<Spouse> getSpouses() {
		return this.spouses;
	}

	public void setSpouses(List<Spouse> spouses) {
		this.spouses = spouses;
	}

	public Spouse addSpous(Spouse spous) {
		getSpouses().add(spous);
		spous.setProfile(this);

		return spous;
	}

	public Spouse removeSpous(Spouse spous) {
		getSpouses().remove(spous);
		spous.setProfile(null);

		return spous;
	}
	
	public HeightPeople getHeight() {
		return this.height;
	}

	public void setHeight(HeightPeople height) {
		this.height = height;
	}

	public WeightCategory getWeight() {
		return this.weight;
	}

	public void setWeight(WeightCategory weight) {
		this.weight = weight;
	}
		
	@Transient
	public String getFullName() {
		return firstName + " " + lastName;
	}
	
	@Transient
	public int getAge(){
		LocalDate birthdate = new LocalDate (birthDay);
		LocalDate now = new LocalDate();
		Years age = Years.yearsBetween(birthdate, now);
		return age.getYears();
	}
	
	@Transient
	public String getProfilePhoto(){
		if(largeFoto != null) {
			return largeFoto;
		} else {
			return "/static/img/profile-placeholder.png";
		}
	}
	
	public void updateProfilePhotos(String largePhoto, String smallPhoto) {
		setLargeFoto(largePhoto);
		setSmallFoto(smallPhoto);
	}

}