<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="alert ${not empty param.sucessMessage?'alert-success':''}"
	style="display: ${not empty param.sucessMessage?'block':'none'};">
	<a href="#" class="close" onclick="$('div.alert').hide()">&times;</a> <strong>Sucesso!</strong>
	<span>${param.sucessMessage}</span>
</div>
