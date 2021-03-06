$('input#cnpj').mask('99.999.999/9999-99');
$('input#telefone').mask('(99) 9999-9999');
$('input#cep').mask('99999-999');

$('input#cidade').autocomplete({
	source : function(request, response) {
		$.getJSON(MAIN_PAGE + '/cidades/' + request.term, function(data) {
			response($.map(data, function(nomeOrIbge, i) {
				return {
					id : i,
					label : nomeOrIbge,
					value : nomeOrIbge
				};
			}));
		});
	},
	select : function(event, ui) {
		$('input#cidade_id').val(ui.item.id);
	},
	minLength : 1
});