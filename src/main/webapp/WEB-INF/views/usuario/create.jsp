<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h3 class="page-header">Adicionar Usuário</h3>

<form:form method="POST" modelAttribute="usuario">

	<%@include file="form.jsp" %>

	<hr />
	<div id="actions" class="row">
		<div class="col-md-12">			
			<input type="submit" value="Salvar" class="btn btn-primary" />
			<a href="<c:url value='/usuario/list' />" class="btn btn-default">Cancelar</a>
		</div>
	</div>
</form:form>
