<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="webnutritionist" tagdir="/WEB-INF/tags"%>

<div id="productList">
	<div id="productContainer" class="row" data-product-total="${page.totalPages }" data-product-number="${page.number }">
		<jsp:include page="../JSP/fragment/product-list.jsp"/>
	</div>
	<c:if test="${page.number < page.totalPages - 1}">
	<div id="loadMoreContainer" class="col-xs-12 text-center">
		<a id="loadMore1" class="btn btn-primary">Загрузить еще</a>
	</div>
	<div id="loadMoreIndicator" class="col-xs-12 text-center" style="display:none;">
		<img src="/static/img/large-loading.gif" alt="loading..."/>
	</div>
	</c:if>
</div>
<webnutritionist:add-product-popub/>
