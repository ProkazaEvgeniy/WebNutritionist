<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ attribute name="selected" required="true" type="java.lang.String" %>

<ul class="nav nav-tabs" role="tablist">
	<li role="presentation" ${selected == 'vegetable-selection/one-month' ? 'class="active"' : ''}><a href="/edit/vegetable-selection/one-month">Период 1 месяц</a></li>
	<li role="presentation" ${selected == 'vegetable-selection/six-month' ? 'class="active"' : ''}><a href="/edit/vegetable-selection/six-month">Период 6 месяц</a></li>
</ul>