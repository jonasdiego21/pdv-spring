var Pdv = Pdv || {};

Pdv.Security = (function() {
	
	function Security() {
		this.token = $('input[name=_csrf]').val();
		this.header = $('input[name=_csrf_header]').val();
	}
	
	Security.prototype.enable = function() {
		$(document).ajaxSend(function(event, jqxhr, settings) {
			jqxhr.setRequestHeader(this.header, this.token);
		}.bind(this));
	}
	
	return Security;
	
}());

$(function() {
	var security = new Pdv.Security();
	security.enable();
});