$(document).ready(function($) {
	$('#cpf').mask('999.999.999-99');
	$("#nascimento").mask("99/99/9999");
    $("#telefone").mask("(99) 9999-9999");
    $("#cep").mask("99999-999");

    $('#cidade').autocomplete({
        source : function(request, response) {
            $.getJSON( MAIN_PAGE + '/cidades/' + request.term, function( data ) {
            	//console.log(data);
                response(
                    $.map(data, function (nomeWithCpf, i) {
                        return {
                            id: i,
                            label: nomeWithCpf,
                            value: nomeWithCpf
                        };
                    })
                );
            });
        }, 
        select: function (event, ui) {
            $('#cidade_id').val(ui.item.id);
        },
        minLength : 1
    });
});