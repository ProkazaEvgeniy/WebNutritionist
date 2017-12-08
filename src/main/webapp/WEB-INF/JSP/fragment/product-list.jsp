<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="webnutritionist" tagdir="/WEB-INF/tags"%>

<c:forEach var="product" items="${products }">
	<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3 col-xlg-3">
			<!-- PRODUCT DATA -->
			<div id="product${product.id }" class="panel panel-default product">
				<div class="panel-body">
					<div class="thumbnail">
						<img src="${product.largePhoto }" alt="${product.name }">
						<div class="desc">
							<div class="cell">
								<p>
									<span class="title">Описание</span>${product.description }
								</p>
							</div>
						</div>
					</div>
					<h4 class="name" data-toggle="tooltip" title="${product.name }">${product.name }</h4>
					<div class="code">Code: ${product.id }</div>
					<div class="price" data-toggle="tooltip" title="Гривна">${product.price } грн</div>
					<a class="btn btn-primary pull-right buy-btn" data-id-product="${product.id }">Купить</a>
					<div class="list-group">
						<div class="list-group-item"> 
							<small>Категория:</small><br> 
							<span class="category" data-toggle="tooltip" title="${product.category.name }">${product.category.name }</span>
						</div> 
						<div class="list-group-item"> 
							<small>Производитель:</small><br>
							<span class="producer" data-toggle="tooltip" title="${product.producer.name }">${product.producer.name }</span>
						</div>
						<div class="list-group-item"> 
							<small>Срок годности:</small><br>
							<span class="shelfLife" data-toggle="tooltip" title="${product.shelfLife }">${product.shelfLife }</span>
						</div>
					</div>
				</div>
			</div>
			<!-- /PRODUCT DATA -->
	</div>
</c:forEach>

