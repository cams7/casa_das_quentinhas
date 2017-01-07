<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="<c:url value='/' />">Casa das Quentinhas</a>
		</div>
		<ul class="nav navbar-nav">
			<li class="${mainPage eq 'home' ? 'active' : '' }"><a
				href="<c:url value='/' />">Início</a></li>

			<li class="${mainPage eq 'cliente' ? 'active' : '' }"><a
				href="<c:url value='/cliente' />">Cliente(s)</a></li>
			<li class="${mainPage eq 'pedido' ? 'active' : '' }"><a
				href="<c:url value='/pedido' />">Pedido(s)</a></li>
			<li class="${mainPage eq 'produto' ? 'active' : '' }"><a
				href="<c:url value='/produto' />">Produto(s)</a></li>
			<li class="${mainPage eq 'empresa' ? 'active' : '' }"><a
				href="<c:url value='/empresa' />">Empresa(s)</a></li>
			<li class="${mainPage eq 'entregador' ? 'active' : '' }"><a
				href="<c:url value='/entregador' />">Entregador(es)</a></li>
			<li class="${mainPage eq 'funcionario' ? 'active' : '' }"><a
				href="<c:url value='/funcionario' />">Funcionário(s)</a></li>
		</ul>
		<ul class="nav navbar-nav navbar-right">
			<li><a href="#"><span class="glyphicon glyphicon-user"></span>
					${loggedinuser}</a></li>
			<li><a href="<c:url value="/logout" />"><span
					class="glyphicon glyphicon-log-out"></span> Sair</a></li>
		</ul>
	</div>
</nav>