<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="p" uri="/WEB-INF/taglibs/pagination.tld"%>

<c:if test="${count > 0}">
	<h3 class="page-header">Itens</h3>

	<div id="list" class="row">
		<div class="table-responsive col-md-12">
			<table class="table table-striped table-bordered dataTable">
				<thead>
					<tr>
						<th class="${sortField eq 'quantidade' ? sortOrder : 'sorting' }"
							id="quantidade">Quantidade</th>
						<th
							class="${sortField eq 'produto.custo' ? sortOrder : 'sorting' }"
							id="produto.custo">Custo</th>
						<th
							class="${sortField eq 'produto.nome' ? sortOrder : 'sorting' }"
							id="produto.nome">Produto</th>
						<th
							class="${sortField eq 'produto.tamanho' ? sortOrder : 'sorting' }"
							id="produto.tamanho">Tamanho</th>

						<sec:authorize access="hasRole('GERENTE') or hasRole('ATENDENTE')">
							<th class="actions">Ações</th>
						</sec:authorize>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${itens}" var="item">
						<tr>
							<td>${item.quantidade}</td>
							<td>${item.produto.formattedCusto}</td>
							<td><a
								href="<c:url value='/produto/${item.id.produtoId}' />">${item.produto.nome}</a></td>
							<td>${item.produto.tamanho.descricao}</td>

							<td class="actions"><sec:authorize
									access="hasRole('GERENTE') or hasRole('ATENDENTE')">
									<button class="btn btn-warning btn-xs item-updade"
										value="${item.id.produtoId}">Alterar</button>
								</sec:authorize> <sec:authorize access="hasRole('GERENTE')">
									<button class="btn btn-danger btn-xs item-delete"
										value="${item.id.produtoId}"
										title="Deseja realmente excluir o item que contém o produto ( ${item.id.produtoId} )">Excluir</button>
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
		<c:url value='/pedido/${itens[0].id.pedidoId}/itens' />
	</c:set>

	<div class="row">
		<div class="col-sm-5">
			<div class="dataTables_info" role="status">
				No total, <span class="badge">${count}</span> itens de pedido foram
				retornadas
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