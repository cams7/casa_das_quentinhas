<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Modal -->
<div class="modal fade" id="delete_modal" tabindex="-1" role="dialog"
	aria-labelledby="modalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Fechar">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="modalLabel">Title</h4>
			</div>
			<div class="modal-body">Message</div>
			<form:form id="delete_form" method="POST">
				<div class="modal-footer">
					<input type="submit" value="Sim" class="btn btn-primary" />
					<button type="button" class="btn btn-default" data-dismiss="modal">N&atilde;o</button>
				</div>
			</form:form>
		</div>
	</div>
</div>