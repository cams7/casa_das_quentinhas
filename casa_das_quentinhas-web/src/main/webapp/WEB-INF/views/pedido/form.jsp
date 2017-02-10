<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="row">
	<c:set var="clienteNomeError">
		<form:errors path="cliente.id" />
	</c:set>
	<div
		class="form-group col-md-6 ${not empty clienteNomeError ? 'has-error' : ''}">
		<label class="control-label" for="cliente.nome">${pedido.tipoCliente eq 'PESSOA_JURIDICA' ? 'Empresa' : 'Cliente' }</label>
		<form:input type="text" path="cliente.nome" id="cliente"
			class="form-control" maxlength="80"
			placeholder="${pedido.tipoCliente eq 'PESSOA_JURIDICA' ? 'Razão social / CNPJ' : 'Nome / CPF / Telefone' }" />
		<form:input type="hidden" path="cliente.id" id="cliente_id" />
		<div class="help-block with-errors">${clienteNomeError}</div>
	</div>

	<c:set var="tipoClienteError">
		<form:errors path="tipoCliente" />
	</c:set>
	<div
		class="form-group col-md-3 ${not empty tipoClienteError?'has-error':''}">
		<label class="control-label" for="tipoCliente">Tipo de cliente</label>

		<form:select path="tipoCliente" id="tipoCliente"
			items="${pedidoTiposCliente}" multiple="true" itemValue="tipoCliente"
			itemLabel="descricao" class="form-control" />
		<div class="help-block with-errors">${tipoClienteError}</div>
	</div>

	<c:set var="formaPagamentoError">
		<form:errors path="formaPagamento" />
	</c:set>
	<div
		class="form-group col-md-3 ${not empty formaPagamentoError?'has-error':''}">
		<label class="control-label" for="formaPagamento">Formas de
			pagamento</label>

		<form:select path="formaPagamento" id="formaPagamento"
			items="${pedidoFormasPagamento}" multiple="true"
			itemValue="formaPagamento" itemLabel="descricao" class="form-control" />
		<div class="help-block with-errors">${formaPagamentoError}</div>
	</div>
</div>
<div class="row">
	<c:set var="entregadorNomeError">
		<form:errors path="entregador.id" />
	</c:set>
	<div
		class="form-group col-md-6 ${not empty entregadorNomeError ? 'has-error' : ''}">
		<label class="control-label" for="entregador.nome">Entregador(a)</label>
		<form:input type="text" path="entregador.nome" id="entregador"
			class="form-control" maxlength="80"
			placeholder="Nome / CPF / Celular" />
		<form:input type="hidden" path="entregador.id" id="entregador_id" />
		<div class="help-block with-errors">${entregadorNomeError}</div>
	</div>

	<c:set var="tipoAtendimentoError">
		<form:errors path="tipoAtendimento" />
	</c:set>
	<div
		class="form-group col-md-3 ${not empty tipoAtendimentoError?'has-error':''}">
		<label class="control-label" for="tipoAtendimento">Tipo de
			atendimento</label>

		<form:select path="tipoAtendimento" id="tipoAtendimento"
			items="${pedidoTiposAtendimento}" multiple="true"
			itemValue="tipoAtendimento" itemLabel="descricao"
			class="form-control" />
		<div class="help-block with-errors">${tipoAtendimentoError}</div>
	</div>

	<c:set var="situacaoError">
		<form:errors path="situacao" />
	</c:set>
	<div
		class="form-group col-md-3 ${not empty situacaoError?'has-error':''}">
		<label class="control-label" for="situacao">Situação</label>
		<c:choose>
			<c:when test="${edit}">
				<form:select path="situacao" id="situacao"
					items="${pedidoSituacoes}" multiple="true" itemValue="situacao"
					itemLabel="descricao" class="form-control" />
				<div class="help-block with-errors">${situacaoError}</div>
			</c:when>
			<c:otherwise>
				<input type="text" value="${pedidoSituacao}" class="form-control"
					readonly="readonly" id="situacao" />
				<form:input type="hidden" path="situacao" />
			</c:otherwise>
		</c:choose>
	</div>
</div>
<div class="row">
	<c:set var="quantidadeError">
		<form:errors path="quantidade" />
	</c:set>
	<div
		class="form-group col-md-3 ${not empty quantidadeError ? 'has-error' : ''}">
		<label class="control-label" for="quantidade">Quantidade total</label>
		<form:input type="text" path="quantidade" id="quantidade"
			class="form-control" readonly="true" />
		<div class="help-block with-errors">${quantidadeError}</div>
	</div>

	<c:set var="custoError">
		<form:errors path="custo" />
	</c:set>
	<div
		class="form-group col-md-3 ${not empty custoError ? 'has-error' : ''}">
		<label class="control-label" for="custo">Total</label>
		<form:input type="text" path="custo" id="custo" class="form-control"
			readonly="true" />
		<div class="help-block with-errors">${custoError}</div>
	</div>

	<c:set var="custoIcmsError">
		<form:errors path="custoIcms" />
	</c:set>
	<div
		class="form-group col-md-3 ${not empty custoIcmsError ? 'has-error' : ''}">
		<label class="control-label" for="custoIcms">ICMS</label>
		<form:input type="text" path="custoIcms" id="custoIcms"
			class="form-control" readonly="true" />
		<div class="help-block with-errors">${custoIcmsError}</div>
	</div>

	<c:set var="custoStError">
		<form:errors path="custoSt" />
	</c:set>
	<div
		class="form-group col-md-3 ${not empty custoStError ? 'has-error' : ''}">
		<label class="control-label" for="custoSt">Subs. tributária</label>
		<form:input type="text" path="custoSt" id="custoSt"
			class="form-control" readonly="true" />
		<div class="help-block with-errors">${custoStError}</div>
	</div>
</div>

<input type="hidden" id="previousPage" name="previousPage"
	value="${previousPage}">

<script src="<c:url value='/static/js/casa_das_quentinhas-form.js' />"></script>
<script type="text/javascript">
	var MAIN_PAGE = '<c:url value='/${mainPage}' />';
</script>
<script
	src="<c:url value='/static/js/casa_das_quentinhas-pedido-form.js' />"></script>