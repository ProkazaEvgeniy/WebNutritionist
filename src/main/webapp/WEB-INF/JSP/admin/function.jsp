<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="webnutritionist" tagdir="/WEB-INF/tags"%>

<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">
			Функции администратора 
		</h3>
	</div>
	<div class="panel-body">
		<div class="form-group">
			<a href="/admin/home" type="button" class="btn btn-success">Просмотр пользователей</a>
		</div>
		<div class="form-group">
			<a href="/admin/product" type="button" class="btn btn-success">Создание товарова</a>
		</div>
	</div>
</div>