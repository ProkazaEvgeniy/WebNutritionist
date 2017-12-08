<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="webnutritionist" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="order">
	<h4 class="text-center">Заказ # ${order.id }</h4>
	<hr />
	<webnutritionist:product-table items="${order.orderItems }" totalCost="${order.totalCost }" showActionColumn="false" />
</div>