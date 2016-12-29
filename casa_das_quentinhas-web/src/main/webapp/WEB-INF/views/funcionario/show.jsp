<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<h3 class="page-header">
	Visualizar Funcionário <span class="label label-default">${funcionario.id}</span>
</h3>

<div class="row">
	<div class="col-md-4">
		<p>
			<strong>Nome</strong>
		</p>
		<p>${funcionario.nome}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>Função</strong>
		</p>
		<p>${funcionario.funcao.descricao}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>Celular</strong>
		</p>
		<p>${funcionario.formattedCelular}</p>
	</div>
	<div class="col-md-4">
		<p>
			<strong>Empresa</strong>
		</p>
		<p>${funcionario.empresa.razaoSocialWithCnpj}</p>
	</div>
</div>
<div class="row">	
	<div class="col-md-4">
		<p>
			<strong>E-mail</strong>
		</p>
		<p>${funcionario.usuario.email}</p>
	</div>
	<div class="col-md-4">
		<p>
			<strong>CPF</strong>
		</p>
		<p>${funcionario.formattedCpf}</p>
	</div>
	<div class="col-md-4">
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
			<button id="delete" class="btn btn-danger" value="${funcionario.id}">Excluir</button>
		</sec:authorize>
		<a href="javascript:history.back()" class="btn btn-default">Cancelar</a>
	</div>
</div>

<script type="text/javascript">
	var MAIN_PAGE = '<c:url value='/${mainPage}' />';
	var MODAL_LABEL = 'Excluir Funcionário';
	var MODAL_BODY = 'Deseja realmente excluir este Funcionário?';
</script>
<script src="<c:url value='/static/js/casa_das_quentinhas-show.js' />"></script>

<%@include file="../../layouts/delete_modal.jsp"%>