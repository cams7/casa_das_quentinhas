<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h3 class="page-header">Adicionar Pedido</h3>

<form:form method="POST" modelAttribute="pedido" id="pedido_form">

	<%@include file="form.jsp"%>

	<hr />
	<div id="actions" class="row">
		<div class="col-md-12">
			<button id="item_add" class="btn btn-success">Novo Item</button>
			<button id="itens_refresh" class="btn btn-default">Atualizar
				Itens</button>
			<input type="submit" value="Salvar" class="btn btn-primary" />
			<button id="cancelar" class="btn btn-default">Cancelar</button>
		</div>
	</div>
</form:form>

<script type="text/javascript">
	var LIST_PAGE = MAIN_PAGE + '/0/itens';
	var ITEM_PAGE = MAIN_PAGE + '/0/item';
</script>

<div class="content">
	<%@include file="itens.jsp"%>
</div>

<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.13/css/dataTables.bootstrap.min.css">
<script src="<c:url value='/static/js/casa_das_quentinhas-list.js' />"></script>

<script src="<c:url value='/static/js/validator.min.js' />"></script>
<script
	src="<c:url value='/static/js/casa_das_quentinhas-pedido_item.js' />"></script>
<%@include file="item_modal.jsp"%>