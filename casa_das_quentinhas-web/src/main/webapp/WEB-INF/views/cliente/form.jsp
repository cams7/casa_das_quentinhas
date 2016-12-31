<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="row">
	<c:set var="nomeError">
		<form:errors path="nome" />
	</c:set>
	<div
		class="form-group col-md-8 ${not empty nomeError ? 'has-error' : ''}">
		<label class="control-label" for="nome">Nome</label>
		<form:input type="text" path="nome" id="nome" class="form-control"
			maxlength="60" />
		<div class="help-block with-errors">${nomeError}</div>
	</div>


	<c:set var="cpfError">
		<form:errors path="cpf" />
	</c:set>
	<div
		class="form-group col-md-2 ${not empty cpfError ? 'has-error' : ''}">
		<label class="control-label" for="cpf">CPF</label>
		<form:input type="text" path="cpf" id="cpf" class="form-control"
			placeholder="999.999.999-99" maxlength="14" />
		<div class="help-block with-errors">${cpfError}</div>
	</div>

	<c:set var="nascimentoError">
		<form:errors path="nascimento" />
	</c:set>
	<div
		class="form-group col-md-2 ${not empty nascimentoError ? 'has-error' : ''}">
		<label class="control-label" for="nascimento">Data de
			nascimento</label>
		<form:input type="text" path="nascimento" id="nascimento"
			class="form-control" placeholder="99/99/9999" maxlength="10" />
		<div class="help-block with-errors">${nascimentoError}</div>
	</div>
</div>

<div class="row">
	<c:set var="emailError">
		<form:errors path="contato.email" />
	</c:set>
	<div
		class="form-group col-md-6 ${not empty emailError ? 'has-error' : ''}">
		<label class="control-label" for="contato.email">E-mail</label>

		<form:input type="text" path="contato.email" id="email"
			class="form-control" maxlength="50" />
		<div class="help-block with-errors">${emailError}</div>
	</div>

	<c:set var="telefoneError">
		<form:errors path="contato.telefone" />
	</c:set>
	<div
		class="form-group col-md-2 ${not empty telefoneError ? 'has-error' : ''}">
		<label class="control-label" for="contato.telefone">Telefone</label>
		<form:input type="text" path="contato.telefone" id="telefone"
			class="form-control" placeholder="(99) 9999-9999" maxlength="14" />
		<div class="help-block with-errors">${telefoneError}</div>
	</div>

	<c:set var="tipoContribuinteError">
		<form:errors path="tipoContribuinte" />
	</c:set>
	<div
		class="form-group col-md-4 ${not empty tipoContribuinteError?'has-error':''}">
		<label class="control-label" for="tipoContribuinte">Tipo de
			contribuinte</label>

		<form:select path="tipoContribuinte" id="tipoContribuinte"
			items="${clienteTiposContribuintes}" multiple="true"
			itemValue="tipoContribuinte" itemLabel="descricao"
			class="form-control" />
		<div class="help-block with-errors">${tipoContribuinteError}</div>
	</div>
</div>

<div class="row">
	<c:set var="cidadeError">
		<form:errors path="cidade.id" />
	</c:set>
	<div
		class="form-group col-md-6 ${not empty cidadeError ? 'has-error' : ''}">
		<label class="control-label" for="cidade.nome">Cidade</label>
		<form:input type="text" path="cidade.nome" id="cidade"
			class="form-control" maxlength="80" placeholder="Nome / Código IBGE"
			value="${empresa.cidade.nomeWithEstadoSigla}" />
		<form:input type="hidden" path="cidade.id" id="cidade_id" />
		<div class="help-block with-errors">${cidadeError}</div>
	</div>

	<c:set var="cepError">
		<form:errors path="endereco.cep" />
	</c:set>
	<div
		class="form-group col-md-2 ${not empty cepError ? 'has-error' : ''}">
		<label class="control-label" for="endereco.cep">CEP</label>
		<form:input type="text" path="endereco.cep" id="cep"
			class="form-control" placeholder="99999-999" maxlength="10" />
		<div class="help-block with-errors">${cepError}</div>
	</div>

	<c:set var="bairroError">
		<form:errors path="endereco.bairro" />
	</c:set>
	<div
		class="form-group col-md-4 ${not empty bairroError ? 'has-error' : ''}">
		<label class="control-label" for="endereco.bairro">Bairro</label>
		<form:input type="text" path="endereco.bairro" id="bairro"
			class="form-control" maxlength="60" />
		<div class="help-block with-errors">${bairroError}</div>
	</div>
</div>

<div class="row">
	<c:set var="logradouroError">
		<form:errors path="endereco.logradouro" />
	</c:set>
	<div
		class="form-group col-md-8 ${not empty logradouroError ? 'has-error' : ''}">
		<label class="control-label" for="endereco.logradouro">Logradouro</label>
		<form:input type="text" path="endereco.logradouro" id="logradouro"
			class="form-control" maxlength="100" />
		<div class="help-block with-errors">${logradouroError}</div>
	</div>

	<c:set var="numeroImovelError">
		<form:errors path="endereco.numeroImovel" />
	</c:set>
	<div
		class="form-group col-md-4 ${not empty numeroImovelError ? 'has-error' : ''}">
		<label class="control-label" for="endereco.numeroImovel">Número
			do imóvel</label>
		<form:input type="text" path="endereco.numeroImovel" id="numeroImovel"
			class="form-control" maxlength="30" />
		<div class="help-block with-errors">${numeroImovelError}</div>
	</div>
</div>

<div class="row">
	<c:set var="complementoError">
		<form:errors path="endereco.complemento" />
	</c:set>
	<div
		class="form-group col-md-6 ${not empty complementoError ? 'has-error' : ''}">
		<label class="control-label" for="endereco.complemento">Complemento</label>
		<form:input type="text" path="endereco.complemento" id="complemento"
			class="form-control" maxlength="30" />
		<div class="help-block with-errors">${complementoError}</div>
	</div>

	<c:set var="pontoReferenciaError">
		<form:errors path="endereco.pontoReferencia" />
	</c:set>
	<div
		class="form-group col-md-6 ${not empty pontoReferenciaError ? 'has-error' : ''}">
		<label class="control-label" for="endereco.pontoReferencia">Ponto
			de referência</label>
		<form:input type="text" path="endereco.pontoReferencia"
			id="pontoReferencia" class="form-control" maxlength="100" />
		<div class="help-block with-errors">${pontoReferenciaError}</div>
	</div>
</div>

<div class="row">
	<c:set var="senhaError">
		<form:errors path="usuarioAcesso.senha" />
	</c:set>
	<div
		class="form-group col-md-6 ${not empty senhaError ? 'has-error' : ''}">
		<label class="control-label" for="usuarioAcesso.senha">Senha</label>

		<form:input type="password" path="usuarioAcesso.senha" id="senha"
			class="form-control" />
		<div class="help-block with-errors">${senhaError}</div>
	</div>

	<c:set var="confirmacaoError">
		<form:errors path="usuarioAcesso.confirmacaoSenha" />
	</c:set>
	<div
		class="form-group col-md-6 ${not empty confirmacaoError ? 'has-error' : ''}">
		<label class="control-label" for="usuarioAcesso.confirmacaoSenha">Confirmação</label>

		<form:input type="password" path="usuarioAcesso.confirmacaoSenha"
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
	src="<c:url value='/static/js/casa_das_quentinhas-cliente-form.js' />"></script>