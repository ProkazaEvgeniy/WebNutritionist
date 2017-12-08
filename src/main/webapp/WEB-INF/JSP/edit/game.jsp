<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="webnutritionist" tagdir="/WEB-INF/tags"%>
<webnutritionist:edit-tab-header selected="game" />
<div class="panel panel-default">
	<div class="panel-body">
		<div class="col-xs-12 text-center">
			<h4 class="data-header">Игра</h4>
			<canvas id="canvas" width="100" height="100"></canvas>
		</div>
	</div>
</div>
<script type="text/javascript" src="/static/js/point.js"></script>
<script type="text/javascript" src="/static/js/game.js"></script>