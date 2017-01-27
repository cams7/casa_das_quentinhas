function setPedido (pedido) {
    // console.log(pedido);

    $('form#pedido_form :input[name="quantidade"]').val(pedido.quantidade > 0 ? pedido.quantidade : '');

    if(pedido.custo > 0)
        $('form#pedido_form :input[name="custo"]').val(pedido.formattedCusto);
    else
        $('form#pedido_form :input[name="custo"]').val('');
}

$('button#item_add').click(function(event) {
	event.preventDefault();
	
    $('div#item_modal form').trigger('reset');
    $('div#item_modal form :input[name="produto_id"]').val('');
    $('div#item_modal form :input[name="produto"]').prop('readonly', false);
    $('div#item_modal').modal('show');
});

$(document).ready(function($) {
//$(function() {	
    $('div#item_modal form :input[name="produto"]').autocomplete({
        source : function(request, response) {
           $.getJSON(MAIN_PAGE + '/produtos/' + request.term, function(data) {                                      
                response(
                    $.map(data, function (nomeWithTamanho, i) {
                        return {                            
                        	id: i,
                        	label: nomeWithTamanho,
                            value: nomeWithTamanho                            
                        };
                    })
                );
            });
        }, 
        select: function (event, ui) {
            $('div#item_modal form :input[name="produto_id"]').val(ui.item.id);
        },
        open: function(event, ui) {
            $('.ui-autocomplete').css('z-index', '1050');
        },
        close: function (event, ui) {                   
            $('.ui-autocomplete').css('z-index', '0');
        },
        minLength : 1
    }); 
    
    $(document).on('click', 'button.item-updade', function(event) {
        event.preventDefault();

        $('div#item_modal form').trigger('reset');
        $('div#item_modal form :input[name="produto_id"]').val('');
        $('div#item_modal form :input[name="produto"]').prop('readonly', true);

        var id = event.target.value;

        var url = ITEM_PAGE + '/' + id;

        $.ajax({
            url: url,
            type: 'GET',
            success: function(data) {
            	var item = data.entity;
            	// console.log(item);
            	
                $('div#item_modal form :input[name="produto_id"]').val(item.id.produtoId);
                $('div#item_modal form :input[name="produto"]').val(item.produto.nomeWithTamanho);
                $('div#item_modal form :input[name="quantidade"]').val(item.quantidade);

                $('div#item_modal').modal('show');
            },
            error: function(data) {
            	createErrorMessage(data.status, data.responseJSON.message);
            }
        });        
    });   

    $(document).on('click', 'button.item-delete', function(event) {
        event.preventDefault();
        
        var id = event.target.value;
        
        var url = $('div#item_modal form').attr('action') + '/' + id + '/delete';
        var method = $('div#item_modal form').attr('method');
        
        $.ajax({
            url: url,
            type: method,         
            success: function(data) {            	
                setPedido(data.entity);
                loadTable(true);
            },
            error: function(data) {
            	createErrorMessage(data.status, data.responseJSON.message);
            }
        });
    });

    $('div#item_modal form').validator().on('submit', function(event) {      
        if (event.isDefaultPrevented()) 
            return; 
        
        event.preventDefault();
        
        var form = event.target;
        
        
        if($(form.produto_id).val() == '' || $(form.quantidade).val() < 1)
            return;
        
        var url = form.action;
        var method = form.method;
        
        $.ajax({
        	url: url,
        	type: method,            
            data: $(form).serialize(),            
            success: function(data) {
            	setPedido(data.entity);
                loadTable();                
            },
            error: function(data) {
            	createErrorMessage(data.status, data.responseJSON.message);
            },
            complete: function(data) {
            	$('div#item_modal').modal('hide');
            }
        })        
    });
});