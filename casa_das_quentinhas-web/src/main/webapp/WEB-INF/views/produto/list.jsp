<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="p" uri="/WEB-INF/taglibs/pagination.tld"%>

<fmt:setLocale value="pt-BR" />

<c:if test="${count > 0}">
	<div id="list" class="row">
		<div class="table-responsive col-md-12">
			<table class="table table-striped table-bordered dataTable">
				<thead>
					<tr>
						<th class="${sortField eq 'id' ? sortOrder : 'sorting' }" id="id">#</th>
						<th class="${sortField eq 'nome' ? sortOrder : 'sorting' }"
							id="nome">Nome</th>
						<th class="${sortField eq 'tamanho' ? sortOrder : 'sorting' }"
							id="tamanho">Tamanho</th>
						<th class="${sortField eq 'custo' ? sortOrder : 'sorting' }"
							id="custo">Custo</th>

						<sec:authorize access="hasRole('GERENTE') or hasRole('ATENDENTE')">
							<th class="actions">Ações</th>
						</sec:authorize>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${produtos}" var="produto">
						<tr>
							<th scope="row">${produto.id}</th>
							<td>${produto.nome}</td>
							<td>${produto.tamanho.descricao}</td>
							<td><fmt:formatNumber value="${produto.custo}" type="currency"/></td>

							<td class="actions"><a class="btn btn-success btn-xs"
								href="<c:url value='/produto/${produto.id}' />">Visualizar</a> <sec:authorize
									access="hasRole('GERENTE') or hasRole('ATENDENTE')">
									<a class="btn btn-warning btn-xs"
										href="<c:url value='/produto/${produto.id}/edit' />">Alterar</a>
								</sec:authorize> <sec:authorize access="hasRole('GERENTE')">
									<button class="btn btn-danger btn-xs delete"
										value="${produto.id}"
										title="Deseja realmente excluir o produto ( ${produto.nomeWithTamanho} )">Excluir</button>
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
	<input type="hidden" id="dataTable_globalFilters" value="${globalFilters}">
	<input type="hidden" id="dataTable_query" value="${query}">

	<input type="hidden" id="dataTable_maxResults" value="${maxResults}">
	<input type="hidden" id="dataTable_count" value="${count}">

	<c:set var="paginateUri">
		<c:url value='/produto/list' />
	</c:set>

	<div class="row">
		<div class="col-sm-5">
			<div class="dataTables_info" role="status">
				No total, <span class="badge">${count}</span> produtos foram
				retornadas
			</div>
		</div>
		<div class="col-sm-7">
			<div class="dataTables_paginate paging_simple_numbers">
				<p:paginate max="15" steps="${maxResults}" offset="${offset}"
					count="${count}" uri="${paginateUri}" />
			</div>
		</div>
	</div>
</c:if>