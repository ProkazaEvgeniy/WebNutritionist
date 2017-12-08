<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="webnutritionist" tagdir="/WEB-INF/tags"%>

<webnutritionist:edit-tab-header selected="chart" />

<div class="panel panel-default">
	<div class="panel-body">
		<div class="row profile">
			<div class="col-xs-12 text-center">
				<h4 class="data-header">График количества пищи на 1 месяц</h4>
				<img src="${chartPath.pathOneMonth }" class="img-thumbnail" />
			</div>
			<div class="col-xs-12 text-center">
				<h4 class="data-header">График количества пищи на 6 месяцев</h4>
				<img src="${chartPath.pathSixMonth }" class="img-thumbnail" />
			</div>
		</div>
	</div>
</div>