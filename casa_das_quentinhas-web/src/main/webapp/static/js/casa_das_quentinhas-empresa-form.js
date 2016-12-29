$(document).ready(function($) {
	$("#cnpj").mask("99.999.999/9999-99");
    $("#telefone").mask("(99) 9999-9999");
    $("#cep").mask("99999-999");

    $('#cidade').autocomplete({
        source : function(request, response) {
            $.getJSON( MAIN_PAGE + '/cidades/' + request.term, function( data ) {
            	//console.log(data);
                response(
                    $.map(data, function (nomeWithEstadoSigla, i) {
                        return {
                            id: i,
                            label: nomeWithEstadoSigla,
                            value: nomeWithEstadoSigla
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