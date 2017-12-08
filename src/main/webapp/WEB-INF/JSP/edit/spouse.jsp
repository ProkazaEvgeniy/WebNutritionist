<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c"      	uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn"     	uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form"   	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="webnutritionist" 	tagdir="/WEB-INF/tags"%>

<webnutritionist:edit-tab-header selected="spouse"/>

<div class="panel panel-default edit-profile">
	<div class="panel-body">
		<h4 class="data-header">Дополнительные данные о супруге</h4>
		<webnutritionist:form-display-error-if-invalid formName="spouseForm" />
		<form:form action="/edit/spouse" commandName="spouseForm" method="post">
		<hr />
		
		<webnutritionist:form-has-error path="name" />
			<div class="form-group ${hasError ? 'has-error' : ''}">
				<label for="inputName" class="col-sm-2 control-label">Имя*</label>
				<div class="col-sm-5">
					
						<form:input path="name" class="form-control" id="inputName" 
						placeholder="Например: Inessa" required="required" />
				
					<webnutritionist:form-error path="name" />
				</div>
				<div class="col-sm-5 help-block">
					<blockquote>Формат имени: Инесса (укажите реальное имя супруга)</blockquote>
				</div>
			</div>
		
		<webnutritionist:form-has-error path="birthDay" />
			<div class="form-group ${hasError ? 'has-error' : ''}">
				<label for="inputBirthDay" class="col-sm-2 control-label">Дата рождение*</label>
				<div class="col-sm-5">
				
						<form:input path="birthDay" class="form-control datepicker" 
						data-date-format="yyyy-mm-dd" id="inputBirthDay" 
						placeholder="Например: 1990-12-31" required="required" />
				
					<webnutritionist:form-error path="birthDay" />
				</div>
				<div class="col-sm-5 help-block">
					<blockquote>Формат даты: yyyy-mm-dd (четыре цифры года - код месяца - день рождения)</blockquote>
				</div>
			</div>
		<hr />
		
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
	});
	</script>
</content>