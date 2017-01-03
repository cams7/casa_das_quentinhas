<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="login-container">
	<div class="login-card">
		<div class="login-form">
			<c:url var="loginUrl" value="/login" />
			<form action="${loginUrl}" method="post" class="form-horizontal">
				<c:if test="${param.error != null}">
					<div class="alert alert-danger">
						<p>E-mail ou senha inválida.</p>
					</div>
				</c:if>
				<c:if test="${param.logout != null}">
					<div class="alert alert-success">
						<p>Logout realizado com sucesso.</p>
					</div>
				</c:if>

				<div class="row input-group">
					<label class="input-group-addon" for="email"><i
						class="glyphicon glyphicon-user"></i></label> <input type="text"
						class="form-control" id="email" name="email" placeholder="E-mail"
						required>
				</div>
				<div class="row">
					<div class="col-md-2">
						<label>E-mails de acesso: </label>
					</div>
					<div class="col-md-2">
						<label>gerente@casa-das-quentinhas.com</label>
					</div>
					<div class="col-md-2">
						<label>atendente@casa-das-quentinhas.com</label>
					</div>
					<div class="col-md-2">
						<label>entregador@casa-das-quentinhas.com</label>
					</div>
					<div class="col-md-2">
						<label>empresa@casa-das-quentinhas.com</label>
					</div>
					<div class="col-md-2">
						<label>cliente@casa-das-quentinhas.com</label>
					</div>
				</div>
				<div class="row input-group">
					<label class="input-group-addon" for="senha"><i
						class="glyphicon glyphicon-lock"></i></label> <input type="password"
						class="form-control" id="senha" name="senha" placeholder="Senha"
						required>
				</div>
				<div class="row">
					<div class="col-md-2">
						<label>Senha de acesso: </label>
					</div>
					<div class="col-md-10">
						<label>12345</label>
					</div>
				</div>
				<div class="row">
					<div class="input-group input-sm">
						<div class="checkbox">
							<label><input type="checkbox" id="lembre_me"
								name="lembre_me"> Lembre-me</label>
						</div>
					</div>
				</div>
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				<div class="row">
					<div class="form-actions">
						<input type="submit" class="btn btn-block btn-primary btn-default"
							value="Entrar">
					</div>
				</div>
			</form>
		</div>
	</div>
</div>