<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row">
	<c:set var="nomeHasBindError">
		<form:errors path="nome" />
	</c:set>
	<div
		class="form-group col-md-6 ${not empty nomeHasBindError?'has-error':''}">
		<label class="control-label" for="nome">Nome</label>

		<form:input type="text" path="nome" id="nome" class="form-control" />
		<div class="help-block with-errors">
			<form:errors path="nome" />
		</div>
	</div>

	<c:set var="sobrenomeHasBindError">
		<form:errors path="sobrenome" />
	</c:set>
	<div
		class="form-group col-md-6 ${not empty sobrenomeHasBindError?'has-error':''}">
		<label class="control-label" for="sobrenome">Sobrenome</label>

		<form:input type="text" path="sobrenome" id="sobrenome"
			class="form-control" />
		<div class="help-block with-errors">
			<form:errors path="sobrenome" />
		</div>
	</div>
</div>

<div class="row">
	<c:set var="emailHasBindError">
		<form:errors path="email" />
	</c:set>
	<div
		class="form-group col-md-5 ${not empty emailHasBindError?'has-error':''}">
		<label class="control-label" for="email">E-mail</label>

		<c:choose>
			<c:when test="${edit}">
				<form:input type="text" path="email" id="email" class="form-control"
					readonly="true" />
			</c:when>
			<c:otherwise>
				<form:input type="text" path="email" id="email" class="form-control" />
				<div class="help-block with-errors">
					<form:errors path="email" />
				</div>
			</c:otherwise>
		</c:choose>
	</div>

	<c:set var="senhaHasBindError">
		<form:errors path="senha" />
	</c:set>
	<div
		class="form-group col-md-5 ${not empty senhaHasBindError?'has-error':''}">
		<label class="control-label" for="senha">Senha</label>

		<form:input type="password" path="senha" id="senha"
			class="form-control" />
		<div class="help-block with-errors">
			<form:errors path="senha" />
		</div>
	</div>

	<c:set var="autorizacoesHasBindError">
		<form:errors path="autorizacoes" />
	</c:set>
	<div
		class="form-group col-md-2 ${not empty autorizacoesHasBindError?'has-error':''}">
		<label class="control-label" for="autorizacoes">Autorização</label>

		<form:select path="autorizacoes" items="${autorizacoes}"
			multiple="true" itemValue="id" itemLabel="papel" class="form-control" />
		<div class="help-block with-errors">
			<form:errors path="autorizacoes" />
		</div>
	</div>
</div>

<input type="hidden" id="lastLoadedPage" name="lastLoadedPage"
	value="${lastLoadedPage}">


<script type="text/javascript">
$(document).ready(function() {
	$('#cancelar').click(event => {
		event.preventDefault();
		window.history.go(-$("#lastLoadedPage").val());
	});
});
</script>