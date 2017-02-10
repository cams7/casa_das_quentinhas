$('select#tipoCliente').change(function(event) {
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
        $.getJSON( MAIN_PAGE + ($( 'select#tipoCliente option:selected' ).val() == 'PESSOA_JURIDICA' ? '/empresas' : '/clientes') + '/' + request.term, function(data) {
        	response(
                $.map(data, function (nomeOrCnpjOrCpfOrTelefone, i) {
                    return {
                        id: i,
                        label: nomeOrCnpjOrCpfOrTelefone,
                        value: nomeOrCnpjOrCpfOrTelefone
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

$('input#entregador').autocomplete({
    source : function(request, response) {
        $.getJSON( MAIN_PAGE + '/entregadores/' + request.term, function(data) {
        	response(
                $.map(data, function (nomeOrCpfOrCelular, i) {
                    return {
                        id: i,
                        label: nomeOrCpfOrCelular,
                        value: nomeOrCpfOrCelular
                    };
                })
            );
        });
    }, 
    select: function (event, ui) {
        $('input#entregador_id').val(ui.item.id);
    },   
    minLength : 1
});

//$('form#pedido_form').submit(function( event ) {
//	if($('form#pedido_form :input[name="entregador.nome"]').val().trim() == '')
//		$('form#pedido_form :input[name="entregador.id"]').val('');
//});