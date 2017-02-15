<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="row">
	<c:set var="razaoSocialError">
		<form:errors path="razaoSocial" />
	</c:set>
	<div
		class="form-group col-md-4 ${not empty razaoSocialError ? 'has-error' : ''}">
		<label class="control-label" for="razaoSocial">Razão social</label>
		<form:input type="text" path="razaoSocial" id="razaoSocial"
			class="form-control" maxlength="60" />
		<div class="help-block with-errors">${razaoSocialError}</div>
	</div>

	<c:set var="nomeFantasiaError">
		<form:errors path="nomeFantasia" />
	</c:set>
	<div
		class="form-group col-md-4 ${not empty nomeFantasiaError ? 'has-error' : ''}">
		<label class="control-label" for="nomeFantasia">Nome fantasia</label>
		<form:input type="text" path="nomeFantasia" id="nomeFantasia"
			class="form-control" maxlength="60" />
		<div class="help-block with-errors">${nomeFantasiaError}</div>
	</div>

	<c:set var="cnpjError">
		<form:errors path="cnpj" />
	</c:set>
	<div
		class="form-group col-md-2 ${not empty cnpjError ? 'has-error' : ''}">
		<label class="control-label" for="cnpj">CNPJ</label>
		<form:input type="text" path="cnpj" id="cnpj" class="form-control"
			placeholder="99.999.999/9999-99" maxlength="18" />
		<div class="help-block with-errors">${cnpjError}</div>
	</div>

	<c:set var="tipoError">
		<form:errors path="tipo" />
	</c:set>
	<div class="form-group col-md-2 ${not empty tipoError?'has-error':''}">

		<sec:authorize access="hasRole('GERENTE')" var="isGerente"></sec:authorize>

		<label class="control-label" for="tipo">Tipo de empresa</label>
		<c:choose>
			<c:when test="${edit and not isGerente}">
				<input type="text" value="${empresa.tipo.descricao}"
					class="form-control" readonly="readonly" id="tipo" />
				<form:input type="hidden" path="tipo" />
			</c:when>
			<c:otherwise>
				<form:select path="tipo" id="tipo" items="${empresaTipos}"
					multiple="true" itemValue="tipo" itemLabel="descricao"
					class="form-control" />
				<div class="help-block with-errors">${tipoError}</div>
			</c:otherwise>
		</c:choose>
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

	<c:set var="regimeTributarioError">
		<form:errors path="regimeTributario" />
	</c:set>
	<div
		class="form-group col-md-4 ${not empty regimeTributarioError?'has-error':''}">

		<label class="control-label" for="regimeTributario">Regime
			tributário</label>
		<form:select path="regimeTributario" id="regimeTributario"
			items="${empresaRegimesTributarios}" multiple="true"
			itemValue="regimeTributario" itemLabel="descricao"
			class="form-control" />
		<div class="help-block with-errors">${regimeTributarioError}</div>
	</div>
</div>

<div class="row">
	<c:set var="inscricaoEstadualError">
		<form:errors path="inscricaoEstadual" />
	</c:set>
	<div
		class="form-group col-md-3 ${not empty inscricaoEstadualError ? 'has-error' : ''}">
		<label class="control-label" for="inscricaoEstadual">Inscrição
			estadual</label>
		<form:input type="text" path="inscricaoEstadual"
			id="inscricaoEstadual" class="form-control" />
		<div class="help-block with-errors">${inscricaoEstadualError}</div>
	</div>

	<c:set var="inscricaoEstadualSTError">
		<form:errors path="inscricaoEstadualST" />
	</c:set>
	<div
		class="form-group col-md-3 ${not empty inscricaoEstadualSTError ? 'has-error' : ''}">
		<label class="control-label" for="inscricaoEstadualST">Inscrição
			estadual do subst. tributário</label>
		<form:input type="text" path="inscricaoEstadualST"
			id="inscricaoEstadualST" class="form-control" />
		<div class="help-block with-errors">${inscricaoEstadualSTError}</div>
	</div>

	<c:set var="inscricaoMuncipalError">
		<form:errors path="inscricaoMuncipal" />
	</c:set>
	<div
		class="form-group col-md-3 ${not empty inscricaoMuncipalError ? 'has-error' : ''}">
		<label class="control-label" for="inscricaoMuncipal">Inscrição
			municipal</label>
		<form:input type="text" path="inscricaoMuncipal"
			id="inscricaoMuncipal" class="form-control" />
		<div class="help-block with-errors">${inscricaoMuncipalError}</div>
	</div>

	<c:set var="codigoCnaeError">
		<form:errors path="codigoCnae" />
	</c:set>
	<div
		class="form-group col-md-3 ${not empty codigoCnaeError ? 'has-error' : ''}">
		<label class="control-label" for="codigoCnae">Códido CNAE</label>
		<form:input type="text" path="codigoCnae" id="codigoCnae"
			class="form-control" />
		<div class="help-block with-errors">${codigoCnaeError}</div>
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

<script src="<c:url value='/static/js/casa_das_quentinhas-form.js' />"></script>
<script type="text/javascript">
	var MAIN_PAGE = '<c:url value='/${mainPage}' />';
</script>
<script
	src="<c:url value='/static/js/casa_das_quentinhas-empresa-form.js' />"></script>