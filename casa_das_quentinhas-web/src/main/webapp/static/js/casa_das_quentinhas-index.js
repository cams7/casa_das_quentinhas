$(document).ready(function() {
	$(document).on('click', '.pagination a', event => {
	    event.preventDefault();
	    
	    offset = 0;
	    array = event.target.href.split('offset=');
		
		if(array.length > 1)
			offset = array[1];
		
		// console.log(offset);
		loadTable(offset, null, null, null);
	});
	
	$('#search_btn').click(event => {
		event.preventDefault();
		
		query = $('#search_query').val();
		loadTable(0, null, null, query);
	});
			
	$(document).on('click', 'table.dataTable tr:eq(0)', event => {
		event.preventDefault();
		
		column = $(event.target);
		var attr = column.attr('id');
		
		if(typeof attr !== typeof undefined && attr !== false) {			
			var sortField = column.attr('id');
			var sortOrder = column.hasClass('sorting') || column.hasClass('sorting_desc') ? 'sorting_asc' : 'sorting_desc';							
			
			// console.log('sortField: ' + sortField + ', sortOrder: ' +
			// sortOrder);
			loadTable(null, sortField, sortOrder, null);			
		}			
	});
			
	function loadTable(offset, sortField, sortOrder, query) {		
		if(offset == null){
	    	offset = $('#dataTable_offset').val();
	    	offset = offset != undefined ? offset : '0';
	    }
	    
	    if(sortField == null){
	    	sortField = $('#dataTable_sortField').val();
	    	sortField = sortField != undefined ? sortField : 'id';
	    }
	    
	    if(sortOrder == null){
	    	sortOrder = $('#dataTable_sortOrder').val();
	    	sortOrder = sortOrder != undefined ? sortOrder : 'sorting_desc';
	    }
	    
	    if(query == null){
			query = $('#dataTable_query').val();
		    query = query != undefined ? query : '';
		}
	    
	    // console.log('get: list?offset=' + offset + '&f=' + sortField +
		// '&s=' + sortOrder + '&q=' + query);
		
		$.get(MAIN_PAGE + '/list?offset=' + offset + '&f=' + sortField + '&s=' + sortOrder + '&q=' + query, data => {
	        // console.log(data);
			$('.content').html(data);
			location.hash = offset;
	   	});
	}
	
    $(document).on('click', 'table.dataTable button.delete', event => {
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
            	
            	offset = $('#dataTable_offset').val();
            	if(offset > 0) {
            		maxResults = $('#dataTable_maxResults').val();
            		count = $('#dataTable_count').val();
            		
            		if(count % maxResults == 1)
            			offset -= maxResults;
            	}
            	
            	loadTable(offset, null, null, null);
            	$('div#delete_modal').modal('toggle');
            },
            error: data => {
                console.log('Error:');
                console.log(data);
            }
        });
    }); 
});