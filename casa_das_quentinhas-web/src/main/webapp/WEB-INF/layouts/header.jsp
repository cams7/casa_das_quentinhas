<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">Casa das Quentinhas</a>
		</div>
		<ul class="nav navbar-nav">
			<li class="${activePage eq 'home' ? 'active' : '' }"><a href="/">Início</a></li>

			<li class="${activePage eq 'cliente_index' ? 'active' : '' }"><a
				href="<c:url value='/cliente/list' />">Clientes</a></li>
			<li class="${activePage eq 'pedido_index' ? 'active' : '' }"><a
				href="<c:url value='/pedido/list' />">Pedidos</a></li>
			<li class="${activePage eq 'produto_index' ? 'active' : '' }"><a
				href="<c:url value='/produto/list' />">Produtos</a></li>
			<li class="${activePage eq 'empresa_index' ? 'active' : '' }"><a
				href="<c:url value='/empresa/list' />">Empresas</a></li>
			<li class="${activePage eq 'entregador_index' ? 'active' : '' }"><a
				href="<c:url value='/entregador/list' />">Entregadores</a></li>
			<li class="${activePage eq 'taxa_index' ? 'active' : '' }"><a
				href="<c:url value='/taxa/list' />">Taxas</a></li>
			<li class="${activePage eq 'funcionario_index' ? 'active' : '' }"><a
				href="<c:url value='/funcionario/list' />">Funcionários</a></li>
			<li class="${activePage eq 'usuario_index' ? 'active' : '' }"><a
				href="<c:url value='/usuario/list' />">Usuários</a></li>
		</ul>
		<ul class="nav navbar-nav navbar-right">
			<li><a href="#"><span class="glyphicon glyphicon-user"></span>
					${loggedinuser}</a></li>
			<li><a href="<c:url value="/logout" />"><span
					class="glyphicon glyphicon-log-out"></span> Sair</a></li>
		</ul>
	</div>
</nav>