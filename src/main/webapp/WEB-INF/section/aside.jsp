<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="webnutritionist" tagdir="/WEB-INF/tags"%>


<div>
	<div class="visible-xs-block xs-option-container">
		<a class="pull-right" data-toggle="collapse" href="#productCatalog">Каталог товаров<span class="caret"></span></a> <a data-toggle="collapse" href="#findProducts">Find products <span
			class="caret"></span></a>
	</div>
	<!-- Search form -->
	<form class="search" action="/searchProduts">
		<div id="findProducts" class="panel panel-default collapse webnutritionist-product">
			<div class="panel-heading">Найти продукт</div>
			<div class="panel-body">
				<div class="input-group">
					<input type="text" name="query" class="form-control" placeholder="Найти продукт"> 
					<span class="input-group-btn"> 
						<a id="goSearch" class="btn btn-default">Go!</a>
					</span>
				</div>
				<div class="more-options">
					<a data-toggle="collapse" href="#searchOptions">Фильтры<span class="caret"></span></a>
				</div>
			</div>
			<div id="searchOptions" class="collapse">
				<div class="panel-heading">Фильтр по категориям</div>
				<div class="panel-body categories">
					<label> <input type="checkbox" id="allCategories"> Все
					</label>
					<c:forEach var="category" items="${categorys }">
					<div class="form-group">
						<div class="checkbox">
							<label><input type="checkbox" name="categories" value="${category.id }" class="search-option">${category.name } (${category.productCount })</label>
						</div>
					</div>
					</c:forEach>
				</div>
				<div class="panel-heading">Фильтр по производителям</div>
				<div class="panel-body producers">
					<label> <input type="checkbox" id="allProducers"> Все
					</label>
					<c:forEach var="producer" items="${producers }">
					<div class="form-group">
						<div class="checkbox">
							<label><input type="checkbox" name="producers" value="${producer.id }" class="search-option">${producer.name } (${producer.productCount }) </label>
						</div>
					</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</form>
	<!-- /Search form -->
	<!-- Categories -->
	<div id="productCatalog" class="panel panel-default collapse webnutritionist-product">
		<div class="panel-heading">Категория продуктов</div>
		<div class="list-group">
			<c:forEach var="category" items="${categorys }">
				<a href="/products/${category.url }" class="list-group-item"> <span class="badge">${category.productCount }</span>${category.name }</a>
			</c:forEach>
		</div>
	</div>
</div>