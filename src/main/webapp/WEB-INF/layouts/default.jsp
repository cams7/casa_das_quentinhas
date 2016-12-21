<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>

<head>
<!--meta charset="utf-8"-->
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title><tiles:getAsString name="title" /></title>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/static/css/casa_das_marmitas.css' />">

<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="<c:url value='/static/js/jquery.maskedinput.min.js' />"></script>
<script src="<c:url value='/static/js/jquery.maskMoney.js' />"></script>

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script src="<c:url value='/static/js/casa_das_marmitas.js' />"></script>
</head>

<body>
	<tiles:insertAttribute name="header" />

	<!-- Modal -->
	<div class="modal fade" id="delete-modal" tabindex="-1" role="dialog"
		aria-labelledby="modalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Fechar">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalLabel">Excluir Item</h4>
				</div>
				<div class="modal-body">Deseja realmente excluir este item?</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary">Sim</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">N&atilde;o</button>
				</div>
			</div>
		</div>
	</div>

	<div id="main" class="container-fluid" style="margin-top: 50px">
		<tiles:insertAttribute name="body" />
	</div>
</body>
</html>