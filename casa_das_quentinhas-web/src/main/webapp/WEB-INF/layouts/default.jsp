<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<meta name="_csrf" content="${_csrf.token}" />
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}" />

<title><tiles:getAsString name="title" /></title>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<link rel="stylesheet" type="text/css"
	href="<c:url value='/static/css/casa_das_quentinhas.css' />">

<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="<c:url value='/static/js/jquery.maskedinput.min.js' />"></script>
<script src="<c:url value='/static/js/jquery.maskMoney.js' />"></script>

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script src="<c:url value='/static/js/casa_das_quentinhas.js' />"></script>
</head>

<body>
	<tiles:insertAttribute name="header" />

	<div class="container-fluid" style="margin-top: 50px">
		<tiles:insertAttribute name="body" />
	</div>

	<div id="loading" class="modal fade" data-backdrop="static"
		data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true"
		style="padding-top: 15%; overflow-y: visible; display: none;">
		<div class="modal-dialog modal-m">
			<div class="modal-content">
				<div class="modal-header">
					<h3 style="margin: 0;">Carregando</h3>
				</div>
				<div class="modal-body">
					<div class="progress progress-striped active"
						style="margin-bottom: 0;">
						<div class="progress-bar" style="width: 100%"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>