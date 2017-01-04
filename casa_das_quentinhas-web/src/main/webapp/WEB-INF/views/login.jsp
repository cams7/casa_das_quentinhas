<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row">
	<div class="col-xs-6">
		<div class="well">
			<c:url var="loginUrl" value="/login" />
			<form action="${loginUrl}" method="POST">
				<div class="form-group">
					<label class="control-label" for="email"></label> <input
						type="text" class="form-control" id="email" name="email"
						title="Por favor entre com o e-mail do usuário"
						placeholder="E-mail" required>
				</div>
				<div class="form-group">
					<label class="control-label" for="senha"></label> <input
						type="password" class="form-control" id="senha" name="senha"
						title="Por favor entre com a senha" placeholder="Senha" required>
				</div>
				<c:if test="${param.error != null}">
					<div class="alert alert-danger">E-mail ou senha inválida.</div>
				</c:if>
				<c:if test="${param.logout != null}">
					<div class="alert alert-success">Logout realizado com
						sucesso.</div>
				</c:if>
				<div class="checkbox">
					<label> <input type="checkbox" id="lembre_me"
						name="lembre_me"> Lembre-me
					</label>
				</div>
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" /> <input type="submit"
					class="btn btn-success btn-block" value="Entrar">
			</form>
		</div>
	</div>
	<div class="col-xs-6">
		<p class="lead">
			E-mails de acesso para a senha <span class="text-success">12345</span>
		</p>
		<ul class="list-unstyled" style="line-height: 2">
			<li>gerente@casa-das-quentinhas.com</li>
			<li>atendente@casa-das-quentinhas.com</li>
			<li>entregador@casa-das-quentinhas.com</li>
			<li>empresa@casa-das-quentinhas.com</li>
			<li>cliente@casa-das-quentinhas.com</li>
		</ul>
	</div>
</div>