function getTotalPages(){
	var count = $('input#dataTable_count').val();
	var maxResults = $('input#dataTable_maxResults').val();
		
	var page = Math.trunc(count / maxResults);
	if(count % maxResults > 0)
		page++;
	
	return page;	
}

function getPageFirst(){
	return ($('div#report_modal div#intervalo').slider('values', 0) - 1) * $('input#dataTable_maxResults').val();	
}

function getPageSize(){
	return ($('div#report_modal div#intervalo').slider('values', 1) - $('div#report_modal div#intervalo').slider('values', 0) + 1) * $('input#dataTable_maxResults').val();
}

function getSortOrder(sortOrder){
	switch (sortOrder) {
	case 'sorting_asc':
		return 'ASCENDING';
	case 'sorting_desc':
		return 'DESCENDING';
	default:
		break;
	}	
	return 'UNSORTED';
}

function atualizaIntervalos(min, max) {
	$('div#report_modal form input#intervalo_informado').val(min + ' - ' + max);
	$('div#report_modal form input[name=pagina][value="selecionada"]').prop('checked', true);
}

function setIntervalos() {
	$('div#report_modal div#intervalo').slider('values', 0, 1);
	$('div#report_modal div#intervalo').slider('values', 1, 1);
	$('div#report_modal div#intervalo').slider('option',{min: 1, max: getTotalPages()});
	atualizaIntervalos(1, 1);
}

function loadTable(removedRow, offset, sortField, sortOrder, query) {		
	if(offset == undefined || offset == null){
    	offset = $('input#dataTable_offset').val();
    	offset = offset != undefined ? offset : 0;
    	
    	if((removedRow != undefined && removedRow) && offset > 0) {
    		var maxResults = $('input#dataTable_maxResults').val();
    		var count = $('input#dataTable_count').val();
    		
    		if(count % maxResults == 1)
    			offset -= maxResults;
    	}
    }
    
    if(sortField == undefined || sortField == null){
    	sortField = $('input#dataTable_sortField').val();
    	sortField = sortField != undefined ? sortField : 'id';
    }
    
    if(sortOrder == undefined || sortOrder == null){
    	sortOrder = $('input#dataTable_sortOrder').val();
    	sortOrder = sortOrder != undefined ? sortOrder : 'sorting_desc';
    }
    
    if(query == undefined || query == null){
		query = $('input#dataTable_query').val();
	    query = query != undefined ? query : '';
	}
    	
	$.get(LIST_PAGE + '?offset=' + offset + '&f=' + sortField + '&s=' + sortOrder + '&q=' + query, function(data) {
       $('.content').html(data);
   	});	
}

$('button#search_btn').click(function(event) {
	event.preventDefault();
	
	query = $('input#search_query').val();
	loadTable(false, 0, null, null, query);
});

$('button#report').click(function(event) {
	event.preventDefault();
	
	$('div#report_modal form').trigger('reset');
	setIntervalos();
	
    $('div#report_modal').modal('show');
});

$(document).ready(function($) {
//$(function() {
	$(document).on('click', '.pagination a', function(event) {
	    event.preventDefault();
	    
	    offset = 0;
	    array = event.target.href.split('offset=');
		
		if(array.length > 1)
			offset = array[1];
		
		loadTable(false, offset);
	});	
			
	$(document).on('click', 'table.dataTable tr:eq(0)', function(event) {
		event.preventDefault();
		
		var column = $(event.target);
		var attr = column.attr('id');
		
		if(attr != undefined) {			
			var sortField = column.attr('id');
			var sortOrder = column.hasClass('sorting') || column.hasClass('sorting_desc') ? 'sorting_asc' : 'sorting_desc';							
			
			loadTable(false, null, sortField, sortOrder);		
		}			
	});	
	
	$(document).on('click', 'table.dataTable button.delete', function(event) {
        event.preventDefault();
                
        $('div#delete_modal form').attr('action', DELETE_PAGE + '/' + event.target.value + '/delete');
        $('div#delete_modal div.modal-body').html(event.target.title);
        $('div#delete_modal').removeClass('delete_from_show_page').addClass('delete_from_list_page').modal('show');
    });
    
    $(document).on('submit', 'div.delete_from_list_page form', function(event) {
        event.preventDefault();
                
        var form = event.target;        
                
        $.ajax({
            url: form.action,
            type: form.method,
            success: function(data) {
            	createSuccessMessage(data.message);            	
            	loadTable(true);            	             	           	
            },
            error: function(data) { 
            	createErrorMessage(data.status, data.responseJSON.message);  
            },
            complete: function(data) {
            	$('div#delete_modal').modal('hide');
            }
        });
    });  
    
    $('div#report_modal div#intervalo').slider({
        range: true,
        slide: function( event, ui ) {
        	atualizaIntervalos(ui.values[0], ui.values[1]);        	
        }
    });
    
    $('div#report_modal form').on('submit', function(event) {      
        event.preventDefault();
        
        var form = $(event.target);
              
        var params = '?';
        
        switch (form.find('input[name=pagina]:checked').val()) {
		case 'todas':
			break;
		case 'selecionada':
			params += 'page_first=' + getPageFirst() + '&page_size=' + getPageSize();
			break;
		case 'atual':
			params += 'page_first=' + $('input#dataTable_offset').val() + '&page_size=' + $('input#dataTable_maxResults').val();
			break;
		default:
			break;
		}
                
        var sortField = $('input#dataTable_sortField').val();
        if(sortField != undefined){
        	params += (params != '?' ? '&': '') + 'sort_field=' + sortField;
        
        	var sortOrder = $('input#dataTable_sortOrder').val();
        	if(sortOrder != undefined)
        		params += '&sort_order=' + getSortOrder(sortOrder);
        }
        
        var query = $('input#dataTable_query').val();
        if(query != undefined && query != ''){
        	params += (params != '?' ? '&': '') + 'globalFilter=' + query;
        	$.each($('input#dataTable_globalFilters').val().split(','), function(index, value) {
        		  params += '&filter_field=' + value;
        	});
        }
        
        var url = MAIN_PAGE + '/report/pdf' + params;
        
       window.open(url,'_blank');
                       
        $('div#report_modal').modal('hide');
    });
});