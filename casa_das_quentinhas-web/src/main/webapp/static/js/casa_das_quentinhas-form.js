$(document).ready(function($) {
//$(function() {
	$(document).off('ajaxSend', loadingShow).off('ajaxComplete', loadingHide);
	
	$('button#cancelar').click(function( event ) {
		event.preventDefault();
		window.history.go(-$('input#lastLoadedPage').val());
	});
});

