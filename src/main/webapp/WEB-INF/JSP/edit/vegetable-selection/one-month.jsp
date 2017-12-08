<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c"      	uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn"     	uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form"   	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="webnutritionist" 	tagdir="/WEB-INF/tags"%>

<webnutritionist:edit-tab-header selected="vegetable-selection" />

<webnutritionist:edit-tab-header-vegetable-selection selected="vegetable-selection/one-month" />

<div class="panel panel-default">

	<div class="panel-heading">
		<div class="row">
			<div class="col-xs-12 text-right">
				<div class="btn-group">
				  <button class="btn btn-default btn-lg dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    Рекомендации на 1 месяц <span class="caret"></span>
				  </button>
				  <div class="dropdown-menu">
					  <div class="panel-body">
							<table class="table table-striped">
								<thead>
									<tr>
										<th>Название овощей</th>
										<th>Вес (кг)</th>
										
									</tr>
								</thead>
								<tbody>
									<c:forEach var="vegetable" items="${vegetableNutritionistForm.items }">
										<tr>
											<td>${vegetable.name }</td>
											<td>${vegetable.count }</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<blockquote>
						<footer>вы можете выбрать количество овощей самостоятельно</footer>
						</blockquote>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="panel-body" id="changeCountVegetables">
	<webnutritionist:form-display-error-if-invalid formName="vegetableSelectionForm" />
		<form:form action="/edit/vegetable-selection/one-month" commandName="vegetableSelectionForm" method="post">
		<h4 class="data-header">У Вас всего <span class="norma">200</span> кг овощей в рационе, распределите их правильно</h4>
			<div class="help-block">
				<blockquote>
					<h2>
						<span id="cost">
						<c:choose>
							<c:when test="${vegetableSelectionForm.items.size() == 0}">
								0
							</c:when>
							<c:otherwise>
								${vegetableSelectionForm.items.get(0).totalVegetables}
							</c:otherwise>
						</c:choose>
						</span> кг
					</h2>
				</blockquote>
			</div>
		<hr />
		
			<sec:csrfInput/>
			<div id="ui-block-container" class="edit-vegetableSelection">
				<c:forEach var="vegetableSelection" items="${vegetableSelectionForm.items}" varStatus="status">
					<webnutritionist:edit-vegetable-selection-block index="${status.index}" vegetableSelection="${vegetableSelection }" />
				</c:forEach>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<a href="javascript:webnutritionist.ui.addBlock();">+ Добавить овощи</a>
				</div>
			</div>
			<hr />
			<div class="row">
				<div class="col-xs-12 text-center">
					<button type="submit" class="btn btn-primary" id="loadBtn">Сохранить</button>
				</div>
			</div>
		</form:form>
	</div>
</div>
<script id="ui-block-template" type="text/x-handlebars-template">
	<webnutritionist:edit-vegetable-selection-block index="{{blockIndex}}" />
</script>