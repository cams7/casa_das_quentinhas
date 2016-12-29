<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h3 class="page-header">Editar Empresa</h3>

<form:form method="POST" modelAttribute="empresa">
	<form:input type="hidden" path="id" />
	<form:input type="hidden" path="cadastro.time" />
	<form:input type="hidden" path="usuarioAcesso.id" />
	<form:input type="hidden" path="usuarioAcesso.tipo" />
	<form:input type="hidden" path="usuarioCadastro.id" />		

	<%@include file="form.jsp"%>

	<hr />
	<div id="actions" class="row">
		<div class="col-md-12">
			<input type="submit" value="Alterar" class="btn btn-primary" />
			<button id="cancelar" class="btn btn-default">Cancelar</button>
		</div>
	</div>
</form:form>