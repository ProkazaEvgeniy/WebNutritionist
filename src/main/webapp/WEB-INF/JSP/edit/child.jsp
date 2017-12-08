<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="webnutritionist" tagdir="/WEB-INF/tags"%>

<webnutritionist:edit-tab-header selected="child" />

<div class="panel panel-default">
	<div class="panel-body">
		<h4 class="data-header">Дополнительные данные о детях</h4>
		<hr />
		<webnutritionist:form-display-error-if-invalid formName="childForm" />
		<form:form action="/edit/child" commandName="childForm" method="post">
			<sec:csrfInput/>
			<div id="ui-block-container">
				<c:forEach var="child" items="${childForm.items}" varStatus="status">
					<webnutritionist:edit-child-block index="${status.index}" child="${child }" />
				</c:forEach>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<a href="javascript:webnutritionist.ui.addBlock();">+ Добавить ребенка</a>
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
<script id="ui-block-template" type="text/x-handlebars-template">
	<webnutritionist:edit-child-block index="{{blockIndex}}" />
</script>