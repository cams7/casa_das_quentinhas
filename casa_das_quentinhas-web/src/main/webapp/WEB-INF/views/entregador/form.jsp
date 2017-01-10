<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<sec:authorize access="hasRole('GERENTE')" var="isGerente"></sec:authorize>

<form:input type="hidden" path="funcao" />

<div class="row">
	<c:set var="nomeError">
		<form:errors path="nome" />
	</c:set>
	<div
		class="form-group col-md-4 ${not empty nomeError ? 'has-error' : ''}">
		<label class="control-label" for="nome">Nome</label>
		<form:input type="text" path="nome" id="nome" class="form-control"
			maxlength="60" />
		<div class="help-block with-errors">${nomeError}</div>
	</div>

	<c:set var="celularError">
		<form:errors path="celular" />
	</c:set>
	<div
		class="form-group col-md-2 ${not empty celularError ? 'has-error' : ''}">
		<label class="control-label" for="celular">Celular</label>
		<form:input type="text" path="celular" id="celular"
			class="form-control" placeholder="(99) 99999-9999" maxlength="15" />
		<div class="help-block with-errors">${celularError}</div>
	</div>

	<c:set var="empresaError">
		<form:errors path="empresa.id" />
	</c:set>
	<div
		class="form-group col-md-6 ${not empty empresaError ? 'has-error' : ''}">
		<label class="control-label" for="empresa.razaoSocial">Empresa</label>
		<form:input type="text" path="empresa.razaoSocial" id="empresa"
			class="form-control" maxlength="60" placeholder="Nome / CNPJ"
			value="${entregador.empresa.razaoSocialWithCnpj}" />
		<form:input type="hidden" path="empresa.id" id="empresa_id" />
		<div class="help-block with-errors">${empresaError}</div>
	</div>
</div>
<div class="row">
	<c:set var="emailError">
		<form:errors path="usuario.email" />
	</c:set>
	<div
		class="form-group col-md-6 ${not empty emailError ? 'has-error' : ''}">
		<label class="control-label" for="usuario.email">E-mail</label>

		<form:input type="text" path="usuario.email" id="email"
			class="form-control" readonly="${edit and not isGerente}"
			maxlength="50" />
		<div class="help-block with-errors">${emailError}</div>
	</div>

	<c:set var="cpfError">
		<form:errors path="cpf" />
	</c:set>
	<div
		class="form-group col-md-3 ${not empty cpfError ? 'has-error' : ''}">
		<label class="control-label" for="cpf">CPF</label>
		<form:input type="text" path="cpf" id="cpf" class="form-control"
			placeholder="999.999.999-99" maxlength="14" />
		<div class="help-block with-errors">${cpfError}</div>
	</div>

	<c:set var="rgError">
		<form:errors path="rg" />
	</c:set>
	<div
		class="form-group col-md-3 ${not empty rgError ? 'has-error' : ''}">
		<label class="control-label" for="rg">RG</label>
		<form:input type="text" path="rg" id="rg" class="form-control"
			placeholder="99999999" maxlength="9" />
		<div class="help-block with-errors">${rgError}</div>
	</div>
</div>
<div class="row">
	<c:set var="senhaError">
		<form:errors path="usuario.senha" />
	</c:set>
	<div
		class="form-group col-md-6 ${not empty senhaError ? 'has-error' : ''}">
		<label class="control-label" for="usuario.senha">Senha</label>

		<form:input type="password" path="usuario.senha" id="senha"
			class="form-control" />
		<div class="help-block with-errors">${senhaError}</div>
	</div>

	<c:set var="confirmacaoError">
		<form:errors path="usuario.confirmacaoSenha" />
	</c:set>
	<div
		class="form-group col-md-6 ${not empty confirmacaoError ? 'has-error' : ''}">
		<label class="control-label" for="usuario.confirmacaoSenha">Confirmação</label>

		<form:input type="password" path="usuario.confirmacaoSenha"
			id="confirmacaoSenha" class="form-control" />
		<div class="help-block with-errors">${confirmacaoError}</div>
	</div>
</div>

<input type="hidden" id="lastLoadedPage" name="lastLoadedPage"
	value="${lastLoadedPage}">

<script src="<c:url value='/static/js/casa_das_quentinhas-form.js' />"></script>
<script type="text/javascript">
	var MAIN_PAGE = '<c:url value='/${mainPage}' />';
</script>
<script
	src="<c:url value='/static/js/casa_das_quentinhas-funcionario-form.js' />"></script>