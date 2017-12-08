<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="index" 		required="true"  type="java.lang.Object" %>
<%@ attribute name="value" 		required="true"  type="java.lang.Object" %>

<%-- Using cache for slider data --%>
<c:if test="${profileFormHeightKeys == null or profileFormHeightValues == null}">
	<c:set var="profileFormHeightKeys" value="[" scope="request" />
	<c:set var="profileFormHeightValues" value="[" scope="request" />
	<c:forEach var="height" items="${heightPeoples }" varStatus="status">
		<c:set var="profileFormHeightKeys"   value='${profileFormHeightKeys}${height.sliderIntValue }${status.last ? "]" : "," }' scope="request" />
		<c:set var="profileFormHeightValues" value='${profileFormHeightValues }"${height.caption }"${status.last ? "]" : "," }' scope="request" />
		
	</c:forEach>
</c:if>

<input name="height" id="itemsHeight" style="width: 100%;" class="height-slider"  
	data-slider-ticks="${profileFormHeightKeys }" data-slider-value="${value}" data-provide="slider" data-slider-handle="square" 
	data-slider-ticks-labels='${profileFormHeightValues }' data-slider-min="1" data-slider-max="9" data-slider-step="1" data-slider-tooltip="hide">