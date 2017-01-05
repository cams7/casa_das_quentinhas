$(document).ready(function($) {
	$('input#custo').maskMoney({
		showSymbol : true,
		symbol : 'R$ ',
		decimal : ',',
		thousands : '.'
	});
});