$(document).ready(function($) {	
	$('#tipoCliente').change(event => {
		event.preventDefault();
		$('#cliente, #cliente_id').val('');
		if(event.target.value == 'PESSOA_JURIDICA')
			$('#cliente').attr('placeholder', 'Razão social / CNPJ');
		else
			$('#cliente').attr('placeholder', 'Nome / CPF / Telefone');
	});
	
    $('#cliente').autocomplete({
        source : function(request, response) {
            $.getJSON( MAIN_PAGE + ($( '#tipoCliente option:selected' ).val() == 'PESSOA_JURIDICA' ? '/empresas' : '/clientes') + '/' + request.term, function( data ) {
            	//console.log(data);
            	console.log();
                response(
                    $.map(data, function (nomeWithCpfOrCnpj, i) {
                        return {
                            id: i,
                            label: nomeWithCpfOrCnpj,
                            value: nomeWithCpfOrCnpj
                        };
                    })
                );
            });
        }, 
        select: function (event, ui) {
            $('#cliente_id').val(ui.item.id);
        },
        minLength : 1
    });
});