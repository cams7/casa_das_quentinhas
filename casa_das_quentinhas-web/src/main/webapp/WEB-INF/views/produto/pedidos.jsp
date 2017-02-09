<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="p" uri="/WEB-INF/taglibs/pagination.tld"%>

<fmt:setLocale value="pt-BR" />

<c:if test="${count > 0}">
	<h3 class="page-header">Pedidos</h3>

	<div id="list" class="row">
		<div class="table-responsive col-md-12">
			<table class="table table-bordered dataTable">
				<thead>
					<tr>
						<th class="${sortField eq 'id.pedidoId' ? sortOrder : 'sorting' }"
							id="id.pedidoId">Pedido</th>
						<th
							class="${sortField eq 'pedido.manutencao.cadastro' ? sortOrder : 'sorting' }"
							id="pedido.manutencao.cadastro">Data do pedido</th>
						<th>Cliente</th>
						<th class="${sortField eq 'quantidade' ? sortOrder : 'sorting' }"
							id="quantidade">Quantidade</th>

						<sec:authorize access="hasRole('GERENTE') or hasRole('ATENDENTE')">
							<th class="actions">Ações</th>
						</sec:authorize>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${itens}" var="item">
						<tr
							class="${item.pedido.situacao eq 'PENDENTE' ? 'bg-warning' : item.pedido.situacao eq 'EM_TRANSITO' ? 'bg-info' : item.pedido.situacao eq 'ENTREGUE' ? 'bg-success' : 'bg-danger'}">
							<th scope="row">${item.id.pedidoId}</th>
							<td><fmt:formatDate type="both" dateStyle="short"
									timeStyle="short" value="${item.pedido.manutencao.cadastro}" /></td>
							<td><c:choose>
									<c:when test="${item.pedido.empresa != null}">
										<a href="<c:url value='/empresa/${item.pedido.empresa.id}' />">${item.pedido.empresa.razaoSocialWithCnpj}</a>
									</c:when>
									<c:otherwise>
										<a href="<c:url value='/cliente/${item.pedido.cliente.id}' />">${item.pedido.cliente.nomeWithCpf}</a>
									</c:otherwise>
								</c:choose></td>
							<td>${item.quantidade}</td>

							<td class="actions"><a class="btn btn-success btn-xs"
								href="<c:url value='/pedido/${item.id.pedidoId}' />">Visualizar</a>
								<sec:authorize
									access="hasRole('GERENTE') or hasRole('ATENDENTE')">
									<a class="btn btn-warning btn-xs"
										href="<c:url value='/pedido/${item.id.pedidoId}/edit' />">Alterar</a>
								</sec:authorize> <sec:authorize access="hasRole('GERENTE')">
									<button class="btn btn-danger btn-xs delete"
										value="${item.id.pedidoId}"
										title="Deseja realmente excluir o pedido ( ${item.id.pedidoId} )">Excluir</button>
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
	<input type="hidden" id="dataTable_globalFilters"
		value="${globalFilters}">
	<input type="hidden" id="dataTable_query" value="${query}">

	<input type="hidden" id="dataTable_maxResults" value="${maxResults}">
	<input type="hidden" id="dataTable_count" value="${count}">

	<c:set var="paginateUri">
		<c:url value='/produto/${itens[0].id.produtoId}/pedidos' />
	</c:set>

	<div class="row">
		<div class="col-sm-5">
			<div class="dataTables_info" role="status">
				No total, <span class="badge">${count}</span> pedido foram
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