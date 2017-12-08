<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:forEach var="order" items="${orders }">
	<tr class="item">
		<td><a href="/admin/order?id=${order.id }">Заказ # ${order.id }</a></td>
		<td><fmt:formatDate value="${order.created }" pattern="yyyy-MM-dd HH:mm" /></td>
	</tr>
</c:forEach>