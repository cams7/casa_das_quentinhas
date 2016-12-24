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
	    	offset = $('#offset').val();
	    	offset = offset != undefined ? offset : '0';
	    }
	    
	    if(sortField == null){
	    	sortField = $('#sortField').val();
	    	sortField = sortField != undefined ? sortField : 'id';
	    }
	    
	    if(sortOrder == null){
	    	sortOrder = $('#sortOrder').val();
	    	sortOrder = sortOrder != undefined ? sortOrder : 'sorting_desc';
	    }
	    
	    if(query == null){
			query = $('#query').val();
		    query = query != undefined ? query : '';
		}
	    
	    // console.log('get: list?offset=' + offset + '&f=' + sortField +
		// '&s=' + sortOrder + '&q=' + query);
		
		$.get('pagination?offset=' + offset + '&f=' + sortField + '&s=' + sortOrder + '&q=' + query, data => {
	        // console.log(data);
			$('.content').html(data);
			location.hash = offset;
	   	});
	}
});