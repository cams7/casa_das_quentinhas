<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<fmt:setLocale value="pt-BR" />

<h3 class="page-header">
	Visualizar Pedido <span class="label label-default">${pedido.id}</span>
</h3>
<%@include file="../../layouts/alert_message.jsp"%>
<div class="row">
	<div class="col-md-6">
		<p>
			<strong>${pedido.tipoCliente eq 'PESSOA_JURIDICA' ? 'Empresa' : 'Cliente' }</strong>
		</p>
		<p>
			<c:choose>
				<c:when test="${pedido.tipoCliente eq 'PESSOA_JURIDICA'}">
					<a href="<c:url value='/empresa/${pedido.cliente.id}' />">${pedido.cliente.nome}</a>
				</c:when>
				<c:otherwise>
					<a href="<c:url value='/cliente/${pedido.cliente.id}' />">${pedido.cliente.nome}</a>
				</c:otherwise>
			</c:choose>
		</p>
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
			<strong>Data do pedido</strong>
		</p>
		<p>
			<fmt:formatDate type="both" dateStyle="short" timeStyle="short"
				value="${pedido.manutencao.cadastro}" />
		</p>
	</div>
</div>
<div class="row">
	<div class="col-md-6">
		<p>
			<strong>Entregador(a)</strong>
		</p>
		<p>
			<c:if test="${pedido.entregador.id != null}">
				<a href="<c:url value='/entregador/${pedido.entregador.id}' />">${pedido.entregador.nome}</a>
			</c:if>
		</p>
	</div>
	<div class="col-md-3">
		<p>
			<strong>Tipo de atendimento</strong>
		</p>
		<p>${pedido.tipoAtendimento.descricao}</p>
	</div>
	<div class="col-md-3">
		<p>
			<strong>Situação</strong>
		</p>
		<p>${pedido.situacao.descricao}</p>
	</div>
</div>
<div class="row">
	<div class="col-md-3">
		<p>
			<strong>Quantidade</strong>
		</p>
		<p>${pedido.quantidade}</p>
	</div>
	<div class="col-md-3">
		<p>
			<strong>Custo</strong>
		</p>
		<p>
			<fmt:formatNumber value="${pedido.custo}" type="currency" />
		</p>
	</div>
	<div class="col-md-3">
		<p>
			<strong>ICMS</strong>
		</p>
		<p>
			<fmt:formatNumber value="${pedido.custoIcms}" type="currency" />
		</p>
	</div>
	<div class="col-md-3">
		<p>
			<strong>Subs. tributária</strong>
		</p>
		<p>
			<fmt:formatNumber value="${pedido.custoSt}" type="currency" />
		</p>
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
				title="Deseja realmente excluir o pedido ( ${pedido.idWithCadastro} )">Excluir</button>
		</sec:authorize>
		<button id="cancelar" class="btn btn-default">Cancelar</button>
	</div>
</div>

<div class="content">
	<%@include file="itens.jsp"%>
</div>

<script type="text/javascript">
	var MAIN_PAGE = '<c:url value='/${mainPage}' />';
	var LIST_PAGE = MAIN_PAGE + '/${pedido.id}/itens';
	var ITEM_PAGE = MAIN_PAGE + '/${pedido.id}/item';
</script>

<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.13/css/dataTables.bootstrap.min.css">

<script src="<c:url value='/static/js/casa_das_quentinhas-show.js' />"></script>
<script src="<c:url value='/static/js/casa_das_quentinhas-list.js' />"></script>

<%@include file="../../layouts/delete_modal.jsp"%>