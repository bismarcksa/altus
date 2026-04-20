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

Altus.BuscaCEP = (function () {

    class BuscaCEP {
        constructor() {
            this.inputCep = $('.js-cep');
            this.comboEstado = $('#estado');
            this.comboCidade = $('#cidade');
			this.ultimoCep = null;
        }

        enable() {
            this.inputCep.on('blur', this.buscar.bind(this));
        }

        buscar(event) {
            const cep = $(event.currentTarget).val().replace(/\D/g, '');

			if (cep.length !== 8) {
				return;
			}
			
			if (cep === this.ultimoCep) {
			    return;
			}

			this.ultimoCep = cep;

            $('.js-img-loading').show();

			$('input[name="endereco.logradouro"]').val('');
			$('input[name="endereco.bairro"]').val('');
			this.comboCidade.val('');
			
            fetch(`https://viacep.com.br/ws/${cep}/json/`)
                .then(response => response.json())
                .then(data => {

                    if (data.erro) {
                        alert('CEP não encontrado');
                        return;
                    }

                    // Preenche campos básicos
                    $('input[name="endereco.logradouro"]').val(data.logradouro);
                    $('input[name="endereco.bairro"]').val(data.bairro);

                    // 🔥 INTEGRAÇÃO ESTADO + CIDADE
                    this.selecionarEstadoECidade(data.uf, data.localidade);
					
					// 🔥 Foco automático no número
					$('input[name="endereco.numero"]').focus();

                })
                .catch(() => {
                    alert('Erro ao buscar CEP');
                })
                .finally(() => {
                    $('.js-img-loading').hide();
                });
        }

        selecionarEstadoECidade(uf, cidadeNome) {

			const estadosUF = {
			        "AC": "Acre",
			        "AL": "Alagoas",
			        "AP": "Amapá",
			        "AM": "Amazonas",
			        "BA": "Bahia",
			        "CE": "Ceará",
			        "DF": "Distrito Federal",
			        "ES": "Espírito Santo",
			        "GO": "Goiás",
			        "MA": "Maranhão",
			        "MT": "Mato Grosso",
			        "MS": "Mato Grosso do Sul",
			        "MG": "Minas Gerais",
			        "PA": "Pará",
			        "PB": "Paraíba",
			        "PR": "Paraná",
			        "PE": "Pernambuco",
			        "PI": "Piauí",
			        "RJ": "Rio de Janeiro",
			        "RN": "Rio Grande do Norte",
			        "RS": "Rio Grande do Sul",
			        "RO": "Rondônia",
			        "RR": "Roraima",
			        "SC": "Santa Catarina",
			        "SP": "São Paulo",
			        "SE": "Sergipe",
			        "TO": "Tocantins"
			    };

			    const nomeEstado = estadosUF[uf];

			    // 🔥 Seleciona estado corretamente
			    this.comboEstado.find('option').each(function () {
			        if ($(this).text().trim() === nomeEstado) {
			            $(this).prop('selected', true);
			            return false;
			        }
			    });

			    this.comboEstado.trigger('change');

			    // 🔥 Agora resolve cidade (esperando carregar)
			    let tentativas = 0;

			    const intervalo = setInterval(() => {

			        tentativas++;

			        if (this.comboCidade.find('option').length > 1) {

			            this.comboCidade.find('option').each(function () {
			                if ($(this).text().toLowerCase().includes(cidadeNome.toLowerCase())) {
			                    $(this).prop('selected', true);
			                    return false;
			                }
			            });

			            this.comboCidade.trigger('change');
			            clearInterval(intervalo);
			        }

			        if (tentativas > 10) {
			            clearInterval(intervalo);
			        }

			    }, 300);
        }
    }

    return BuscaCEP;

})();


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

	var buscaCep = new Altus.BuscaCEP();
	buscaCep.enable();
});

