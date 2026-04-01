package br.com.aformula.bean;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import br.com.aformula.dao.FuncionarioDAO;
import br.com.aformula.dao.MensagemContatoDAO;
import br.com.aformula.domain.Funcionario;
import br.com.aformula.domain.MensagemContato;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class MensagemContatoBean implements Serializable{
	private static final long serialVersionUID = 1L;

	public MensagemContato mensagemContatoCadastro;
	private List<MensagemContato> listaMensagemContatos;
	private List<MensagemContato> listaMensagemContatosFiltrados;
	private String acao;
	private Long codigo;
	private Long totalNaoLidas;
	private List<Funcionario> listaFuncionariosTodosAtivos;
	
	@ManagedProperty(value = "#{autenticacaoBean}")
	private AutenticacaoBean autenticacaoBean;
	
	public MensagemContato getMensagemContatoCadastro() {
		return mensagemContatoCadastro;
	}

	public void setMensagemContatoCadastro(MensagemContato mensagemContatoCadastro) {
		this.mensagemContatoCadastro = mensagemContatoCadastro;
	}

	public List<MensagemContato> getListaMensagemContatos() {
		return listaMensagemContatos;
	}

	public void setListaMensagemContatos(List<MensagemContato> listaMensagemContatos) {
		this.listaMensagemContatos = listaMensagemContatos;
	}

	public List<MensagemContato> getListaMensagemContatosFiltrados() {
		return listaMensagemContatosFiltrados;
	}

	public void setListaMensagemContatosFiltrados(List<MensagemContato> listaMensagemContatosFiltrados) {
		this.listaMensagemContatosFiltrados = listaMensagemContatosFiltrados;
	}
	
	public List<Funcionario> getListaFuncionariosTodosAtivos() {
		return listaFuncionariosTodosAtivos;
	}

	public void setListaFuncionariosTodosAtivos(List<Funcionario> listaFuncionariosTodosAtivos) {
		this.listaFuncionariosTodosAtivos = listaFuncionariosTodosAtivos;
	}

	public AutenticacaoBean getAutenticacaoBean() {
		return autenticacaoBean;
	}

	public void setAutenticacaoBean(AutenticacaoBean autenticacaoBean) {
		this.autenticacaoBean = autenticacaoBean;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Long getTotalNaoLidas() {
		return totalNaoLidas;
	}

	public void setTotalNaoLidas(Long totalNaoLidas) {
		this.totalNaoLidas = totalNaoLidas;
	}

	public void novo() {
		mensagemContatoCadastro = new MensagemContato();
	}
	
	public void salvar() {
		try {
			//SETA FUNCIONÁRIO QUE FEZ O REGISTRO
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			Funcionario funcionario = funcionarioDAO.buscarPorCodigo(autenticacaoBean.getFuncionarioLogado().getCodigo());				
			mensagemContatoCadastro.setUsuario(funcionario);
			mensagemContatoCadastro.setMensagemNaoLida(0);//SETA MENSAGEM COMO ZERO
			
			MensagemContatoDAO mensagemContatoBuscaDAO = new MensagemContatoDAO();
			MensagemContato codigoContato = mensagemContatoBuscaDAO.buscarContatoPorCodigo(funcionario.getCodigo(), mensagemContatoCadastro.getContato().getCodigo());
			
			if(mensagemContatoCadastro.getContato().getCodigo() == funcionario.getCodigo()) {
				FacesUtil.adicionarMsgAlerta("CONTATO NÃO PERMITIDO.");
				return;
			}
			
			if (codigoContato != null) {
				FacesUtil.adicionarMsgAlerta("CONTATO JÁ CADASTRADO.");
				return;
			}
		
			MensagemContatoDAO mensagemContatoDAO = new MensagemContatoDAO();	
			mensagemContatoDAO.salvar(mensagemContatoCadastro);	
//			mensagemContatoCadastro = new MensagemContato();
			
			//SALVANDO O CONTRÁRIO, O USUARIO COMO CONTATO DA OUTRA PESSOA
			mensagemContatoCadastro.setUsuario(mensagemContatoCadastro.getContato());
			mensagemContatoCadastro.setContato(funcionario);
			mensagemContatoCadastro.setMensagemNaoLida(0);
			
			MensagemContatoDAO mensagemContato2DAO = new MensagemContatoDAO();	
			mensagemContato2DAO.salvar(mensagemContatoCadastro);	
			
			mensagemContatoCadastro = new MensagemContato();
			
			
			FacesUtil.adicionarMsgInfo("CONTATO salvo com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar mensagemContato:" + ex.getMessage());
		}	
	}
	
	public void carregarPesquisa() {
		try {
			//SETA FUNCIONÁRIO QUE FEZ O REGISTRO
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			Funcionario funcionario = funcionarioDAO.buscarPorCodigo(autenticacaoBean.getFuncionarioLogado().getCodigo());				
			
			MensagemContatoDAO mensagemContatoDAO = new MensagemContatoDAO();
			listaMensagemContatos = mensagemContatoDAO.listar(funcionario.getCodigo());
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar mensagemContato:" + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {					
			if (codigo != null) {
				MensagemContatoDAO mensagemContatoDAO = new MensagemContatoDAO();
				mensagemContatoCadastro = mensagemContatoDAO.buscarPorCodigo(codigo);
			}else {
				mensagemContatoCadastro = new MensagemContato();
			} 

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			listaFuncionariosTodosAtivos = funcionarioDAO.listarTodosAtivo();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados da mensagemContato:" + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			MensagemContatoDAO mensagemContatoDAO = new MensagemContatoDAO();
			mensagemContatoDAO.excluir(mensagemContatoCadastro);
			
			FacesUtil.adicionarMsgInfo("MensagemContato removida com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover mensagemContato:" + ex.getMessage());
		}		
	}
	
	public void editar() {
		try {
			MensagemContatoDAO mensagemContatoDAO = new MensagemContatoDAO();						
			mensagemContatoDAO.editar(mensagemContatoCadastro);

			FacesUtil.adicionarMsgInfo("MensagemContato editado com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar mensagemContato:" + ex.getMessage());
		}	
	}
	
	//EXIBE A QUANTIDADE DE MENSAGENS NÃO LIDAS
	public void carregarMensagensNaoLidas() {
		try {	
			
			MensagemContatoDAO mensagemContatoDAO = new MensagemContatoDAO();
			this.totalNaoLidas = mensagemContatoDAO.somaMensagensNaoLida(autenticacaoBean.getFuncionarioLogado().getCodigo());
//			System.out.println("QUANTIDADE NAO LIDAS: " + totalNaoLidas);		
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados da mensagemContato:" + ex.getMessage());
		}
	}
}
