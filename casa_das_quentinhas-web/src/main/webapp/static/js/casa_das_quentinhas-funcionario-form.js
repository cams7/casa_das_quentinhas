$('input#celular').mask('(99) 99999-9999');
$('input#cpf').mask('999.999.999-99');

$('input#empresa').autocomplete({
	source : function(request, response) {
		$.getJSON(MAIN_PAGE + '/empresas/' + request.term, function(data) {
			// console.log(data);
			response($.map(data, function(razaoSocialWithCnpj, i) {
				return {
					id : i,
					label : razaoSocialWithCnpj,
					value : razaoSocialWithCnpj
				};
			}));
		});
	},
	select : function(event, ui) {
		$('input#empresa_id').val(ui.item.id);
	},
	minLength : 1
});