<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Registration Form</title>
<link href="<c:url value='/static/css/bootstrap.css' />"
	rel="stylesheet"></link>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>

<body>
	<div class="generic-container">
		<%@include file="authheader.jsp"%>

		<div class="well lead">User Registration Form</div>
		<form:form method="POST" modelAttribute="usuarioEntity"
			class="form-horizontal">
			<form:input type="hidden" path="id" id="id" />

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="nome">Nome</label>
					<div class="col-md-7">
						<form:input type="text" path="nome" id="nome"
							class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="nome" class="help-inline" />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="sobrenome">Sobrenome</label>
					<div class="col-md-7">
						<form:input type="text" path="sobrenome" id="sobrenome"
							class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="sobrenome" class="help-inline" />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="email">E-mail</label>
					<div class="col-md-7">
						<c:choose>
							<c:when test="${edit}">
								<form:input type="text" path="email" id="email"
									class="form-control input-sm" readonly="true" />
							</c:when>
							<c:otherwise>
								<form:input type="text" path="email" id="email"
									class="form-control input-sm" />
								<div class="has-error">
									<form:errors path="email" class="help-inline" />
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="senha">Senha</label>
					<div class="col-md-7">
						<form:input type="password" path="senha" id="senha"
							class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="senha" class="help-inline" />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="autorizacoes">Autorizações</label>
					<div class="col-md-7">
						<form:select path="autorizacoes" items="${autorizacoes}"
							multiple="true" itemValue="id" itemLabel="papel"
							class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="autorizacoes" class="help-inline" />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-actions floatRight">
					<c:choose>
						<c:when test="${edit}">
							<input type="submit" value="Update"
								class="btn btn-primary btn-sm" /> or <a
								href="<c:url value='/list' />">Cancel</a>
						</c:when>
						<c:otherwise>
							<input type="submit" value="Register"
								class="btn btn-primary btn-sm" /> or <a
								href="<c:url value='/list' />">Cancel</a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</form:form>
	</div>
</body>
</html>