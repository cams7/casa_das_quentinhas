$(document).on(
		'click',
		'button#delete',
		function(event) {
			event.preventDefault();

			$('div#delete_modal form').attr('action',
					MAIN_PAGE + '/' + event.target.value + '/delete');
			$('div#delete_modal div.modal-body').html(event.target.title);
			$('div#delete_modal').removeClass('delete_from_list_page')
					.addClass('delete_from_show_page').modal('show');
		});

$(document).on('submit', 'div.delete_from_show_page form', function(event) {
	event.preventDefault();

	var form = event.target;

	$.ajax({
		url : form.action + '?removePreviousPage=true',
		type : form.method,
		success : function(data) {
			var previousPage = data.previousPage;
			if (previousPage != undefined)
				window.location.replace(previousPage);
			else
				window.location.replace($('input#mainPage').val() + '?sucessMessage=' + data.message);

			// window.location.assign($('input#previousPage').val());
			// window.location.href = $('input#previousPage').val();
		},
		error : function(data) {
			createErrorMessage(data.status, data.responseJSON.message);
		},
		complete : function(data) {
			$('div#delete_modal').modal('hide');
		}
	});
});