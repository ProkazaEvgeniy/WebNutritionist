package net.www.webnutritionist.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import net.www.webnutritionist.model.HeightPeople;
import net.www.webnutritionist.model.WeightCategory;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Profile.class)
public abstract class Profile_ {

	public static volatile ListAttribute<Profile, Spouse> spouses;
	public static volatile SingularAttribute<Profile, Date> birthDay;
	public static volatile SingularAttribute<Profile, String> country;
	public static volatile SingularAttribute<Profile, String> lastName;
	public static volatile SingularAttribute<Profile, String> role;
	public static volatile SingularAttribute<Profile, String> city;
	public static volatile SingularAttribute<Profile, Date> created;
	public static volatile SingularAttribute<Profile, WeightCategory> weight;
	public static volatile SingularAttribute<Profile, Boolean> completed;
	public static volatile ListAttribute<Profile, Child> childs;
	public static volatile SingularAttribute<Profile, String> firstName;
	public static volatile SingularAttribute<Profile, String> uid;
	public static volatile SingularAttribute<Profile, String> smallFoto;
	public static volatile SingularAttribute<Profile, String> password;
	public static volatile SingularAttribute<Profile, String> largeFoto;
	public static volatile SingularAttribute<Profile, String> phone;
	public static volatile SingularAttribute<Profile, Long> id;
	public static volatile SingularAttribute<Profile, String> email;
	public static volatile SingularAttribute<Profile, HeightPeople> height;

}

