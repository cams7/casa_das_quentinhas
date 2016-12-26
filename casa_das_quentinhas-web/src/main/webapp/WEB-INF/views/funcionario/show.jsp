<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<h3 class="page-header">
	Visualizar Funcionário <span class="label label-default">${funcionario.id}</span>
</h3>

<div class="row">
	<div class="col-md-10">
		<p>
			<strong>E-mail</strong>
		</p>
		<p>${funcionario.usuario.email}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>Função</strong>
		</p>
		<p>${funcionario.funcao.nome}</p>
	</div>
</div>

<hr />
<div id="actions" class="row">
	<div class="col-md-12">
		<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
			<a class="btn btn-warning"
				href="<c:url value='/funcionario/${funcionario.id}/edit' />">Alterar</a>
		</sec:authorize>
		<sec:authorize access="hasRole('ADMIN')">
			<a class="btn btn-danger"
				href="<c:url value='/funcionario/${funcionario.id}/delete' />">Excluir</a>
		</sec:authorize>
		<a href="javascript:history.back()" class="btn btn-default">Cancelar</a>
	</div>
</div>