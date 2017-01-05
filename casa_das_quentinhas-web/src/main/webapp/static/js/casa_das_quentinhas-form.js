$(document).ready(function($) {
	$('button#cancelar').click(event => {
		event.preventDefault();
		window.history.go(-$('input#lastLoadedPage').val());
	});
});