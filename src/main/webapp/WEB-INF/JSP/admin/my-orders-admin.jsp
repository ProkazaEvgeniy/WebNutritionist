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
				<td colspan="2" class="text-center">Заказов не найдено</td>
			</tr>
		</c:if>
		<jsp:include page="../admin/my-orders-tbody-admin.jsp" />
	</tbody>
</table>