<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form"   	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="webnutritionist" 	tagdir="/WEB-INF/tags"%>

<div class="panel panel-default small-center-block">
	<div class="panel-heading">
		<h3 class="panel-title">
			<i class="fa fa-user-plus"></i> Укажите Ваши персональные данные
		</h3>
	</div>
	<div class="panel-body">
		<webnutritionist:form-display-error-if-invalid formName="profileForm" />
		<form:form action="/sign-up" commandName="profileForm" method="post">
		
			<sec:csrfInput/>
			<div class="help-block text-justify">
				Имейте ввиду, что введенные Вами имя и фамилия не смогут быть изменены в будущем! Поэтому предоставляйте реальные имя и фамилию! 
			</div>
			
			<webnutritionist:form-has-error path="firstName"/>
			<div class="form-group ${hasError ? 'has-error has-feedback' : ''}">
				<label for="firstName">Имя</label> 
				<form:input path="firstName" id="firstName" cssClass="form-control" placeholder="Например: Вася" required="required"/>
				<webnutritionist:form-error path="firstName" />
			</div>
			
			<webnutritionist:form-has-error path="lastName"/>
			<div class="form-group ${hasError ? 'has-error has-feedback' : ''}">
				<label for="lastName">Фамилия</label> 
				<form:input path="lastName" id="lastName" cssClass="form-control" placeholder="Например: Васильев" required="required"/>
				<webnutritionist:form-error path="lastName" />
			</div>
			
			<webnutritionist:form-has-error path="password"/>
			<div class="form-group ${hasError ? 'has-error has-feedback' : ''}">
				<label class="control-label" for="password">Новый пароль</label> 
				<form:password path="password" id="password" cssClass="form-control" required="required"/>
				<webnutritionist:form-error path="password" />
			</div>
			
			<webnutritionist:form-has-error path="confirmPassword"/>
			<div class="form-group ${hasError ? 'has-error has-feedback' : ''}">
				<label class="control-label" for="confirmPassword">Подтверждение пароля</label> 
				<form:password path="confirmPassword" id="confirmPassword" cssClass="form-control" required="required"/>
				<webnutritionist:form-error path="confirmPassword" />
			</div>
			<!--  
			<div class="form-group">
				<div>
					<img id="pathCaptchaPhoto" src="${pathCaptchaPhoto }" class="pathCaptchaPhoto-photo"/>
				</div>
			</div>
			<webnutritionist:form-has-error path="captchaText"/>
			<div class="form-group ${hasError ? 'has-error has-feedback' : ''}">
				<label class="control-label" for="pathCaptchaPhoto">Vzhuh Captcha</label>
				<form:input path="captchaText" id="captchaText" cssClass="form-control" placeholder="Введите число, которое наколдовал волшебный кот" required="required"/>
				<webnutritionist:form-error path="captchaText" />
			</div>
			-->
			<button type="submit" class="btn btn-primary">Зарегистрироваться</button>
		</form:form>
	</div>
</div>