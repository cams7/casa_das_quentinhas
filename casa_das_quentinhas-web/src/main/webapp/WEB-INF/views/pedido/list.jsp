<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="p" uri="/WEB-INF/taglibs/pagination.tld"%>

<fmt:setLocale value="pt-BR" />

<c:if test="${count > 0}">
	<div id="list" class="row">
		<div class="table-responsive col-md-12">
			<table class="table table-bordered dataTable">
				<thead>
					<tr>
						<th class="${sortField eq 'id' ? sortOrder : 'sorting' }" id="id">#</th>
						<c:if test="${not escondeCliente}">
							<th>Cliente/Empresa</th>
						</c:if>
						<th class="${sortField eq 'tipoCliente' ? sortOrder : 'sorting' }"
							id="tipoCliente">Tipo de cliente</th>
						<th class="${sortField eq 'quantidade' ? sortOrder : 'sorting' }"
							id="quantidade">Quantidade</th>
						<th class="${sortField eq 'custo' ? sortOrder : 'sorting' }"
							id="custo">Custo</th>
						<th
							class="${sortField eq 'manutencao.cadastro' ? sortOrder : 'sorting' }"
							id="manutencao.cadastro">Data do pedido</th>

						<sec:authorize access="hasRole('GERENTE') or hasRole('ATENDENTE')">
							<th class="actions">Ações</th>
						</sec:authorize>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${pedidos}" var="pedido">
						<tr class="${pedido.situacao eq 'PENDENTE' ? 'bg-warning' : pedido.situacao eq 'EM_TRANSITO' ? 'bg-info' : pedido.situacao eq 'ENTREGUE' ? 'bg-success' : 'bg-danger'}">
							<th scope="row">${pedido.id}</th>
							<c:if test="${not escondeCliente}">
								<td><c:choose>
										<c:when test="${pedido.empresa != null}">
											<a href="<c:url value='/empresa/${pedido.empresa.id}' />">${pedido.empresa.razaoSocialWithCnpj}</a>
										</c:when>
										<c:otherwise>
											<a href="<c:url value='/cliente/${pedido.cliente.id}' />">${pedido.cliente.nomeWithCpf}</a>
										</c:otherwise>
									</c:choose></td>
							</c:if>
							<td>${pedido.tipoCliente.descricao}</td>
							<td>${pedido.quantidade}</td>
							<td><fmt:formatNumber value="${pedido.custo}" type="currency"/></td>
							<td><fmt:formatDate type="both" dateStyle="short"
									timeStyle="short" value="${pedido.manutencao.cadastro}" /></td>

							<td class="actions"><a class="btn btn-success btn-xs"
								href="<c:url value='/pedido/${pedido.id}' />">Visualizar</a> <sec:authorize
									access="hasRole('GERENTE') or hasRole('ATENDENTE')">
									<a class="btn btn-warning btn-xs"
										href="<c:url value='/pedido/${pedido.id}/edit' />">Alterar</a>
								</sec:authorize> <sec:authorize access="hasRole('GERENTE')">
									<button class="btn btn-danger btn-xs delete"
										value="${pedido.id}"
										title="Deseja realmente excluir o pedido ( ${pedido.idWithCadastro} )">Excluir</button>
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
		<c:url value='/pedido/list' />
	</c:set>

	<div class="row">
		<div class="col-sm-5">
			<div class="dataTables_info" role="status">
				No total, <span class="badge">${count}</span> pedidos foram
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