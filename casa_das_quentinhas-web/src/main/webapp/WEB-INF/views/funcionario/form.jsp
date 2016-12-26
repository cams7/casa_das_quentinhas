<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row">
	<c:set var="emailError">
		<form:errors path="usuario.email" />
	</c:set>
	<div
		class="form-group col-md-4 ${not empty emailError ? 'has-error' : ''}">
		<label class="control-label" for="usuario.email">E-mail</label>

		<c:choose>
			<c:when test="${edit}">
				<form:input type="text" path="usuario.email" id="usuario.email"
					class="form-control" readonly="true" />
			</c:when>
			<c:otherwise>
				<form:input type="text" path="usuario.email" id="usuario.email"
					class="form-control" />
				<div class="help-block with-errors">${emailError}</div>
			</c:otherwise>
		</c:choose>
	</div>

	<c:set var="senhaError">
		<form:errors path="usuario.senha" />
	</c:set>
	<div
		class="form-group col-md-3 ${not empty senhaError ? 'has-error' : ''}">
		<label class="control-label" for="usuario.senha">Senha</label>

		<form:input type="password" path="usuario.senha" id="usuario.senha"
			class="form-control" />
		<div class="help-block with-errors">${senhaError}</div>
	</div>

	<c:set var="confirmacaoError">
		<form:errors path="usuario.confirmacaoSenha" />
	</c:set>
	<div
		class="form-group col-md-3 ${not empty confirmacaoError ? 'has-error' : ''}">
		<label class="control-label" for="usuario.confirmacaoSenha">Confirmação</label>

		<form:input type="password" path="usuario.confirmacaoSenha"
			id="usuario.confirmacaoSenha" class="form-control" />
		<div class="help-block with-errors">${confirmacaoError}</div>
	</div>

	<c:set var="funcaoError">
		<form:errors path="funcao" />
	</c:set>
	<div
		class="form-group col-md-2 ${not empty funcaoError?'has-error':''}">
		<label class="control-label" for="funcao">Função</label>

		<form:select path="funcao" items="${funcionarioFuncoes}"
			multiple="true" itemValue="funcao" itemLabel="nome"
			class="form-control" />
		<div class="help-block with-errors">${funcaoError}</div>
	</div>
</div>

<input type="hidden" id="lastLoadedPage" name="lastLoadedPage"
	value="${lastLoadedPage}">

<script src="<c:url value='/static/js/casa_das_quentinhas-form.js' />"></script>