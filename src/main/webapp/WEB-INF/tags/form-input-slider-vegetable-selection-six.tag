<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="index" 		required="true"  type="java.lang.Object" %>
<%@ attribute name="value" 		required="true"  type="java.lang.Object" %>

<%-- Using cache for slider data --%>
<c:if test="${vegetableSelectionKeys == null or vegetableSelectionKeys == null}">
	<c:set var="vegetableSelectionKeys" value="[" scope="request" />
	<c:set var="vegetableSelectionValues" value="[" scope="request" />
	<c:forEach var="quantity" items="${vegetableSelectionsSix }" varStatus="status">
		<c:set var="vegetableSelectionKeys" value='${vegetableSelectionKeys}${quantity.sliderIntValue }${status.last ? "]" : "," }' scope="request" />
		<c:set var="vegetableSelectionValues" value='${vegetableSelectionValues }"${quantity.caption }"${status.last ? "]" : "," }' scope="request" />
	</c:forEach>
</c:if>

<input name="items[${index }].quantity" id="quantity${index }" style="width: 100%;" class="quantity-slider count"  
	data-slider-ticks="${vegetableSelectionKeys }" data-slider-value="${value}" data-provide="slider" data-slider-handle="square" 
	data-slider-ticks-labels='${vegetableSelectionValues }' data-slider-min="1" data-slider-max="11" data-slider-step="1" data-slider-tooltip="hide">