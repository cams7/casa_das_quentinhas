<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<h3 class="page-header">Casa da quentinhas</h3>

<div class="row">
	<div class="col-md-6">
		<p>
			<strong>Razão social</strong>
		</p>
		<p>${empresa.razaoSocial}</p>
	</div>
	<div class="col-md-4">
		<p>
			<strong>Nome fantasia</strong>
		</p>
		<p>${empresa.nomeFantasia}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>CNPJ</strong>
		</p>
		<p>${empresa.formattedCnpj}</p>
	</div>	
</div>

<div class="row">
	<div class="col-md-6">
		<p>
			<strong>E-mail</strong>
		</p>
		<p>${empresa.contato.email}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>Telefone</strong>
		</p>
		<p>${empresa.contato.formattedTelefone}</p>
	</div>
	<div class="col-md-4">
		<p>
			<strong>Regime tributário</strong>
		</p>
		<p>${empresa.regimeTributario.descricao}</p>
	</div>
</div>

<div class="row">
	<div class="col-md-3">
		<p>
			<strong>Inscrição estadual</strong>
		</p>
		<p>${empresa.inscricaoEstadual}</p>
	</div>
	<div class="col-md-3">
		<p>
			<strong>Inscrição estadual do subst. tributário</strong>
		</p>
		<p>${empresa.inscricaoEstadualST}</p>
	</div>
	<div class="col-md-3">
		<p>
			<strong>Inscrição municipal</strong>
		</p>
		<p>${empresa.inscricaoMuncipal}</p>
	</div>
	<div class="col-md-3">
		<p>
			<strong>Códido CNAE</strong>
		</p>
		<p>${empresa.codigoCnae}</p>
	</div>
</div>

<div class="row">
	<div class="col-md-6">
		<p>
			<strong>Cidade</strong>
		</p>
		<p>${empresa.cidade.nomeWithEstadoSigla}</p>
	</div>
	<div class="col-md-2">
		<p>
			<strong>CEP</strong>
		</p>
		<p>${empresa.endereco.formattedCep}</p>
	</div>
	<div class="col-md-4">
		<p>
			<strong>Bairro</strong>
		</p>
		<p>${empresa.endereco.bairro}</p>
	</div>
</div>

<div class="row">
	<div class="col-md-8">
		<p>
			<strong>Logradouro</strong>
		</p>
		<p>${empresa.endereco.logradouro}</p>
	</div>
	<div class="col-md-4">
		<p>
			<strong>Número do imóvel</strong>
		</p>
		<p>${empresa.endereco.numeroImovel}</p>
	</div>
</div>

<div class="row">
	<div class="col-md-6">
		<p>
			<strong>Complemento</strong>
		</p>
		<p>${empresa.endereco.complemento}</p>
	</div>
	<div class="col-md-6">
		<p>
			<strong>Ponto de referência</strong>
		</p>
		<p>${empresa.endereco.pontoReferencia}</p>
	</div>
</div>