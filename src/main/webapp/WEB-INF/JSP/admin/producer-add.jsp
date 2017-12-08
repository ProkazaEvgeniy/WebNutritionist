<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="webnutritionist" tagdir="/WEB-INF/tags"%>

<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">
			Создать производителя
		</h3>
		<div class="edit-block" style="text-align: right;">
			<a href="/admin/producer-list" >Назад к производителям</a>
		</div>
	</div>
	<div class="panel-body">
		<form:form commandName="producerForm" action="/admin/save-producer" method="post" cssClass="form-horizontal data-form">
		
			<div class="form-group ${hasError ? 'has-error' : ''}">
				<label for="inputname" class="col-sm-2 control-label">Имя производителя*</label>
				<div class="col-sm-5">
					<form:input path="name" class="form-control" id="inputname" placeholder="Например: Завод Молочник" required="required" />
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