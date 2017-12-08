<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"    uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="webnutritionist" tagdir="/WEB-INF/tags"%>

<sec:authorize access="isAuthenticated()">
<sec:authentication property="principal" var="CURRENT_PROFILE"/>
<c:set var="showEdit" value="${CURRENT_PROFILE.id == profile.id }" />
</sec:authorize>

<div id="profile-${profile.id }" class="row profile" >
	<div class="col-xs-12 col-sm-6 col-md-4">
		<webnutritionist:profile-name profile="${profile }" showEdit="${showEdit }"/>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-8">
		<webnutritionist:profile-charts/>
	</div>
</div>