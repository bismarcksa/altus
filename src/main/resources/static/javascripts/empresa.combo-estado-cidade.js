var Altus = Altus || {};

Altus.ComboEstado = (function(){

    function ComboEstado() {
        this.combo = $('#estado');
        this.emitter = $({});  // Objeto para auxiliar lançar eventos
        this.on = this.emitter.on.bind(this.emitter); // Lança eventos em "iniciar"
    }

    ComboEstado.prototype.iniciar = function(){
        this.combo.on('change', onEstadoAlterado.bind(this));

        function onEstadoAlterado() {
//			console.log('ESTADO ALTERADO: ', this.combo.val());
            this.emitter.trigger('alterado', this.combo.val());			
        }
    };
    return ComboEstado;
}());

Altus.ComboCidade = (function(){

    function ComboCidade(comboEstado) {
        this.comboEstado = comboEstado;
        this.combo = $('#cidade');
        this.imgLoading = $('.js-img-loading');
		this.inputHiddenCidadeSelecionada = $('#inputHiddenCidadeSelecionada');
    }

    ComboCidade.prototype.iniciar = function(){
        resetComboCidade.call(this);
        this.comboEstado.on('alterado', onEstadoAlterado.bind(this));
		var idEstado = this.comboEstado.combo.val();
		inicializarCidades.call(this, idEstado);
    };

    function onEstadoAlterado(evento, idEstado) {  		
		this.inputHiddenCidadeSelecionada.val('');
		inicializarCidades.call(this, idEstado);
    }
	
	function inicializarCidades(idEstado){
		if(idEstado){
		    var resposta = $.ajax({
			    	url: this.combo.data('url'),
			    	method: 'GET',
			    	contentType: 'application/json',
			    	data: {'estado': idEstado},
			    	beforeSend: iniciarRequisicao.bind(this),
			    	complete: finalizarRequisicao.bind(this)
		    });
		    resposta.done(onBuscarCidadesFinalizado.bind(this));
		} else{
			resetComboCidade.call(this);
		}
	}

    function onBuscarCidadesFinalizado(cidades) {
		console.log('VEJA: ', cidades);
        var options = [];
        cidades.forEach(function(cidade){
        		options.push('<option value="' + cidade.id + '">' + cidade.nome + '</option>');
        });
		
        this.combo.html(options.join(''));
        this.combo.removeAttr('disabled');
		
		var codigoCidadeSelecionada = this.inputHiddenCidadeSelecionada.val();
		if(codigoCidadeSelecionada){
			this.combo.val(codigoCidadeSelecionada);
		}
    }

    function resetComboCidade() {
        this.combo.html('<option value="">Selecione uma cidade</option>');
        this.combo.val('');
        this.combo.attr('disabled', 'disabled');
    }

    function iniciarRequisicao() {
        resetComboCidade.call(this);
        this.imgLoading.show();
    }

    function finalizarRequisicao() {
        this.imgLoading.hide();
    }

    return ComboCidade;

}());


$(function () {
    var comboEstado = new Altus.ComboEstado();
    comboEstado.iniciar();

    var comboCidade = new Altus.ComboCidade(comboEstado);
    comboCidade.iniciar();
});