<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="webnutritionist" tagdir="/WEB-INF/tags"%>

<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">
			Просмтор пользователей 
		</h3>
		<div class="edit-block" style="text-align: right;">
			<a href="/admin/function" >Назад к функциям</a>
		</div>
	</div>
	<div class="panel-body">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>ID</th>
					<th>Фото</th>
					<th>Имя</th>
					<th>Фамилия</th>
					<th>Email</th>
					<th>Телефон</th>
					<th>Д.р.</th>
					<th>Страна</th>
					<th>Город</th>
					<th>Создан</th>
					<th>Действия</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="profile" items="${profiles}">
					<tr>
						<td>${profile.id }</td>
						<td><img alt="${profile.fullName }" src="${profile.smallFoto }" class="photo"></td>
						<td>${profile.firstName }</td>
						<td>${profile.lastName }</td>
						<td>${profile.email }</td>
						<td>${profile.phone }</td>
						<td>${profile.birthDay }</td>
						<td>${profile.country }</td>
						<td>${profile.city }</td>
						<td>${profile.created }</td>
						<td>
							<a href="/${profile.uid }" class="btn btn-primary pull-right hidden-xs">Детали</a>
							<a href="/admin/my-order?id=${profile.id }" class="btn btn-primary pull-right hidden-xs">Заказы</a>
						</td>
					</tr>
				</c:forEach>		
			</tbody>
		</table>
	</div>
</div>