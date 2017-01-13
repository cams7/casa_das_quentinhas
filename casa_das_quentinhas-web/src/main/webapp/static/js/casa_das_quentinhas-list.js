function loadTable(removedRow = false, offset = null, sortField = null, sortOrder = null, query = null) {		
	if(offset == null){
    	offset = $('input#dataTable_offset').val();
    	offset = offset != undefined ? offset : 0;
    	
    	if(removedRow && offset > 0) {
    		var maxResults = $('input#dataTable_maxResults').val();
    		var count = $('input#dataTable_count').val();
    		
    		if(count % maxResults == 1)
    			offset -= maxResults;
    	}
    }
    
    if(sortField == null){
    	sortField = $('input#dataTable_sortField').val();
    	sortField = sortField != undefined ? sortField : 'id';
    }
    
    if(sortOrder == null){
    	sortOrder = $('input#dataTable_sortOrder').val();
    	sortOrder = sortOrder != undefined ? sortOrder : 'sorting_desc';
    }
    
    if(query == null){
		query = $('input#dataTable_query').val();
	    query = query != undefined ? query : '';
	}
    
    // console.log('get: list?offset=' + offset + '&f=' + sortField + '&s=' +
	// sortOrder + '&q=' + query);
	
	$.get(LIST_PAGE + '?offset=' + offset + '&f=' + sortField + '&s=' + sortOrder + '&q=' + query, data => {
        // console.log(data);
		$('.content').html(data);
		// location.hash = offset;
   	});
}

$('button#search_btn').click(event => {
	event.preventDefault();
	
	query = $('#search_query').val();
	loadTable(false, 0, null, null, query);
});

$('button#report').click(event => {
	event.preventDefault();
	
	console.log("Gerar relatório");
});

$(document).ready(function($) {
	$(document).on('click', '.pagination a', event => {
	    event.preventDefault();
	    
	    offset = 0;
	    array = event.target.href.split('offset=');
		
		if(array.length > 1)
			offset = array[1];
		
		loadTable(false, offset);
	});	
			
	$(document).on('click', 'table.dataTable tr:eq(0)', event => {
		event.preventDefault();
		
		column = $(event.target);
		var attr = column.attr('id');
		
		if(typeof attr !== typeof undefined && attr !== false) {			
			var sortField = column.attr('id');
			var sortOrder = column.hasClass('sorting') || column.hasClass('sorting_desc') ? 'sorting_asc' : 'sorting_desc';							
			
			loadTable(false, null, sortField, sortOrder);			
		}			
	});	
	
    $(document).on('click', 'table.dataTable button.delete', event => {
        event.preventDefault();
                
        $('div#delete_modal #delete_form').attr('action', DELETE_PAGE + '/' + event.target.value + '/delete');
        $('div#delete_modal div.modal-body').html(event.target.title);
        $('div#delete_modal').removeClass('delete_from_show_page').addClass('delete_from_list_page').modal('show');
    });
    
    $(document).on('submit', 'div.delete_from_list_page #delete_form', event => {
        event.preventDefault();
                
        var form = event.target;        
                
        $.ajax({
            url: form.action,
            type: form.method,
            success: data => {
            	createSuccessMessage(data.message);            	
            	loadTable(true);            	             	           	
            },
            error: data => { 
            	createErrorMessage(data.status, data.responseJSON.message);  
            },
            complete: data => {
            	$('div#delete_modal').modal('hide');
            }
        });
    });  
});