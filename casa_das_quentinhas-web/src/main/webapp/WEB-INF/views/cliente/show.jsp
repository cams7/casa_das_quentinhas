<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<fmt:setLocale value="pt-BR" />

<h3 class="page-header">
	Visualizar Cliente <span class="label label-default">${cliente.id}</span>
</h3>

<div class="row">
	<div class="col-md-6">
		<p>
			<strong>Nome</strong>
		</p>
		<p>${cliente.nome}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>CPF</strong>
		</p>
		<p>${cliente.formattedCpf}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>Data de nascimento</strong>
		</p>
		<p>${cliente.formattedNascimento}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>Cadastro</strong>
		</p>
		<p>
			<fmt:formatDate type="both" dateStyle="short" timeStyle="short"
				value="${cliente.manutencao.cadastro}" />
		</p>
	</div>
</div>

<div class="row">
	<div class="col-md-6">
		<p>
			<strong>E-mail</strong>
		</p>
		<p>${cliente.contato.email}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>Telefone</strong>
		</p>
		<p>${cliente.contato.formattedTelefone}</p>
	</div>
	<div class="col-md-4">
		<p>
			<strong>Tipo de contribuinte</strong>
		</p>
		<p>${cliente.tipoContribuinte.descricao}</p>
	</div>
</div>

<div class="row">
	<div class="col-md-6">
		<p>
			<strong>Cidade</strong>
		</p>
		<p>${cliente.cidade.nomeWithEstadoSigla}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>CEP</strong>
		</p>
		<p>${cliente.endereco.formattedCep}</p>
	</div>
	<div class="col-md-4">
		<p>
			<strong>Bairro</strong>
		</p>
		<p>${cliente.endereco.bairro}</p>
	</div>
</div>

<div class="row">
	<div class="col-md-8">
		<p>
			<strong>Logradouro</strong>
		</p>
		<p>${cliente.endereco.logradouro}</p>
	</div>
	<div class="col-md-4">
		<p>
			<strong>Número do imóvel</strong>
		</p>
		<p>${cliente.endereco.numeroImovel}</p>
	</div>
</div>

<div class="row">
	<div class="col-md-6">
		<p>
			<strong>Complemento</strong>
		</p>
		<p>${cliente.endereco.complemento}</p>
	</div>
	<div class="col-md-6">
		<p>
			<strong>Ponto de referência</strong>
		</p>
		<p>${cliente.endereco.pontoReferencia}</p>
	</div>
</div>

<hr />
<div id="actions" class="row">
	<div class="col-md-12">
		<sec:authorize access="hasRole('GERENTE') or hasRole('ATENDENTE')">
			<a class="btn btn-warning"
				href="<c:url value='/cliente/${cliente.id}/edit' />">Alterar</a>
		</sec:authorize>
		<sec:authorize access="hasRole('GERENTE')">
			<button id="delete" class="btn btn-danger" value="${cliente.id}"
				title="Deseja realmente excluir o(a) cliente ( ${cliente.nomeWithCpf} )">Excluir</button>
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
	var LIST_PAGE = MAIN_PAGE + '/${cliente.id}/pedidos';
	var DELETE_PAGE = '<c:url value='/pedido' />';
</script>

<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.13/css/dataTables.bootstrap.min.css">

<script src="<c:url value='/static/js/casa_das_quentinhas-show.js' />"></script>
<script src="<c:url value='/static/js/casa_das_quentinhas-list.js' />"></script>

<%@include file="../../layouts/delete_modal.jsp"%>