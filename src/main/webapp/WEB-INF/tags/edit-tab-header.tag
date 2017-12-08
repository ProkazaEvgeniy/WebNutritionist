<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ attribute name="selected" required="true" type="java.lang.String" %>

<ul class="nav nav-tabs" role="tablist">
	<li role="presentation" ${selected == 'profile'						? 'class="active"' : ''}><a href="/edit">Профиль главы семейства</a></li>
	<li role="presentation" ${selected == 'spouse'						? 'class="active"' : ''}><a href="/edit/spouse">Супруг</a></li>
	<li role="presentation" ${selected == 'child'						? 'class="active"' : ''}><a href="/edit/child">Дети</a></li>
	<li role="presentation" ${selected == 'chart'      					? 'class="active"' : ''}><a href="/edit/chart">График</a></li>
	<li role="presentation" ${selected == 'vegetable-selection'      	? 'class="active"' : ''}><a href="/edit/vegetable-selection">Выбор овощей</a></li>
	<li role="presentation" ${selected == 'game'      					? 'class="active"' : ''}><a href="/edit/game">Игра</a></li>
</ul>