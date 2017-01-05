function setPedido (pedido) {
    // console.log(pedido);

    $('form#pedido_form :input[name="quantidade"]').val(pedido.quantidade > 0 ? pedido.quantidade : '');

    if(pedido.custo > 0)
        $('form#pedido_form :input[name="custo"]').val(Number(pedido.custo).formatMoney());
    else
        $('form#pedido_form :input[name="custo"]').val('');
}

$(document).ready(function($) {
    $('div#item_modal form#item_form :input[name="produto"]').autocomplete({
        source : function(request, response) {
           $.getJSON(MAIN_PAGE + '/produtos/' + request.term, data => {                                      
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
            $('div#item_modal form#item_form :input[name="produto_id"]').val(ui.item.id);
        },
        open: function(event, ui) {
            $('.ui-autocomplete').css('z-index', '1050');
        },
        close: function (event, ui) {                   
            $('.ui-autocomplete').css('z-index', '0');
        },
        minLength : 1
    }); 

    $('button#itens_refresh').click(event => {
    	event.preventDefault();
        
    	loadTable();
    }); 

    $('button#item_add').click(event => {
    	event.preventDefault();
    	
        $('div#item_modal form#item_form').trigger('reset');
        $('div#item_modal form#item_form :input[name="produto_id"]').val('');
        $('div#item_modal form#item_form :input[name="produto"]').prop('readonly', false);
        $('input#modal_state').val('add');        
        $('div#item_modal').modal('show');
    });
 
    $(document).on('click', 'button.item-updade', event => {
        event.preventDefault();

        $('div#item_modal form#item_form').trigger('reset');
        $('div#item_modal form#item_form :input[name="produto_id"]').val('');
        $('div#item_modal form#item_form :input[name="produto"]').prop('readonly', true);

        var id = event.target.value;

        var url = $('div#item_modal form#item_form').attr('action');
        url = url + '/' + id;
        console.log('GET ' + url);

        $.ajax({
            url: url,
            type: 'GET',
            success: data => { 
                //console.log('Sucess:');
                //console.log(data);

                $('div#item_modal form#item_form :input[name="produto_id"]').val(data.id.produtoId);
                $('div#item_modal form#item_form :input[name="produto"]').val(data.produto.nomeWithTamanho);
                $('div#item_modal form#item_form :input[name="quantidade"]').val(data.quantidade);

                $('input#modal_state').val('update');
                $('div#item_modal').modal('show');
            },
            error: data => {
                console.log('Error:');
                console.log(data);
            }
        });        
    });   

    $(document).on('click', 'button.item-delete', event => {
        event.preventDefault();

        var id = event.target.value;
        
        var url = $('div#item_modal form#item_form').attr('action') + '/' + id + '/delete';
        console.log('DELETE ' + url);

        $.ajax({
            url: url,
            type: 'GET',         
            success: data => {
                // console.log('Sucess:');
                // console.log(data);
                setPedido(data);

                loadTable();
            },
            error: data => {
                console.log('Error:');
                console.log(data);
            }
        });
    });

    $('div#item_modal form#item_form').validator().on('submit', event => {      
        if (event.isDefaultPrevented()) 
            return; 
        
        event.preventDefault();
        
        var form = event.target;
        
        if($(form.produto_id).val() == '' || $(form.quantidade).val() < 1)
            return;
 
		// $.ajaxSetup({
		// header:$(':input[name="_csrf"]').val()
		// })

       
        var state = $('input#modal_state').val();    
        
        $.ajax({
            type: 'GET',
            url: form.action,
            data: $(form).serialize(),            
            success: data => {
                // console.log('Sucess:');
                // console.log(data);
                setPedido(data);

                loadTable();

                $('div#item_modal').modal('toggle');
            },
            error: data => {
                console.log('Error:');
                console.log(data);
            }
        })        
    });
});