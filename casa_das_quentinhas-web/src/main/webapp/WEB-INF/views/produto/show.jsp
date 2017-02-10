<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<fmt:setLocale value="pt-BR" />

<h3 class="page-header">
	Visualizar Produto <span class="label label-default">${produto.id}</span>
</h3>
<%@include file="../../layouts/alert_message.jsp"%>
<div class="row">
	<div class="col-md-6">
		<p>
			<strong>Nome</strong>
		</p>
		<p>${produto.nome}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>Custo</strong>
		</p>
		<p>
			<fmt:formatNumber value="${produto.custo}" type="currency" />
		</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>Tamanho</strong>
		</p>
		<p>${produto.tamanho.descricao}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>Cadastro</strong>
		</p>
		<p>
			<fmt:formatDate type="both" dateStyle="short" timeStyle="short"
				value="${produto.manutencao.cadastro}" />
		</p>
	</div>
</div>

<div class="row">
	<div class="col-md-8">
		<p>
			<strong>Ingredientes</strong>
		</p>
		<p>${produto.ingredientes}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>NCM</strong>
		</p>
		<p>${produto.codigoNcm}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>CEST</strong>
		</p>
		<p>${produto.codigoCest}</p>
	</div>
</div>

<input type="hidden" id="previousPage" name="previousPage"
	value="${not empty param.sucessMessage?'3':'1'}">
	
<hr />
<div id="actions" class="row">
	<div class="col-md-12">
		<sec:authorize access="hasRole('GERENTE') or hasRole('ATENDENTE')">
			<a class="btn btn-warning"
				href="<c:url value='/produto/${produto.id}/edit' />">Alterar</a>
		</sec:authorize>
		<sec:authorize access="hasRole('GERENTE')">
			<button id="delete" class="btn btn-danger" value="${produto.id}"
				title="Deseja realmente excluir o produto ( ${produto.nomeWithTamanho} )">Excluir</button>
		</sec:authorize>
		<button id="cancelar" class="btn btn-default">Cancelar</button>
	</div>
</div>



<div class="content">
	<%@include file="pedidos.jsp"%>
</div>

<script type="text/javascript">
	var MAIN_PAGE = '<c:url value='/${mainPage}' />';
	var LIST_PAGE = MAIN_PAGE + '/${produto.id}/pedidos';
	var DELETE_PAGE = '<c:url value='/pedido' />';
</script>

<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.13/css/dataTables.bootstrap.min.css">

<script src="<c:url value='/static/js/casa_das_quentinhas-show.js' />"></script>
<script src="<c:url value='/static/js/casa_das_quentinhas-list.js' />"></script>

<%@include file="../../layouts/delete_modal.jsp"%>