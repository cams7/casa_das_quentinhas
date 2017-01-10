<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="row">
	<c:set var="nomeError">
		<form:errors path="nome" />
	</c:set>
	<div
		class="form-group col-md-6 ${not empty nomeError ? 'has-error' : ''}">
		<label class="control-label" for="nome">Nome</label>
		<form:input type="text" path="nome" id="nome" class="form-control"
			placeholder="Nome da marmita" maxlength="60" />
		<div class="help-block with-errors">${nomeError}</div>
	</div>


	<c:set var="custoError">
		<form:errors path="custo" />
	</c:set>
	<div
		class="form-group col-md-2 ${not empty custoError ? 'has-error' : ''}">
		<label class="control-label" for="custo">Custo</label>
		<form:input type="text" path="custo" id="custo" class="form-control"
			placeholder="Custo da marmita" />
		<div class="help-block with-errors">${custoError}</div>
	</div>

	<c:set var="tamanhoError">
		<form:errors path="tamanho" />
	</c:set>
	<div
		class="form-group col-md-4 ${not empty tamanhoError?'has-error':''}">
		<label class="control-label" for="tamanho">Tamanho</label>

		<form:select path="tamanho" id="tamanho" items="${produtoTamanhos}"
			multiple="true" itemValue="tamanho" itemLabel="descricao"
			class="form-control" />
		<div class="help-block with-errors">${tamanhoError}</div>
	</div>
</div>

<div class="row">
	<c:set var="ingredientesError">
		<form:errors path="ingredientes" />
	</c:set>
	<div
		class="form-group col-md-8 ${not empty ingredientesError ? 'has-error' : ''}">
		<label class="control-label" for="ingredientes">Ingredientes</label>

		<form:textarea path="ingredientes" id="ingredientes"
			class="form-control" placeholder="Ingredientes da marmita" />
		<div class="help-block with-errors">${ingredientesError}</div>
	</div>

	<c:set var="codigoNcmError">
		<form:errors path="codigoNcm" />
	</c:set>
	<div
		class="form-group col-md-2 ${not empty codigoNcmError ? 'has-error' : ''}">
		<label class="control-label" for="codigoNcm">NCM</label>
		<form:input type="text" path="codigoNcm" id="codigoNcm"
			class="form-control" />
		<div class="help-block with-errors">${codigoNcmError}</div>
	</div>

	<c:set var="codigoCestError">
		<form:errors path="codigoCest" />
	</c:set>
	<div
		class="form-group col-md-2 ${not empty codigoCestError ? 'has-error' : ''}">
		<label class="control-label" for="codigoCest">CEST</label>
		<form:input type="text" path="codigoCest" id="codigoCest"
			class="form-control" />
		<div class="help-block with-errors">${codigoCestError}</div>
	</div>
</div>

<input type="hidden" id="lastLoadedPage" name="lastLoadedPage"
	value="${lastLoadedPage}">

<script src="<c:url value='/static/js/casa_das_quentinhas-form.js' />"></script>
<script
	src="<c:url value='/static/js/casa_das_quentinhas-produto-form.js' />"></script>