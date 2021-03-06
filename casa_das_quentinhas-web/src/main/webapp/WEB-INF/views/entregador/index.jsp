<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div id="top" class="row">
	<div class="col-sm-3">
		<h2>Entregadores</h2>
	</div>

	<div class="col-sm-6">
		<div class="input-group h2">
			<input name="data[search]" class="form-control" id="search_query"
				type="text"
				placeholder="Nome / CPF / E-mail / Celular / Nome da Empresa / CNPJ da Empresa">
			<span class="input-group-btn">
				<button id="search_btn" class="btn btn-success" type="submit">
					<span class="glyphicon glyphicon-search"></span>
				</button>
			</span>
		</div>
	</div>

	<div class="col-sm-3 h2">
		<button id="report" class="btn btn-default">
			<span class="glyphicon glyphicon-file"></span>Relatório
		</button>
		<sec:authorize access="hasRole('GERENTE')">
			<a href="<c:url value='/entregador/create' />"
				class="btn btn-primary">Novo Entregador</a>
		</sec:authorize>
	</div>
</div>

<hr />
<%@include file="../../layouts/alert_message.jsp"%>
<div class="content">
	<%@include file="list.jsp"%>
</div>

<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.13/css/dataTables.bootstrap.min.css">

<script type="text/javascript">
	var MAIN_PAGE = '<c:url value='/${mainPage}' />';
	var LIST_PAGE = MAIN_PAGE + '/list';
	var DELETE_PAGE = MAIN_PAGE;
</script>
<script src="<c:url value='/static/js/casa_das_quentinhas-list.js' />"></script>

<%@include file="../../layouts/delete_modal.jsp"%>
<%@include file="../../layouts/report_modal.jsp"%>