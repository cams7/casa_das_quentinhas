$('input#celular').mask('(99) 99999-9999');
$('input#cpf').mask('999.999.999-99');

$('input#empresa').autocomplete({
	source : function(request, response) {
		$.getJSON(MAIN_PAGE + '/empresas/' + request.term, function(data) {
			response($.map(data, function(razaoSocialOrCnpj, i) {
				return {
					id : i,
					label : razaoSocialOrCnpj,
					value : razaoSocialOrCnpj
				};
			}));
		});
	},
	select : function(event, ui) {
		$('input#empresa_id').val(ui.item.id);
	},
	minLength : 1
});