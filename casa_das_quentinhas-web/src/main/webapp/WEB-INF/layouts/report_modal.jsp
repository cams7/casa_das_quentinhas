<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="modal fade" id="report_modal" tabindex="-1" role="dialog"
	aria-labelledby="modalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Fechar">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="modalLabel">Intervalo entre as
					páginas</h4>
			</div>
			<form method="POST">
				<div class="modal-body">
					<div class="row">
						<div class="radio col-md-12">
							<label><input type="radio" name="pagina" value="todas">Todas as
								páginas</label>
						</div>
					</div>
					<div class="row">
						<div class="radio col-md-6">
							<label for="amount"><input type="radio" name="pagina" value="selecionada">Páginas:</label>								
							<input type="text" id="intervalo_informado" readonly style="border:0; color:#f6931f; font-weight:bold;">
						</div>
						<div class="col-md-6">
							<div id="intervalo"></div>
						</div>
					</div>
					<div class="row">
						<div class="radio col-md-12">
							<label><input type="radio" name="pagina" value="atual">Página
								atual</label>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<input type="submit" class="btn btn-primary" value="Gerar">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
				</div>
			</form>
		</div>
	</div>
</div>