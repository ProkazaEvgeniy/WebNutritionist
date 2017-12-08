package net.www.webnutritionist.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Spouse.class)
public abstract class Spouse_ {

	public static volatile SingularAttribute<Spouse, Date> birthDay;
	public static volatile SingularAttribute<Spouse, Profile> profile;
	public static volatile SingularAttribute<Spouse, String> name;
	public static volatile SingularAttribute<Spouse, Long> id;

}

