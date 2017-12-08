<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="webnutritionist" tagdir="/WEB-INF/tags"%>

<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">
			Создать товар 
		</h3>
		<div class="edit-block" style="text-align: right;">
			<a href="/admin/function" >Назад к функциям</a>
		</div>
	</div>
	<div class="panel-body">
		<form:form commandName="productForm" action="/admin/save-product?${_csrf.parameterName}=${_csrf.token}" method="post" cssClass="form-horizontal data-form" enctype="multipart/form-data">	
			<div class="form-group ${hasError ? 'has-error' : ''}">
				<label for="inputPhoto" class="col-sm-2 control-label">Фото продукта*</label>
				<div class="col-sm-5 btn-file">
					<img id="currentPhoto" src="${productForm.productPhoto }" class="edit-photo" style="margin-bottom:10px"/><br /> 
					<input type="file" name="productPhoto" id="profilePhoto"/>

				</div>
				<div class="col-sm-5 help-block">
					<blockquote>
						1. Размер фотографии должен быть не меньше чем 400x400 <br /> 
						2. Файл должен быть в формате jpg <br />
					</blockquote>
				</div>
			</div>

			<div class="form-group ${hasError ? 'has-error' : ''}">
				<label for="inputshelfLife" class="col-sm-2 control-label">Cрок годности*</label>
				<div class="col-sm-5">
					<form:input path="shelfLife" class="form-control datepicker" data-date-format="yyyy-mm-dd" id="inputshelfLife" placeholder="Example: 1990-12-31" required="required" />
				
				</div>
				<div class="col-sm-5 help-block">
					<blockquote>Формат даты: yyyy-mm-dd (четыре цифры года - код месяца - день срока годности)</blockquote>
				</div>
			</div>
		
			<div class="form-group ${hasError ? 'has-error' : ''}">
				<label for="inputproducer" class="col-sm-2 control-label">Производитель*</label>
				<div class="col-sm-5">
					<select name="producer" class="form-control">
					<c:forEach var="producer" items="${producers }">
						<option value="${producer.id }" ${producer.id != null ? ' selected="selected"' : ''}>${producer.name }</option>
					</c:forEach>
					</select>
				</div>
				<div class="col-sm-5 help-block">
					<blockquote>
						<div class="btn-group">
							<a href="/admin/producer-add" class="btn btn-success">Добавить производителя</a>
							<a href="/admin/producer-list" type="button" class="btn btn-info">Просмотр производителей</a>
						</div>
					</blockquote>
				</div>
			</div>
	
			<div class="form-group ${hasError ? 'has-error' : ''}">
				<label for="inputcategory" class="col-sm-2 control-label">Категория товара*</label>
				<div class="col-sm-5">
					<select name="category" class="form-control">
					<c:forEach var="category" items="${categories }">
						<option value="${category.id }" ${category.id != null ? ' selected="selected"' : ''}>${category.name }</option>
					</c:forEach>
					</select>
				</div>
				<div class="col-sm-5 help-block">
					<blockquote>
						<div class="btn-group">
							<a href="/admin/category-add" class="btn btn-success">Добавить категорию</a>
							<a href="/admin/category-list" type="button" class="btn btn-info">Просмотр категорий</a>
						</div>
					</blockquote>
				</div>
			</div>
			
			<div class="form-group ${hasError ? 'has-error' : ''}">
				<label for="inputname" class="col-sm-2 control-label">Название товара*</label>
				<div class="col-sm-5">
					<form:input path="name" class="form-control" id="inputname" placeholder="Например: Сосиски" required="required" />

				</div>
				<div class="col-sm-5 help-block"></div>
			</div>

			<div class="form-group ${hasError ? 'has-error' : ''}">
				<label for="inputprice" class="col-sm-2 control-label">Цена*</label>
				<div class="col-sm-5">
					<form:input path="price" class="form-control" id="inputprice" placeholder="Например: 15.25" required="required" />

				</div>
				<div class="col-sm-5 help-block"></div>
			</div>
			
			<div class="form-group ${hasError ? 'has-error' : ''}">
				<label for="inputdescription" class="col-sm-2 control-label">Описание*</label>
				<div class="col-sm-5">
					<form:textarea path="description" class="form-control" id="inputdescription" rows="7"
						placeholder="Например: Какое-то описание продукта"/>
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

<content tag="js-init">
	<script>
	$(document).ready(function(){
		webnutritionist.createDatePicker();
		webnutritionist.createPhotoUploader();
	});
	</script>
</content>