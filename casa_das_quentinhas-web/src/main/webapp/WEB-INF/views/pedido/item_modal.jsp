<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="modal fade" id="item_modal" tabindex="-1" role="dialog"
	aria-labelledby="modalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Fechar">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="modalLabel">Adicionar Item</h4>
			</div>
			<form action="<c:url value='/${mainPage}/${pedido.id != null ? pedido.id : 0}/item' />" method="POST"
				id="item_form" data-toggle="validator" role="form">
				<div class="modal-body">
					<div class="row">
						<div class="form-group col-md-8">
							<label class="control-label" for="produto">Produto</label> <input
								type="text" name="produto" id="produto" class="form-control"
								maxlength="60" placeholder="Nome da marmita" required>
							<div class="help-block with-errors"></div>
							<input type="hidden" id="produto_id" name="produto_id" value="">
						</div>
						<div class="form-group col-md-4">
							<label class="control-label" for="quantidade">Quantidade</label>
							<input type="text" name="quantidade" id="quantidade"
								class="form-control" placeholder="Qtd de marmitas"
								pattern="^\d+$" required>
							<div class="help-block with-errors"></div>
							<input type="hidden" id="item_id" name="item_id" value="">
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<input type="submit" class="btn btn-primary" value="Salvar">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
				</div>
			</form>
		</div>
	</div>
</div>
<input type="hidden" id="modal_state" value="add">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />