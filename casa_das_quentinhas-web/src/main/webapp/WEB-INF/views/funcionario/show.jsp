<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<fmt:setLocale value="pt-BR" />

<h3 class="page-header">
	Visualizar Funcionário <span class="label label-default">${funcionario.id}</span>
</h3>
<%@include file="../../layouts/alert_message.jsp"%>
<div class="row">
	<div class="col-md-6">
		<p>
			<strong>Nome</strong>
		</p>
		<p>${funcionario.nome}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>Celular</strong>
		</p>
		<p>${funcionario.formattedCelular}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>Função</strong>
		</p>
		<p>${funcionario.funcao.descricao}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>Cadastro</strong>
		</p>
		<p>
			<fmt:formatDate type="both" dateStyle="short" timeStyle="short"
				value="${funcionario.manutencao.cadastro}" />
		</p>
	</div>
</div>
<div class="row">
	<div class="col-md-6">
		<p>
			<strong>E-mail</strong>
		</p>
		<p>${funcionario.usuario.email}</p>
	</div>
	<div class="col-md-3">
		<p>
			<strong>CPF</strong>
		</p>
		<p>${funcionario.formattedCpf}</p>
	</div>
	<div class="col-md-3">
		<p>
			<strong>RG</strong>
		</p>
		<p>${funcionario.rg}</p>
	</div>
</div>

<hr />
<div id="actions" class="row">
	<div class="col-md-12">
		<sec:authorize access="hasRole('GERENTE') or hasRole('ATENDENTE')">
			<a class="btn btn-warning"
				href="<c:url value='/funcionario/${funcionario.id}/edit' />">Alterar</a>
		</sec:authorize>
		<sec:authorize access="hasRole('GERENTE')">
			<button id="delete" class="btn btn-danger" value="${funcionario.id}"
				title="Deseja realmente excluir o(a) funcionário(a) ( ${funcionario.nomeWithCpf} )">Excluir</button>
		</sec:authorize>
		<a href="javascript:history.back()" class="btn btn-default">Cancelar</a>
	</div>
</div>

<c:if test="${count > 0}">
	<h3 class="page-header">Pedidos</h3>
</c:if>
<div class="content">
	<%@include file="../pedido/list.jsp"%>
</div>

<script type="text/javascript">
	var MAIN_PAGE = '<c:url value='/${mainPage}' />';
	var LIST_PAGE = MAIN_PAGE + '/${funcionario.id}/pedidos';
	var DELETE_PAGE = '<c:url value='/pedido' />';
</script>

<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.13/css/dataTables.bootstrap.min.css">

<script src="<c:url value='/static/js/casa_das_quentinhas-show.js' />"></script>
<script src="<c:url value='/static/js/casa_das_quentinhas-list.js' />"></script>

<%@include file="../../layouts/delete_modal.jsp"%>