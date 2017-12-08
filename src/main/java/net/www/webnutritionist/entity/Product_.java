package net.www.webnutritionist.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Product.class)
public abstract class Product_ {

	public static volatile SingularAttribute<Product, String> largePhoto;
	public static volatile SingularAttribute<Product, BigDecimal> price;
	public static volatile SingularAttribute<Product, String> name;
	public static volatile SingularAttribute<Product, String> description;
	public static volatile SingularAttribute<Product, Producer> producer;
	public static volatile SingularAttribute<Product, Long> id;
	public static volatile SingularAttribute<Product, Date> shelfLife;
	public static volatile SingularAttribute<Product, String> smallPhoto;
	public static volatile SingularAttribute<Product, Category> category;

}

