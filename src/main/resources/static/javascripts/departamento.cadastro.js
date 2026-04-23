var Brewer = Brewer || {};

Brewer.DepartamentoCadastro = (function(){
	
	function DepartamentoCadastro(){
		this.modal = $('#modalCadastroDepartamento');
		this.selectEmpresa = $('#empresa');
		this.botaoSalvar = this.modal.find('.js-modal-cadastro-salvar-btn');
		this.form = this.modal.find('form');
		this.url = this.form.attr('action');
		this.inputNomeDepartamento = $('#nomeDepartamento');
		this.containerMensagemErro = $('.js-mensagem-cadastro-departamento');
	}
	
	DepartamentoCadastro.prototype.iniciar = function(){
		this.form.on('submit', function(event) {event.preventDefault()});
		this.modal.on('show.bs.modal', onModalShow.bind(this));
		this.modal.on('hide.bs.modal', onModalClose.bind(this));
		this.botaoSalvar.on('click', onBotaoSalvarClick.bind(this));
	}
	
	function onModalShow(){
		this.inputNomeDepartamento.focus();
	}
	
	function onModalClose(){
		this.inputNomeDepartamento.val('');
		this.selectEmpresa.val('');
		this.containerMensagemErro.addClass('d-none');
		this.containerMensagemErro.removeClass('alert-success').addClass('alert-danger');
	}
	
	function onBotaoSalvarClick(){
		var nomeDepartamento = this.inputNomeDepartamento.val().trim();
		var empresaId = this.selectEmpresa.val();

		var dados = {
			nome: nomeDepartamento
		};

		// Só adiciona a empresa ao objeto se houver um ID selecionado
		if (empresaId) {
			dados.empresa = { id: empresaId };
		}

		$.ajax({
			url: this.url,
		    method: 'POST',
		    contentType: 'application/json',
		    data: JSON.stringify(dados), // Agora 'empresa' pode ir nulo se não selecionado
		    success: onDepartamentoSalvo.bind(this),
		    error: onErroSalvandoDepartamento.bind(this)
		});
	}
		
	function onErroSalvandoDepartamento(obj){
		var erros = obj.responseJSON;

		// fallback seguro
		if (!Array.isArray(erros)) {
			if (obj.responseText) {
				erros = [obj.responseText];
			} else {
				erros = ["Erro inesperado"];
			}
		}

		this.containerMensagemErro.removeClass('d-none');
		var html = erros.map(e => '<span>' + e + '</span>').join('<br>');
		this.containerMensagemErro.html(html);
		this.form.find('.form-group').addClass('has-error');
	}
		
	function onDepartamentoSalvo(htmlFragmento){
		// 1. Atualiza a tabela com o HTML que veio do servidor
		    // Substituímos o conteúdo do tbody pelo novo fragmento
		    $('.js-tabela-departamentos tbody').replaceWith(htmlFragmento);

		    // 2. Mostra mensagem de sucesso
		    this.containerMensagemErro.removeClass('d-none alert-danger').addClass('alert-success');
		    this.containerMensagemErro.html('<span>Departamento salvo com sucesso!</span>');

		    // 3. Limpa o formulário
		    this.inputNomeDepartamento.val('');
		    this.selectEmpresa.val('');
		    this.inputNomeDepartamento.focus();
			
		//FECHA MODAL
//		this.modal.modal('hide');

		// opcional: recarregar página
//		location.reload();
	}
	
	return DepartamentoCadastro;
}());

$(function(){

	var estiloCadastroRapido = new Brewer.DepartamentoCadastro();
	estiloCadastroRapido.iniciar();
	
});