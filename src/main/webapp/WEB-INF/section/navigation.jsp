<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="webnutritionist" tagdir="/WEB-INF/tags"%>

	<sec:authentication property="principal" var="CURRENT_PROFILE"/>
		<nav class="navbar navbar-default navbar-fixed-top webnutritionist-nav" role="navigation">
				<div class="container-fluid">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" data-target="#ishopNav" aria-expanded="false">
							<span class="sr-only">Toggle navigation</span>
							<span class="icon-bar"></span> 
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
						</button>
						<a class="navbar-brand" href="/welcome">ДобрОгород</a>
					</div>
					<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
						<ul class="nav navbar-nav navbar-right">
							<sec:authorize access="!isAuthenticated()">
								<li><a href="/products">Магазин</a></li>
								<!-- shop cart  -->
									<ul id="currentShoppingCart" class="nav navbar-nav navbar-right ${CURRENT_SHOPPING_CART == null or CURRENT_SHOPPING_CART.totalCount == 0 ? 'hidden' : '' }">
										<li class="dropdown">
											<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
												<i class="fa fa-shopping-cart" aria-hidden="true"></i> Корзина (<span class="total-count">${CURRENT_SHOPPING_CART.totalCount}</span>)<span class="caret"></span>
											</a>
											<div class="dropdown-menu shopping-cart-desc">
												Общее кол-во: <span class="total-count">${CURRENT_SHOPPING_CART.totalCount}</span><br> 
												Общая стоимость: <span class="total-cost">${CURRENT_SHOPPING_CART.totalCost}</span><br> 
												<a href="/shopping-cart" class="btn btn-primary btn-block">Показать корзину</a>
											</div>
										</li>
									</ul>
								<!-- shop carrt -->
								<li><a href="/sign-in">Войти</a></li>
								<li><a href="/sign-up">Зарегистрироваться</a></li>
							</sec:authorize>
							<sec:authorize access="hasAuthority('USER')">
								<li><a href="/products">Магазин</a></li>
								<!-- shop cart  -->
									<ul id="currentShoppingCart" class="nav navbar-nav navbar-right ${CURRENT_SHOPPING_CART == null or CURRENT_SHOPPING_CART.totalCount == 0 ? 'hidden' : '' }">
										<li class="dropdown">
											<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
												<i class="fa fa-shopping-cart" aria-hidden="true"></i> Корзина (<span class="total-count">${CURRENT_SHOPPING_CART.totalCount}</span>)<span class="caret"></span>
											</a>
											<div class="dropdown-menu shopping-cart-desc">
												Общее кол-во: <span class="total-count">${CURRENT_SHOPPING_CART.totalCount}</span><br> 
												Общая стоимость: <span class="total-cost">${CURRENT_SHOPPING_CART.totalCost}</span><br> 
												<a href="/shopping-cart" class="btn btn-primary btn-block">Показать корзину</a>
											</div>
										</li>
									</ul>
								<!-- shop carrt -->
								<li class="dropdown">
									<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${CURRENT_PROFILE.fullName } <span class="caret"></span></a>
									<ul class="dropdown-menu profile-menu">
										<li><a href="/${CURRENT_PROFILE.username }"><i class="fa fa-eye"></i> Мой профиль</a></li>
										<li><a href="/my-orders"><i class="fa fa-cart-plus" aria-hidden="true"></i> Мои заказы</a></li>
										<li><a href="/edit"><i class="fa fa-pencil"></i> Изменить профиль</a></li>
										<li><a href="/edit/password"><i class="fa fa-unlock-alt"></i> Изменить пароль</a></li>
										<li><a href="/remove"><i class="fa fa-trash-o"></i> Удалить профиль</a></li>
										<li role="separator" class="divider"></li>
										<li><a href="javascript:webnutritionist.logout('${_csrf.token}');"><i class="fa fa-sign-out"></i> Выйти</a></li>
									</ul>
								</li>
							</sec:authorize>
							<sec:authorize access="hasAuthority('ADMIN')">
								<li><a href="/products">Магазин</a></li>
								<li class="dropdown">
									<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${CURRENT_PROFILE.fullName } <span class="caret"></span></a>
									<ul class="dropdown-menu profile-menu">
										<li><a href="/${CURRENT_PROFILE.username }"><i class="fa fa-eye"></i>Мой профиль</a></li>
										<li><a href="/edit"><i class="fa fa-pencil"></i>Изменить</a></li>
										<li><a href="/admin/function"><i class="fa fa-pencil"></i>Функции Адмистратора</a></li>
										<li><a href="/edit/password"><i class="fa fa-unlock-alt"></i>Пароль</a></li>
										<li><a href="/remove"><i class="fa fa-trash-o"></i>Удалить</a></li>
										<li role="separator" class="divider"></li>
										<li><a href="javascript:webnutritionist.logout('${_csrf.token}');"><i class="fa fa-sign-out"></i>Выйти</a></li>
									</ul></li>
							</sec:authorize>
						</ul>
						<form action="/search" method="get" class="navbar-form navbar-right" role="search">
							<div class="form-group">
								<input class="form-control" placeholder="Найти профиль" name="query" value="${query }">
							</div>
							<button type="submit" class="btn btn-primary">Найти</button>
						</form>
					</div>
				</div>
		</nav>