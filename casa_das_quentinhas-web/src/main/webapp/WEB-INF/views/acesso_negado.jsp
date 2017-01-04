<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="authbar">
	<span><strong>${loggedinuser}</strong> não tem acesso a essa
		página. </span> <span class="floatRight"><a
		href="javascript:history.back()">Voltar</a></span>
</div>