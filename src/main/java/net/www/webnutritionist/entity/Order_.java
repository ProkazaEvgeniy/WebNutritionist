package net.www.webnutritionist.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Order.class)
public abstract class Order_ {

	public static volatile SingularAttribute<Order, Timestamp> created;
	public static volatile SingularAttribute<Order, Profile> profile;
	public static volatile SingularAttribute<Order, Long> id;

}

