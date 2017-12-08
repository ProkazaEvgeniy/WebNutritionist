<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="webnutritionist" tagdir="/WEB-INF/tags"%>

<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">
			Создать категорию товара
		</h3>
		<div class="edit-block" style="text-align: right;">
			<a href="/admin/category-list" >Назад к категориям</a>
		</div>
	</div>
	<div class="panel-body">
		<form:form commandName="categoryForm" action="/admin/save-category" method="post" cssClass="form-horizontal data-form">
		
			<div class="form-group ${hasError ? 'has-error' : ''}">
				<label for="inputcategory" class="col-sm-2 control-label">Имя категории товара*</label>
				<div class="col-sm-5">
					<form:input path="name" class="form-control" id="inputcategory" placeholder="Например: Молочные продукты" required="required" />
				</div>
				<div class="col-sm-5 help-block"></div>
			</div>

			<div class="form-group ${hasError ? 'has-error' : ''}">
				<label for="inputproducer" class="col-sm-2 control-label">Имя url*</label>
				<div class="col-sm-5">
					<form:input path="url" class="form-control" id="inputproducer" placeholder="Например: milk (на английском языке)" required="required" />
				</div>
				<div class="col-sm-5 help-block"></div>
			</div>
			
			<div class="form-group ${hasError ? 'has-error' : ''}">
				<label for="inputproductCount" class="col-sm-2 control-label">Количество продуктов*</label>
				<div class="col-sm-5">
					<form:input path="productCount" class="form-control" id="inputproductCount" placeholder="Например: целое число - 10" required="required" />
				</div>
				<div class="col-sm-5 help-block"></div>
			</div>
	
			<div class="row">
				<div class="col-xs-12 text-center">
					<button type="submit" class="btn btn-primary">Сохранить</button>
				</div>
			</div>
		</form:form>
	</div>
</div>