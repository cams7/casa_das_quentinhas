<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<h3 class="page-header">
	Visualizar Cliente <span class="label label-default">${cliente.id}</span>
</h3>

<div class="row">
	<div class="col-md-8">
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
			<button id="delete" class="btn btn-danger" value="${cliente.id}">Excluir</button>
		</sec:authorize>
		<a href="javascript:history.back()" class="btn btn-default">Cancelar</a>
	</div>
</div>

<script type="text/javascript">
	var MAIN_PAGE = '<c:url value='/${mainPage}' />';
	var MODAL_LABEL = 'Excluir Cliente';
	var MODAL_BODY = 'Deseja realmente excluir este Cliente?';
</script>
<script src="<c:url value='/static/js/casa_das_quentinhas-show.js' />"></script>

<%@include file="../../layouts/delete_modal.jsp"%>