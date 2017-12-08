<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="webnutritionist" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h4 class="text-center">Мои заказы</h4>
<hr />
<table id="myOrders" class="table table-bordered" data-page-number="1" data-page-count="${pageCount }">
	<thead>
		<tr>
			<th>Номер заказа</th>
			<th>Дата</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${empty orders }">
			<tr>
				<td colspan="2" class="text-center">Не найдено заказов</td>
			</tr>
		</c:if>
		<jsp:include page="../JSP/fragment/my-orders-tbody.jsp" />
	</tbody>
</table>
<!-- 
<div class="text-center hidden-print">
	<c:if test="${pageCount > 1 }">
		<a id="loadMoreMyOrders" class="btn btn-success">Загрузить еще</a>
	</c:if>
</div>
 -->
