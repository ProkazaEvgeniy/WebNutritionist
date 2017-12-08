<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" 				uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" 			uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="webnutritionist" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" 			uri="http://www.springframework.org/tags"%>

<%@ attribute name="index" 				required="true" 	type="java.lang.Object"%>
<%@ attribute name="vegetableSelection" required="false" 	type="net.www.webnutritionist.entity.VegetableSelection"%>

<div id="ui-item-${index }" class="panel panel-default">
	<input type="hidden" name="items[${index }].id" value="${vegetableSelection.id }" />
	<input type="hidden" name="items[${index }].totalVegetables" id="countVegetables" value="" />
	<div class="panel-body ui-item">
		<div class="row">
			<div class="col-xs-12">
				<button type="button" class="close" onclick="$('#ui-item-${index }').remove();"><span aria-hidden="true">&times;</span>
				</button>
			</div>
		</div>
		<div class="row">
			<c:if test="${vegetableSelection != null }"><webnutritionist:form-has-error path="items[${index }].name"/></c:if>
			<div class="col-xs-8 col-md-3 form-group${hasError ? 'has-error has-feedback' : ''}">
				<label class="control-label" for="items${index }name">Название продукта*</label> 
				<input class="form-control" name="items[${index }].name" id="items${index }name" placeholder="Например: Картофель" value="${vegetableSelection.name }" required="required">
				<c:if test="${vegetableSelection != null }">
					<webnutritionist:form-error path="items[${index }].name" />
				</c:if>
			</div>
			<div class="col-xs-12 col-md-9">
				<label for="items${index }quantity">Вес (кг)</label> 
				<div style="padding: 0 30px;">
					<webnutritionist:form-input-slider-vegetable-selection index="${index }" value="${vegetableSelection != null ? vegetableSelection.quantity.sliderIntValue : 0}" />
				</div>
			</div>
		</div>
	</div>
</div>
