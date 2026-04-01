Brewer = Brewer || {};

Brewer._pesquisaRapidaClienteIniciada = Brewer._pesquisaRapidaClienteIniciada || false;

Brewer.PesquisaRapidaCliente = (function () {

    function PesquisaRapidaCliente() {
		this.pesquisaRapidaClienteModal = $('#pesquisaRapidaClientes');
		this.nomeInput = $('#nomeClienteModal');
		this.pesquisaRapidaBtn = $('.js-pesquisa-rapida-clientes-btn');
		this.containerTabelaPesquisa = $('#containerTabelaPesquisaRapidaClientes');
		this.htmlTabelaPesquisa = $('#tabela-pesquisa-rapida-cliente').html();
		this.template = Handlebars.compile(this.htmlTabelaPesquisa);
		this.mensagemErro = $('.js-mensagem-erro');
	
	}

    PesquisaRapidaCliente.prototype.iniciar = function () {

        if (Brewer._pesquisaRapidaClienteIniciada) {
            return;
        }

        Brewer._pesquisaRapidaClienteIniciada = true;

		this.pesquisaRapidaBtn.on('click', onBtnPesquisaRapidoClicado.bind(this));
		this.pesquisaRapidaClienteModal.on('shown.bs.modal',onModalShow.bind(this));
		
    };
	
	function onModalShow() {
		this.nomeInput.focus();
	}
	
	function onBtnPesquisaRapidoClicado(event) {
		event.preventDefault();
		$.ajax({
			//url: this.pesquisaRapidaClienteModal.find('form').attr('action'), NÃO PEGA O NOME CORRETAMENTE
			url: '/brewer/clientes/pesquisa',			
			method: 'GET',
			data: {nome: this.nomeInput.val()},
			success: onPesquisaConcluida.bind(this),
			error: onErroPesquisa.bind(this)
		});

	//		        function onPesquisaConcluida(resultado) {
	//		            this.mensagemErro.addClass('hidden');
	//		            var html = this.template(resultado);
	//		            this.containerTabelaPesquisa.html(html);

	//		            var tabelaClientePesquisaRapida = new Brewer.TabelaClientePesquisaRapida(this.pesquisaRapidaClienteModal);
	//		            tabelaClientePesquisaRapida.iniciar();
	//		        }


	//		        function onErroPesquisa() {
	//		            this.mensagemErro.removeClass('hidden');
		//	        }
	}
	
	function onPesquisaConcluida(resultado) {
		this.mensagemErro.addClass('hidden');
		
		var html = this.template(resultado);
		this.containerTabelaPesquisa.html(html);

		var tabelaClientePesquisaRapida = new Brewer.TabelaClientePesquisaRapida(this.pesquisaRapidaClienteModal);
		tabelaClientePesquisaRapida.iniciar();
	}


	function onErroPesquisa() {
		this.mensagemErro.removeClass('hidden');
	}

    return PesquisaRapidaCliente;

}());


Brewer.TabelaClientePesquisaRapida =(function(){

    function TabelaClientePesquisaRapida(modal) {
        this.modalCliente = modal;
        this.cliente = $('.js-cliente-pesquisa-rapida');
    }

    TabelaClientePesquisaRapida.prototype.iniciar = function () {
        this.cliente.on('click',onClienteSelecionado.bind(this));
    };
    
    function onClienteSelecionado(event) {
        var clienteSelecionado = $(event.currentTarget);
        this.modalCliente.modal('hide');

        $('#nomeCliente').val(clienteSelecionado.data('nome'));
        $('#codigoCliente').val(clienteSelecionado.data('codigo'));
    }

    return TabelaClientePesquisaRapida;

}());



$(function () {
    new Brewer.PesquisaRapidaCliente().iniciar();
});
