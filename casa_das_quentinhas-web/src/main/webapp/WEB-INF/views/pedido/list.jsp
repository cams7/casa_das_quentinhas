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
						<c:if test="${not escondeCliente }">
							<th>Cliente</th>
						</c:if>
						<th class="${sortField eq 'tipoCliente' ? sortOrder : 'sorting' }"
							id="tipoCliente">Tipo de cliente</th>
						<th class="${sortField eq 'quantidade' ? sortOrder : 'sorting' }"
							id="quantidade">Quantidade</th>
						<th class="${sortField eq 'custo' ? sortOrder : 'sorting' }"
							id="custo">Custo</th>

						<sec:authorize access="hasRole('GERENTE') or hasRole('ATENDENTE')">
							<th class="actions">Ações</th>
						</sec:authorize>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${pedidos}" var="pedido">
						<tr>
							<td>${pedido.id}</td>
							<c:if test="${not escondeCliente }">
								<td>${pedido.empresa != null ? pedido.empresa.razaoSocialWithCnpj : pedido.cliente.nomeWithCpf}</td>
							</c:if>
							<td>${pedido.tipoCliente.descricao}</td>
							<td>${pedido.quantidade}</td>
							<td>${pedido.formattedCusto}</td>

							<td class="actions"><a class="btn btn-success btn-xs"
								href="<c:url value='/pedido/${pedido.id}' />">Visualizar</a> <sec:authorize
									access="hasRole('GERENTE') or hasRole('ATENDENTE')">
									<a class="btn btn-warning btn-xs"
										href="<c:url value='/pedido/${pedido.id}/edit' />">Alterar</a>
								</sec:authorize> <sec:authorize access="hasRole('GERENTE')">
									<button class="btn btn-danger btn-xs delete"
										value="${pedido.id}"
										title="Deseja realmente excluir o pedido ( ${pedido.id} )">Excluir</button>
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
				<p:paginate max="15" offset="${offset}" count="${count}"
					uri="${paginateUri}" next="&raquo;" previous="&laquo;" />
			</div>
		</div>
	</div>
</c:if>