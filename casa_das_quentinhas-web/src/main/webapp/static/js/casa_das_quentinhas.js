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