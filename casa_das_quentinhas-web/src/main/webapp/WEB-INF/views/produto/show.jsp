<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<h3 class="page-header">
	Visualizar Produto <span class="label label-default">${produto.id}</span>
</h3>

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
		<p>${produto.formattedCusto}</p>
	</div>
	<div class="col-md-4">
		<p>
			<strong>Tamanho</strong>
		</p>
		<p>${produto.tamanho.descricao}</p>
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

<hr />
<div id="actions" class="row">
	<div class="col-md-12">
		<sec:authorize access="hasRole('GERENTE') or hasRole('ATENDENTE')">
			<a class="btn btn-warning"
				href="<c:url value='/produto/${produto.id}/edit' />">Alterar</a>
		</sec:authorize>
		<sec:authorize access="hasRole('GERENTE')">
			<button id="delete" class="btn btn-danger" value="${produto.id}">Excluir</button>
		</sec:authorize>
		<a href="javascript:history.back()" class="btn btn-default">Cancelar</a>
	</div>
</div>

<script type="text/javascript">
	var MAIN_PAGE = '<c:url value='/${mainPage}' />';
	var MODAL_LABEL = 'Excluir Produto';
	var MODAL_BODY = 'Deseja realmente excluir este Produto?';
</script>
<script src="<c:url value='/static/js/casa_das_quentinhas-show.js' />"></script>

<%@include file="../../layouts/delete_modal.jsp"%>