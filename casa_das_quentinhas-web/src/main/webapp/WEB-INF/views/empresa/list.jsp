<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="p" uri="/WEB-INF/taglibs/pagination.tld"%>

<c:if test="${count > 0}">
	<div id="list" class="row">
		<div class="table-responsive col-md-12">
			<table class="table table-striped table-bordered dataTable">
				<thead>
					<tr>
						<th class="${sortField eq 'id' ? sortOrder : 'sorting' }"
							id="id">#</th>
						<th class="${sortField eq 'razaoSocial' ? sortOrder : 'sorting' }"
							id="razaoSocial">Razão social</th>
						<th class="${sortField eq 'cnpj' ? sortOrder : 'sorting' }"
							id="cnpj">CNPJ</th>
						<th
							class="${sortField eq 'contato.email' ? sortOrder : 'sorting' }"
							id="contato.email">E-mail</th>
						<th
							class="${sortField eq 'contato.telefone' ? sortOrder : 'sorting' }"
							id="contato.telefone">Telefone</th>
						<th class="${sortField eq 'tipo' ? sortOrder : 'sorting' }"
							id="tipo">Tipo de empresa</th>
						<th
							class="${sortField eq 'cidade.nome' ? sortOrder : 'sorting' }"
							id="cidade.nome">Cidade</th>
						
						<sec:authorize access="hasRole('GERENTE') or hasRole('ATENDENTE')">
							<th class="actions">Ações</th>
						</sec:authorize>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${empresas}" var="empresa">
						<tr>
							<td>${empresa.id}</td>
							<td>${empresa.razaoSocial}</td>
							<td>${empresa.formattedCnpj}</td>
							<td>${empresa.contato.email}</td>
							<td>${empresa.contato.formattedTelefone}</td>
							<td>${empresa.tipo.descricao}</td>	
							<td>${empresa.cidade.nomeWithEstadoSigla}</td>						

							<td class="actions"><a class="btn btn-success btn-xs"
								href="<c:url value='/empresa/${empresa.id}' />">Visualizar</a>
								<sec:authorize
									access="hasRole('GERENTE') or hasRole('ATENDENTE')">
									<a class="btn btn-warning btn-xs"
										href="<c:url value='/empresa/${empresa.id}/edit' />">Alterar</a>
								</sec:authorize> <sec:authorize access="hasRole('GERENTE')">
									<button class="btn btn-danger btn-xs delete"
										value="${empresa.id}">Excluir</button>
								</sec:authorize></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<input type="hidden" id="dataTable_offset" value="${offset}">
	<input type="hidden" id="dataTable_sortField" value="${sortField}">
	<input type="hidden" id="dataTable_sortOrder" value="${sortOrder}">
	<input type="hidden" id="dataTable_query" value="${query}">

	<input type="hidden" id="dataTable_maxResults" value="${maxResults}">
	<input type="hidden" id="dataTable_count" value="${count}">

	<c:set var="paginateUri">
		<c:url value='/empresa/list' />
	</c:set>

	<div class="row">
		<div class="col-sm-5">
			<div class="dataTables_info" role="status">
				No total, <span class="badge">${count}</span> empresas foram
				retornadas
			</div>
		</div>
		<div class="col-sm-7">
			<div class="dataTables_paginate paging_simple_numbers">
				<p:paginate max="15" offset="${offset}" count="${count}"
					uri="${paginateUri}" next="&raquo;" previous="&laquo;" />
			</div>
		</div>
	</div>
</c:if>