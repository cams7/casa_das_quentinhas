$(document).ready(function() {	
    $(document).on('click', 'button#delete', event => {
        event.preventDefault();
        
        var id = event.target.value;
        
        $('div#delete_modal #delete_form').attr('action', MAIN_PAGE + '/' + id + '/delete');
        $('div#delete_modal h4#modalLabel').html(MODAL_LABEL);
        $('div#delete_modal div.modal-body').html(MODAL_BODY);
        $('div#delete_modal').modal('show');
    });
    
    $('div#delete_modal #delete_form').on('submit', event => {
        event.preventDefault();

        var form = event.target;
        
        var url = form.action;
        // console.log('DELETE ' + url);

        $.ajax({
            url: url,
            type: 'GET',
            // contentType: "application/json",
            // dataType: 'JSON',
            /*
			 * data: { 'id': 1, '_method': 'DELETE' },
			 */
            // timeout: 600000,
            success: data => {
                // console.log('Sucess:');
                // console.log(data);
            	
            	window.history.go(-1);
            },
            error: data => {
                console.log('Error:');
                console.log(data);
            }
        });
    }); 
});