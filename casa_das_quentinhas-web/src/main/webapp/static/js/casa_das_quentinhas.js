Number.prototype.formatMoney = function(c, d, t) {
	var n = this, c = isNaN(c = Math.abs(c)) ? 2 : c, d = d == undefined ? ','
			: d, t = t == undefined ? '.' : t, s = n < 0 ? '-' : '', i = String(parseInt(n = Math
			.abs(Number(n) || 0).toFixed(c))), j = (j = i.length) > 3 ? j % 3
			: 0;
	return 'R$ '
			+ (s + (j ? i.substr(0, j) + t : '')
					+ i.substr(j).replace(/(\d{3})(?=\d)/g, '$1' + t) + (c ? d
					+ Math.abs(n - i).toFixed(c).slice(2) : ''));
};

function createSuccessMessage(message) {
	$('div.alert strong').html('Sucesso!');
	$('div.alert span').html(message);
	$('div.alert').removeClass('alert-danger alert-warning').addClass('alert-success').show(); 
}

function createErrorMessage(status, message) {
	var title = 'Erro interno do servidor!';
	
	switch (status) {
	case 400:		
		title = 'Requisição inválida!';
		$('div.alert').removeClass('alert-success alert-danger').addClass('alert-warning');
		break;
	case 404:	
		title = 'Não encontrado!';
		$('div.alert').removeClass('alert-success alert-danger').addClass('alert-warning');
		break;
	default:
		$('div.alert').removeClass('alert-success alert-warning').addClass('alert-danger');
		break;
	}
	
	$('div.alert strong').html(title);
	$('div.alert span').html(message);
    $('div.alert').show();
}

var loadingShow = event => {
	$('div#loading_modal').modal('show');
	// console.log('loading show');
};

var loadingHide = event => {
	$('div#loading_modal').modal('hide');
	// console.log('loading hide');
};

$(document).ready(function($) {
	$(document).on('ajaxSend', loadingShow).on('ajaxComplete', loadingHide);

	var token = $('meta[name="_csrf"]').attr('content');
	var header = $('meta[name="_csrf_header"]').attr('content');
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
		// console.log('ajaxSend(token: ' + token + ', header: ' + header +
		// ')');
	});
});