<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<fmt:setLocale value="pt-BR" />

<h3 class="page-header">
	Visualizar Empresa <span class="label label-default">${empresa.id}</span>
</h3>
<%@include file="../../layouts/alert_message.jsp"%>
<div class="row">
	<div class="col-md-4">
		<p>
			<strong>Razão social</strong>
		</p>
		<p>${empresa.razaoSocial}</p>
	</div>
	<div class="col-md-4">
		<p>
			<strong>Nome fantasia</strong>
		</p>
		<p>${empresa.nomeFantasia}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>CNPJ</strong>
		</p>
		<p>${empresa.formattedCnpj}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>Cadastro</strong>
		</p>
		<p>
			<fmt:formatDate type="both" dateStyle="short" timeStyle="short"
				value="${empresa.manutencao.cadastro}" />
		</p>
	</div>
</div>

<div class="row">
	<div class="col-md-4">
		<p>
			<strong>E-mail</strong>
		</p>
		<p>${empresa.contato.email}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>Telefone</strong>
		</p>
		<p>${empresa.contato.formattedTelefone}</p>
	</div>
	<div class="col-md-4">
		<p>
			<strong>Regime tributário</strong>
		</p>
		<p>${empresa.regimeTributario.descricao}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>Tipo de empresa</strong>
		</p>
		<p>${empresa.tipo.descricao}</p>
	</div>
</div>

<div class="row">
	<div class="col-md-3">
		<p>
			<strong>Inscrição estadual</strong>
		</p>
		<p>${empresa.inscricaoEstadual}</p>
	</div>
	<div class="col-md-3">
		<p>
			<strong>Inscrição estadual do subst. tributário</strong>
		</p>
		<p>${empresa.inscricaoEstadualST}</p>
	</div>
	<div class="col-md-3">
		<p>
			<strong>Inscrição municipal</strong>
		</p>
		<p>${empresa.inscricaoMuncipal}</p>
	</div>
	<div class="col-md-3">
		<p>
			<strong>Códido CNAE</strong>
		</p>
		<p>${empresa.codigoCnae}</p>
	</div>
</div>

<div class="row">
	<div class="col-md-6">
		<p>
			<strong>Cidade</strong>
		</p>
		<p>${empresa.cidade.nomeWithEstadoSigla}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>CEP</strong>
		</p>
		<p>${empresa.endereco.formattedCep}</p>
	</div>
	<div class="col-md-4">
		<p>
			<strong>Bairro</strong>
		</p>
		<p>${empresa.endereco.bairro}</p>
	</div>
</div>

<div class="row">
	<div class="col-md-8">
		<p>
			<strong>Logradouro</strong>
		</p>
		<p>${empresa.endereco.logradouro}</p>
	</div>
	<div class="col-md-4">
		<p>
			<strong>Número do imóvel</strong>
		</p>
		<p>${empresa.endereco.numeroImovel}</p>
	</div>
</div>

<div class="row">
	<div class="col-md-6">
		<p>
			<strong>Complemento</strong>
		</p>
		<p>${empresa.endereco.complemento}</p>
	</div>
	<div class="col-md-6">
		<p>
			<strong>Ponto de referência</strong>
		</p>
		<p>${empresa.endereco.pontoReferencia}</p>
	</div>
</div>

<hr />
<div id="actions" class="row">
	<div class="col-md-12">
		<sec:authorize access="hasRole('GERENTE') or hasRole('ATENDENTE')">
			<a class="btn btn-warning"
				href="<c:url value='/empresa/${empresa.id}/edit' />">Alterar</a>
		</sec:authorize>
		<sec:authorize access="hasRole('GERENTE')">
			<button id="delete" class="btn btn-danger" value="${empresa.id}"
				title="Deseja realmente excluir a empresa ( ${empresa.razaoSocialWithCnpj} )">Excluir</button>
		</sec:authorize>
		<button id="cancelar" class="btn btn-default">Cancelar</button>
	</div>
</div>

<c:choose>
	<c:when test="${empresa.tipo eq 'CLIENTE'}">
		<c:if test="${count > 0}">
			<h3 class="page-header">Pedidos</h3>
		</c:if>
		<div class="content">
			<%@include file="../pedido/list.jsp"%>
		</div>

		<script type="text/javascript">
			var MAIN_PAGE = '<c:url value='/${mainPage}' />';
			var LIST_PAGE = MAIN_PAGE + '/${empresa.id}/pedidos';
			var DELETE_PAGE = '<c:url value='/pedido' />';
		</script>
	</c:when>
	<c:otherwise>
		<c:if test="${count > 0}">
			<h3 class="page-header">Entregadores</h3>
		</c:if>
		<div class="content">
			<%@include file="../entregador/list.jsp"%>
		</div>

		<script type="text/javascript">
			var MAIN_PAGE = '<c:url value='/${mainPage}' />';
			var LIST_PAGE = MAIN_PAGE + '/${empresa.id}/entregadores';
			var DELETE_PAGE = '<c:url value='/entregador' />';
		</script>
	</c:otherwise>
</c:choose>

<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.13/css/dataTables.bootstrap.min.css">

<script src="<c:url value='/static/js/casa_das_quentinhas-show.js' />"></script>
<script src="<c:url value='/static/js/casa_das_quentinhas-list.js' />"></script>

<%@include file="../../layouts/delete_modal.jsp"%>