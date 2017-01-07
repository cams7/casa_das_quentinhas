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
						<th class="${sortField eq 'id' ? sortOrder : 'sorting' }" id="id">#</th>
						<th class="${sortField eq 'nome' ? sortOrder : 'sorting' }"
							id="nome">Nome</th>
						<th class="${sortField eq 'cpf' ? sortOrder : 'sorting' }"
							id="cpf">CPF</th>
						<th
							class="${sortField eq 'usuario.email' ? sortOrder : 'sorting' }"
							id="usuario.email">E-mail</th>
						<th class="${sortField eq 'celular' ? sortOrder : 'sorting' }"
							id="celular">Celular</th>
						<th class="${sortField eq 'funcao' ? sortOrder : 'sorting' }"
							id="funcao">Função</th>

						<sec:authorize access="hasRole('GERENTE') or hasRole('ATENDENTE')">
							<th class="actions">Ações</th>
						</sec:authorize>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${funcionarios}" var="funcionario">
						<tr>
							<td>${funcionario.id}</td>
							<td>${funcionario.nome}</td>
							<td>${funcionario.formattedCpf}</td>
							<td>${funcionario.usuario.email}</td>
							<td>${funcionario.formattedCelular}</td>
							<td>${funcionario.funcao.descricao}</td>

							<td class="actions"><a class="btn btn-success btn-xs"
								href="<c:url value='/funcionario/${funcionario.id}' />">Visualizar</a>
								<sec:authorize
									access="hasRole('GERENTE') or hasRole('ATENDENTE')">
									<a class="btn btn-warning btn-xs"
										href="<c:url value='/funcionario/${funcionario.id}/edit' />">Alterar</a>
								</sec:authorize> <sec:authorize access="hasRole('GERENTE')">
									<button class="btn btn-danger btn-xs delete"
										value="${funcionario.id}"
										title="Deseja realmente excluir o(a) funcionário(a) ( ${funcionario.nomeWithCpf} )">Excluir</button>
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
		<c:url value='/funcionario/list' />
	</c:set>

	<div class="row">
		<div class="col-sm-5">
			<div class="dataTables_info" role="status">
				No total, <span class="badge">${count}</span> funcionários foram
				retornados
			</div>
		</div>
		<div class="col-sm-7">
			<div class="dataTables_paginate paging_simple_numbers">
				<p:paginate max="15" steps="${maxResults}" offset="${offset}"
					count="${count}" uri="${paginateUri}" next="&raquo;"
					previous="&laquo;" />
			</div>
		</div>
	</div>
</c:if>