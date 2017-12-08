package net.www.webnutritionist.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class AbstractFinishDateEntity<T> extends AbstractEntity<T> {
	private static final long serialVersionUID = -3388293457711051284L;

	@Column(name = "birth_day")
	@Temporal(TemporalType.DATE)
	@JsonIgnore
	private Date birthDay;
	
	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

}
