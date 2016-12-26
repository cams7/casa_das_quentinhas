$(document).ready(function() {
	$('#cancelar').click(event => {
		event.preventDefault();
		window.history.go(-$("#lastLoadedPage").val());
	});
});