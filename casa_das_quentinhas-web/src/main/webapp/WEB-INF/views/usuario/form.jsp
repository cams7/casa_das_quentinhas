<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row">
	<c:set var="emailError">
		<form:errors path="email" />
	</c:set>
	<div
		class="form-group col-md-4 ${not empty emailError ? 'has-error' : ''}">
		<label class="control-label" for="email">E-mail</label>

		<c:choose>
			<c:when test="${edit}">
				<form:input type="text" path="email" id="email" class="form-control"
					readonly="true" />
			</c:when>
			<c:otherwise>
				<form:input type="text" path="email" id="email" class="form-control" />
				<div class="help-block with-errors">${emailError}</div>
			</c:otherwise>
		</c:choose>
	</div>

	<c:set var="senhaError">
		<form:errors path="senha" />
	</c:set>
	<div
		class="form-group col-md-3 ${not empty senhaError ? 'has-error' : ''}">
		<label class="control-label" for="senha">Senha</label>

		<form:input type="password" path="senha" id="senha"
			class="form-control" />
		<div class="help-block with-errors">${senhaError}</div>
	</div>

	<c:set var="confirmacaoError">
		<form:errors path="confirmacaoSenha" />
	</c:set>
	<div
		class="form-group col-md-3 ${not empty confirmacaoError ? 'has-error' : ''}">
		<label class="control-label" for="confirmacaoSenha">Confirmação</label>

		<form:input type="password" path="confirmacaoSenha"
			id="confirmacaoSenha" class="form-control" />
		<div class="help-block with-errors">${confirmacaoError}</div>
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

<script src="<c:url value='/static/js/casa_das_marmitas-form.js' />"></script>