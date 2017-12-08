<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="webnutritionist" tagdir="/WEB-INF/tags"%>

<div id="shoppingCart">
	<sec:authorize access="!isAuthenticated()">
		<div class="alert alert-warning hidden-print" role="alert">Для того, чтобы сделать заказ зарегистрируйтесь или войдите в свой личный кабинет</div>
	</sec:authorize>
	<webnutritionist:product-table items="${CURRENT_SHOPPING_CART.items }" totalCost="${CURRENT_SHOPPING_CART.totalCost }" showActionColumn="true" />
	<div class="row hidden-print">
		<div class="col-md-4 col-md-offset-4 col-lg-2 col-lg-offset-5">
			<c:choose> 
				<c:when test="${fn:length(CURRENT_SHOPPING_CART.items) != 0 }">
					<sec:authorize access="hasAuthority('USER')">
						<a href="javascript:void(0);" class="post-request btn btn-primary btn-block" data-url="/order">Сделать заказ</a>
					</sec:authorize>
				</c:when>
			</c:choose>
		</div>
	</div>
</div>