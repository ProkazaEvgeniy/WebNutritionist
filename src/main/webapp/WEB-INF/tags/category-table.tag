<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<table class="table table-striped">
	<thead>
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>URL</th>
			<th>Product count</th>
			<th>Actions</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${fn:length(categorys) == 0 }">
			<tr>
				<td colspan="5">Список пуст</td>
			</tr>
		</c:if>
		<c:forEach var="category" items="${categorys}" varStatus="status">
			<tr>
				<td>${status.index+1 }</td>
				<td>${category.name }</td>
				<td>${category.url }</td>
				<td>${category.productCount }</td>
				<td><a href="/admin/deleteСategory?id=${category.id }" class="btn btn-danger">Удалить категорию</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>