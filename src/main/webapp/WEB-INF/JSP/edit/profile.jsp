<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c"      	uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn"     	uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form"   	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="webnutritionist" 	tagdir="/WEB-INF/tags"%>

<webnutritionist:edit-tab-header selected="profile"/>

<div class="panel panel-default edit-profile">
	<div class="panel-body">
		<h1 class="text-center">${profileForm.fullName }</h1>
		<c:if test="${!profileForm.completed }">
		<hr />
		<h4 class="data-header">Следующие поля должны быть заполнены, чтобы завершить регистрацию!!</h4>
		</c:if>
		<webnutritionist:form-display-error-if-invalid formName="contactsForm" />
		<form:form commandName="profileForm" action="/edit?${_csrf.parameterName}=${_csrf.token}" method="post" cssClass="form-horizontal data-form" enctype="multipart/form-data">
			<form:hidden path="firstName"/>
			<form:hidden path="lastName"/>
			<form:hidden path="largeFoto"/>
			<form:hidden path="completed"/>
			
			<webnutritionist:form-has-error path="profilePhoto" />
			<div class="form-group ${hasError ? 'has-error' : ''}">
				<label for="inputPhoto" class="col-sm-2 control-label">Фото*</label>
				<div class="col-sm-5 btn-file">
					<img id="currentPhoto" src="${profileForm.profilePhoto }" class="edit-photo img-thumbnail" style="margin-bottom:10px"/><br /> 
					<input type="file" name="profilePhoto" id="profilePhoto" />
					<webnutritionist:form-error path="profilePhoto" />
				</div>
				<div class="col-sm-5 help-block">
					<blockquote>
						1. Размер фотографии должен быть не меньше чем 400x400 <br /> 
						2. Файл должен быть в формате jpg <br />
					</blockquote>
				</div>
			</div>
			<webnutritionist:form-has-error path="birthDay" />
			<div class="form-group ${hasError ? 'has-error' : ''}">
				<label for="inputBirthDay" class="col-sm-2 control-label">Дата рождение*</label>
				<div class="col-sm-5">
					<form:input path="birthDay" class="form-control datepicker" data-date-format="yyyy-mm-dd" id="inputBirthDay" placeholder="Например: 1990-12-31" required="required" />
					<webnutritionist:form-error path="birthDay" />
				</div>
				<div class="col-sm-5 help-block">
					<blockquote>Формат даты: yyyy-mm-dd (четыре цифры года - код месяца - день рождения)</blockquote>
				</div>
			</div>
			<webnutritionist:form-has-error path="country" />
			<div class="form-group ${hasError ? 'has-error' : ''}">
				<label for="inputCountry" class="col-sm-2 control-label">Страна*</label>
				<div class="col-sm-5">
					<form:input path="country" class="form-control" id="inputCountry" placeholder="Например: Украина" required="required" />
					<webnutritionist:form-error path="country" />
				</div>
				<div class="col-sm-5 help-block"></div>
			</div>
			<webnutritionist:form-has-error path="city" />
			<div class="form-group ${hasError ? 'has-error' : ''}">
				<label for="inputCity" class="col-sm-2 control-label">Город*</label>
				<div class="col-sm-5">
					<form:input path="city" class="form-control" id="inputCity" placeholder="Например: Харьков" required="required" />
					<webnutritionist:form-error path="city" />
				</div>
				<div class="col-sm-5 help-block"></div>
			</div>
			<webnutritionist:form-has-error path="email" />
			<div class="form-group ${hasError ? 'has-error' : ''}">
				<label for="inputEmail" class="col-sm-2 control-label">Email*</label>
				<div class="col-sm-5">
					<form:input path="email" class="form-control" id="inputEmail" placeholder="Например: имя.фамилия@gmail.com" required="required" />
					<webnutritionist:form-error path="email" />
				</div>
				<div class="col-sm-5 help-block">
					<blockquote>
						1. Желательно, чтобы email содержал Ваше имя и фамилию как указано в загран паспорте. Если указанное имя уже занято, возможны сокращения.<br />
					</blockquote>
				</div>
			</div>
			<webnutritionist:form-has-error path="phone" />
			<div class="form-group ${hasError ? 'has-error' : ''}">
				<label for="inputPhone" class="col-sm-2 control-label">Телефон*</label>
				<div class="col-sm-5">
					<form:input path="phone" class="form-control" id="inputPhone" placeholder="Например: +380501234567" required="required" />
					<webnutritionist:form-error path="phone" />
				</div>
				<div class="col-sm-5 help-block">
					<blockquote>Номер телефона должен быть рабочим и тем номером с которого Вы будете отвечать на звонки с неизвестных Вам номеров. 
					Номер телефона нужно предоставлять в формате <a href="https://ru.wikipedia.org/wiki/E.164" target="_blank">E.164</a>, например: +380501234567</blockquote>
				</div>
			</div>
			
			<div id="ui-block-container" class="edit-anthropometryProfile">
				<webnutritionist:edit-anthropometryProfile-block index="1" profileForm="${profileForm }"/>
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