$(document).ready(function($) {	
    $(document).on('click', 'button#delete', event => {
        event.preventDefault();
                
        $('div#delete_modal #delete_form').attr('action', MAIN_PAGE + '/' + event.target.value + '/delete');
        $('div#delete_modal div.modal-body').html(event.target.title);
        $('div#delete_modal').removeClass('delete_from_list_page').addClass('delete_from_show_page').modal('show');
    });
    
    $(document).on('submit', 'div.delete_from_show_page #delete_form', event => {
        event.preventDefault();
                               
        var form = event.target;

        $.ajax({
            url: form.action,
            type: form.method,
            success: data => {
            	window.history.go(-1);
            },
            error: data => {
            	createErrorMessage(data.status, data.responseJSON);
            },
            complete: data => {
            	$('div#delete_modal').modal('hide');
            }
        });
    }); 
});