<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
	<c:when test="${messages != null}">
		<div class="alert alert-warning text-center">
			<h4 class="alert-heading">Requisi��o inv�lida!</h4>
			<c:forEach items="${messages}" var="message">
				<p>
					<strong>${message.key}</strong> - ${message.value}.
				</p>
			</c:forEach>
			<p>
				<a class="alert-link" href="javascript:history.back()">Voltar</a>
			</p>
		</div>
	</c:when>
	<c:otherwise>
		<div class="alert alert-warning text-center">
			<p>
				<strong>Requisi��o inv�lida!</strong> ${message}.
			</p>
			<p>
				<a class="alert-link" href="javascript:history.back()">Voltar</a>
			</p>
		</div>
	</c:otherwise>
</c:choose>