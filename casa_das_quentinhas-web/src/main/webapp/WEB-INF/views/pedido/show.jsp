<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<h3 class="page-header">
	Visualizar Pedido <span class="label label-default">${pedido.id}</span>
</h3>

<div class="row">
	<div class="col-md-6">
		<p>
			<strong>${pedido.tipoCliente eq 'PESSOA_JURIDICA' ? 'Empresa' : 'Cliente' }</strong>
		</p>
		<p>${pedido.cliente.nome}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>Tipo de cliente</strong>
		</p>
		<p>${pedido.tipoCliente.descricao}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>Forma de pagamento</strong>
		</p>
		<p>${pedido.formaPagamento.descricao}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>Situação</strong>
		</p>
		<p>${pedido.situacao.descricao}</p>
	</div>
</div>

<div class="row">
	<div class="col-md-2">
		<p>
			<strong>Quantidade</strong>
		</p>
		<p>${pedido.quantidade}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>Custo</strong>
		</p>
		<p>${pedido.formattedCusto}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>ICMS</strong>
		</p>
		<p>${pedido.formattedCustoIcms}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>Subs. tributária</strong>
		</p>
		<p>${pedido.formattedCustoSt}</p>
	</div>
	<div class="col-md-4">
		<p>
			<strong>Tipo de atendimento</strong>
		</p>
		<p>${pedido.tipoAtendimento.descricao}</p>
	</div>
</div>

<hr />
<div id="actions" class="row">
	<div class="col-md-12">
		<sec:authorize access="hasRole('GERENTE') or hasRole('ATENDENTE')">
			<a class="btn btn-warning"
				href="<c:url value='/pedido/${pedido.id}/edit' />">Alterar</a>
		</sec:authorize>
		<sec:authorize access="hasRole('GERENTE')">
			<button id="delete" class="btn btn-danger" value="${pedido.id}"
				title="Deseja realmente excluir o pedido ( ${pedido.id} )">Excluir</button>
		</sec:authorize>
		<a href="javascript:history.back()" class="btn btn-default">Cancelar</a>
	</div>
</div>

<script type="text/javascript">
	var MAIN_PAGE = '<c:url value='/${mainPage}' />';
	var LIST_PAGE = MAIN_PAGE + '/${pedido.id}/itens';
</script>

<div class="content">
	<%@include file="itens.jsp"%>
</div>

<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.13/css/dataTables.bootstrap.min.css">
<script src="<c:url value='/static/js/casa_das_quentinhas-list.js' />"></script>

<script src="<c:url value='/static/js/casa_das_quentinhas-show.js' />"></script>

<%@include file="../../layouts/delete_modal.jsp"%>