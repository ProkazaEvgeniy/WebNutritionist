<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="webnutritionist" tagdir="/WEB-INF/tags"%>

<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">
			Просмтор категорий товара
		</h3>
		<div class="edit-block" style="text-align: right;">
			<a href="/admin/product" >Назад к созданию товара</a>
		</div>
	</div>
	<div class="panel-body">
		<webnutritionist:category-table/>
		<webnutritionist:category-add/>
	</div>
</div>