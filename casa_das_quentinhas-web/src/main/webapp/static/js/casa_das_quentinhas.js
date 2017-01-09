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

$(document).ready(function($) {
	$(document).bind("ajaxSend", function() {
		$("div#loading").modal('show');
		//console.log('loading show');
	}).bind("ajaxComplete", function() {
		$("div#loading").modal('hide');
		//console.log('loading hide');
	});

	var token = $('meta[name="_csrf"]').attr('content');
	var header = $('meta[name="_csrf_header"]').attr('content');
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
//		console.log('ajaxSend(token: ' + token + ', header: ' + header + ')');
	});
});