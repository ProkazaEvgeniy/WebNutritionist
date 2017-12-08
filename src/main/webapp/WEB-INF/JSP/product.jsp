<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="webnutritionist" tagdir="/WEB-INF/tags"%>

<div class="container-default">
	<div class="row">
		<aside class="col-xs-12 col-sm-3 col-md-3 col-lg-3">
			<jsp:include page="../section/aside.jsp" />
		</aside>
		<div class="col-xs-12 col-sm-9 col-md-9 col-lg-9">
			<jsp:include page="../section/main.jsp"/>
		</div>
	</div>
</div>