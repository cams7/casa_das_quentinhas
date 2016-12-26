<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="authbar">
	<span>Dear <strong>${loggedinuser}</strong>, You are not
		authorized to access this page.
	</span> <span class="floatRight"><a href="<c:url value="/logout" />">Logout</a></span>
</div>