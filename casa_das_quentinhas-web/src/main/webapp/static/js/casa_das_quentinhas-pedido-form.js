$(document).ready(function($) {	
	$('select#tipoCliente').change(event => {
		event.preventDefault();
		$('input#cliente, input#cliente_id').val('');
		if(event.target.value == 'PESSOA_JURIDICA'){
			$('input#cliente').attr('placeholder', 'Razão social / CNPJ');
			$('label[for="cliente.nome"]').html('Empresa');
		}else{
			$('input#cliente').attr('placeholder', 'Nome / CPF / Telefone');
			$('label[for="cliente.nome"]').html('Cliente');
		}
	});
	
    $('input#cliente').autocomplete({
        source : function(request, response) {
            $.getJSON( MAIN_PAGE + ($( 'select#tipoCliente option:selected' ).val() == 'PESSOA_JURIDICA' ? '/empresas' : '/clientes') + '/' + request.term, function( data ) {
            	// console.log(data);
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
            $('input#cliente_id').val(ui.item.id);
        },
        minLength : 1
    });
});