var Altus = Altus || {};

Altus.MascaraCNPJ = (function () {

    class MascaraCNPJ {
        constructor() {
            this.inputCnpj = $('.js-cnpj');
        }
        enable() {
            this.inputCnpj.mask('00.000.000/0000-00', {
                clearIfNotMatch: true
            });
        }
    }
    return MascaraCNPJ;
}());

Altus.MascaraCEP = (function(){

    class MascaraCEP {
        constructor() {
            this.inputCep = $('.js-cep');
        }
        enable() {
            this.inputCep.mask('00.000-000');
        }
    }
    return MascaraCEP;
}());


Altus.MascaraTELEFONE = (function () {

    class MascaraTELEFONE {
        constructor() {
            this.inputPhone = $('.js-telefone');
        }
        enable() {
            var maskBehavior = function(val) {
                return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
            };

            var options = {
                clearIfNotMatch: true, // Limpa o campo se não corresponder ao padrão
                onKeyPress: function(val, e, field, options) {
                    field.mask(maskBehavior.apply({}, arguments), options);
                }
            };
            this.inputPhone.mask(maskBehavior, options);
        }
    }
    return MascaraTELEFONE;
})();

Altus.MaskDate = (function () {

    function MaskDate() {
        this.inputDate = $('.js-date');
    }

    MaskDate.prototype.enable = function () {
        this.inputDate.mask('00/00/0000');
        this.inputDate.datepicker({
			language: 'pt-BR',
			orientation: 'bottom',
			autoclose: true,
			todayHighlight: true
        });
    };

    return MaskDate;

}());

/*
Altus.Security = (function () {

    function Security() {
        this.token = $('input[name=_csrf]').val();
        this.header = $('input[name=_csrf_header]').val();
    }

    Security.prototype.enable = function () {
        $(document).ajaxSend(function (event,jqxhr,settings) {
            jqxhr.setRequestHeader(this.header,this.token)
        }.bind(this));
    };

    return Security;

}());*/

$(function () {
	var MascaraCNPJ = new Altus.MascaraCNPJ();
	MascaraCNPJ.enable();
	
	var MascaraCEP = new Altus.MascaraCEP();
	MascaraCEP.enable();
	
    var MascaraTELEFONE = new Altus.MascaraTELEFONE();
    MascaraTELEFONE.enable();  

	var maskDate = new Altus.MaskDate();
	maskDate.enable();
	
//	var security = new Altus.Security();
//	security.enable();
});

