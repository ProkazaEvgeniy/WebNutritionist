<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" 				uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" 			uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="webnutritionist" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" 			uri="http://www.springframework.org/tags"%>

<%@ attribute name="index" required="true" type="java.lang.Object"%>
<%@ attribute name="child" required="false" type="net.www.webnutritionist.entity.Child"%>

<div id="ui-item-${index }" class="panel panel-default">
	<input type="hidden" name="items[${index }].id" value="${child.id }" />
	<div class="panel-body ui-item">
		<div class="row">
			<div class="col-xs-12">
				<button type="button" class="close" onclick="$('#ui-item-${index }').remove();">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		</div>
		<div class="row">
			<c:if test="${child != null }"><webnutritionist:form-has-error path="items[${index }].name"/></c:if>
			<div class="col-xs-6 col-md-6 form-group ${hasError ? 'has-error has-feedback' : ''}">
				<label class="control-label" for="items${index }name">Имя ребенка*</label> 
				<input class="form-control" name="items[${index }].name" id="items${index }name" 
										placeholder="Example: Matvey" value="${child.name }" required="required">
				<c:if test="${child != null }">
				<webnutritionist:form-error path="items[${index }].name" />
				</c:if>
			</div>
			<c:if test="${child != null }"><webnutritionist:form-has-error path="items[${index }].birthDay"/></c:if>
			<div class="col-xs-6 col-md-6 form-group ${hasError ? 'has-error has-feedback' : ''}">
				<label class="control-label" for="items${index }birthDay">Год рождения*</label> 
				<input class="form-control datepicker" name="items[${index }].birthDay" id="items${index }.birthDay" 
										data-date-format="yyyy-mm-dd" placeholder="Example: 2012-04-12" value="${child.birthDay }" required="required">
				<c:if test="${child != null }">
				<webnutritionist:form-error path="items[${index }].birthDay" />
				</c:if>
			</div>
		</div>
	</div>
</div>
<content tag="js-init">
	<script>
	$(document).ready(function(){
		webnutritionist.createDatePicker();
	});
	</script>
</content>