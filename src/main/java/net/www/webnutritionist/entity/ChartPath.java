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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="chart_path")
public class ChartPath extends AbstractEntity<Long> implements Serializable, ProfileEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CHART_PATH_ID_GENERATOR", sequenceName="seq_chart_path", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CHART_PATH_ID_GENERATOR")
	private Long id;

	@Column(name = "path_one_month", length = 255)
	@JsonIgnore
	@Size(max=255)
	private String pathOneMonth;
	
	@Column(name = "path_six_month", length = 255)
	@Size(max=255)
	private String pathSixMonth;
	
	//bi-directional many-to-one association to Profile
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_profile")
	private Profile profile;

	public ChartPath() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPathOneMonth() {
		return pathOneMonth;
	}

	public void setPathOneMonth(String pathOneMonth) {
		this.pathOneMonth = pathOneMonth;
	}

	public String getPathSixMonth() {
		return pathSixMonth;
	}

	public void setPathSixMonth(String pathSixMonth) {
		this.pathSixMonth = pathSixMonth;
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
		result = prime * result + ((pathOneMonth == null) ? 0 : pathOneMonth.hashCode());
		result = prime * result + ((pathSixMonth == null) ? 0 : pathSixMonth.hashCode());
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
		ChartPath other = (ChartPath) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pathOneMonth == null) {
			if (other.pathOneMonth != null)
				return false;
		} else if (!pathOneMonth.equals(other.pathOneMonth))
			return false;
		if (pathSixMonth == null) {
			if (other.pathSixMonth != null)
				return false;
		} else if (!pathSixMonth.equals(other.pathSixMonth))
			return false;
		return true;
	}
}
