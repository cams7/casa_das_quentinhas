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
			<table id="usuarios"
				class="table table-striped table-bordered dataTable">
				<thead>
					<tr>
						<th class="${sortField eq 'nome' ? sortOrder : 'sorting' }"
							id="nome">Nome</th>
						<th class="${sortField eq 'sobrenome' ? sortOrder : 'sorting' }"
							id="sobrenome">Sobrenome</th>
						<th class="${sortField eq 'email' ? sortOrder : 'sorting' }"
							id="email">E-mail</th>
						<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
							<th class="actions">Ações</th>
						</sec:authorize>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${usuarios}" var="usuario">
						<tr>
							<td>${usuario.nome}</td>
							<td>${usuario.sobrenome}</td>
							<td>${usuario.email}</td>

							<td class="actions"><a class="btn btn-success btn-xs"
								href="<c:url value='/usuario/${usuario.id}' />">Visualizar</a>
								<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
									<a class="btn btn-warning btn-xs"
										href="<c:url value='/usuario/${usuario.id}/edit' />">Alterar</a>
								</sec:authorize> <sec:authorize access="hasRole('ADMIN')">
									<!--a class="btn btn-danger btn-xs" href="#" data-toggle="modal" data-target="#delete-modal">Excluir</a-->
									<a class="btn btn-danger btn-xs"
										href="<c:url value='/usuario/${usuario.id}/delete' />">Excluir</a>
								</sec:authorize></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<input type="hidden" id="offset" value="${offset}">
	<input type="hidden" id="sortField" value="${sortField}">
	<input type="hidden" id="sortOrder" value="${sortOrder}">
	<input type="hidden" id="query" value="${query}">

	<c:set var="paginateUri">
		<c:url value='/usuario/list' />
	</c:set>

	<div class="row">
		<div class="col-sm-5">
			<div class="dataTables_info" id="usuarios_info" role="status">
				No total, <span class="badge">${count}</span> usuários foram
				retornados
			</div>
		</div>
		<div class="col-sm-7">
			<div class="dataTables_paginate paging_simple_numbers"
				id="usuarios_paginate">
				<p:paginate max="15" offset="${offset}" count="${count}"
					uri="${paginateUri}" next="&raquo;" previous="&laquo;" />
			</div>
		</div>
	</div>
</c:if>