$(document).ready(function($) {
	$(document).off('ajaxSend', loadingShow).off('ajaxComplete', loadingHide);
	
	$('button#cancelar').click(event => {
		event.preventDefault();
		window.history.go(-$('input#lastLoadedPage').val());
	});
});