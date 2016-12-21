<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row">
	<div class="form-group col-md-6">
		<label class="control-label" for="nome">Nome</label>

		<form:input type="text" path="nome" id="nome" class="form-control" />
		<div class="has-error">
			<form:errors path="nome" class="help-inline" />
		</div>
	</div>

	<div class="form-group col-md-6">
		<label class="control-label" for="sobrenome">Sobrenome</label>

		<form:input type="text" path="sobrenome" id="sobrenome"
			class="form-control" />
		<div class="has-error">
			<form:errors path="sobrenome" class="help-inline" />
		</div>
	</div>
</div>

<div class="row">
	<div class="form-group col-md-5">
		<label class="control-label" for="email">E-mail</label>

		<c:choose>
			<c:when test="${edit}">
				<form:input type="text" path="email" id="email" class="form-control"
					readonly="true" />
			</c:when>
			<c:otherwise>
				<form:input type="text" path="email" id="email" class="form-control" />
				<div class="has-error">
					<form:errors path="email" class="help-inline" />
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	<div class="form-group col-md-5">
		<label class="control-label" for="senha">Senha</label>

		<form:input type="password" path="senha" id="senha"
			class="form-control" />
		<div class="has-error">
			<form:errors path="senha" class="help-inline" />
		</div>
	</div>
	<div class="form-group col-md-2">
		<label class="control-label" for="autorizacoes">Autorização</label>

		<form:select path="autorizacoes" items="${autorizacoes}"
			multiple="true" itemValue="id" itemLabel="papel" class="form-control" />
		<div class="has-error">
			<form:errors path="autorizacoes" class="help-inline" />
		</div>
	</div>
</div>