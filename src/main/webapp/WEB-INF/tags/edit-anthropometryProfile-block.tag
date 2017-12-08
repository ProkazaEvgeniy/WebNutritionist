<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="webnutritionist" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ attribute name="index" required="true" type="java.lang.Object"%>
<%@ attribute name="profileForm" required="false" type="net.www.webnutritionist.entity.Profile"%>

<div class="panel panel-default">
	<input type="hidden" name="profileForm.id" value="${profileForm.id }" />
	<div class="panel-body">
		<h4 class="data-header text-center">Для реализации правильного состава здорового питания необходимо знать Ваши парамерты*</h4>
		<div class="row">
			<div class="col-xs-12">
				<label for="profileFormHeight">Рост (см)</label>
				<div style="padding: 0 30px;">
					<webnutritionist:form-input-slider index="${profileForm.id }" value="${profileForm != null ? profileForm.height.sliderIntValue : 0}" />
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12 form-group" style="padding: 0 30px; padding-top: 30px">
				<label for="profileFormWeight">Вес (кг)</label> 
				<select name="weight" class="form-control">
					<c:forEach var="weightCategory" items="${weightCategorys }">
						<option value="${weightCategory.dbValue }" ${weightCategory == profileForm.weight ? 'selected="selected"' : '' }>
							<spring:message code="weightCategory.${weightCategory}" />
						</option>
					</c:forEach>
				</select>
			</div>
		</div>
	</div>
</div>