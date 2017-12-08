<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ attribute name="profile" required="true" type="net.www.webnutritionist.entity.Profile" %>
<%@ attribute name="showEdit"  required="false" type="java.lang.Boolean" %>

<div class="panel panel-primary">
	<c:choose>
	<c:when test="${showEdit }">
	<div class="text-center">
		<a href="/edit"><img alt="photo" src="${profile.largeFoto }" class="img-responsive photo img-thumbnail"></a>
	</div>
	<h1 class="text-center"><a href="/edit" style="color:black;">${profile.fullName }</a></h1>
	</c:when>
	<c:otherwise>
	<img alt="photo" src="${profile.largeFoto }" class="img-responsive photo">
	<h1 class="text-center">${profile.fullName }</h1>
	</c:otherwise>
	</c:choose>
	<h6 class="text-center">
		<strong>${profile.city }, ${profile.country }</strong>
	</h6>
	<h6 class="text-center">
		<strong>Возраст:</strong> ${profile.age }, <strong>День рождения: </strong> <fmt:formatDate value="${profile.birthDay }" type="date" dateStyle="medium"/>
	</h6>
	<div class="list-group contacts">
		<a href="tel:${profile.phone }" class="list-group-item"><i class="fa fa-phone"></i> ${profile.phone }</a> 
		<a href="mailto:${profile.email}" class="list-group-item"><i class="fa fa-envelope"></i> ${profile.email}</a> 
	</div>
</div>