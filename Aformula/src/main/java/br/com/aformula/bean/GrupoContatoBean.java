package br.com.aformula.bean;
import java.io.IOException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import br.com.aformula.dao.FuncionarioDAO;
import br.com.aformula.dao.GrupoContatoDAO;
import br.com.aformula.dao.GrupoDAO;
import br.com.aformula.dao.MensagemContatoDAO;
import br.com.aformula.domain.GrupoContato;
import br.com.aformula.domain.GrupoContatoID;
import br.com.aformula.domain.MensagemContato;
import br.com.aformula.domain.Funcionario;
import br.com.aformula.domain.Grupo;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class GrupoContatoBean {

	private GrupoContatoID grupoContatoID;
	private GrupoContato grupoContatoCadastro;
	private Grupo grupoCadastro;
	private List<GrupoContato> listaGrupoContatos;
	private List<GrupoContato> listaGrupoContatosFiltrados;	
	private Integer codigo; //CÓDIGO DO GRUPO
	private Integer fun_codigo;
	private List<Funcionario> listaFuncionariosGrupo;
	private List<Funcionario> listaFuncionariosTodosAtivos;
	private List<GrupoContato> listaGrupoFunc;
	public MensagemContato mensagemContatoCadastro;
	private Long totalNaoLidas;
	
	private List<GrupoContato> grupoContatoSelecionados;
	
	@ManagedProperty(value = "#{autenticacaoBean}")
	private AutenticacaoBean autenticacaoBean;
	
	public List<GrupoContato> getListaGrupoContatos() {
		return listaGrupoContatos;
	}

	public void setListaGrupoContatos(List<GrupoContato> listaGrupoContatos) {
		this.listaGrupoContatos = listaGrupoContatos;
	}

	public List<GrupoContato> getListaGrupoContatosFiltrados() {
		return listaGrupoContatosFiltrados;
	}

	public void setListaGrupoContatosFiltrados(List<GrupoContato> listaGrupoContatosFiltrados) {
		this.listaGrupoContatosFiltrados = listaGrupoContatosFiltrados;
	}

	public List<Funcionario> getListaFuncionariosGrupo() {
		return listaFuncionariosGrupo;
	}

	public void setListaFuncionariosGrupo(List<Funcionario> listaFuncionariosGrupo) {
		this.listaFuncionariosGrupo = listaFuncionariosGrupo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getFun_codigo() {
		return fun_codigo;
	}

	public void setFun_codigo(Integer fun_codigo) {
		this.fun_codigo = fun_codigo;
	}
	
	public List<Funcionario> getListaFuncionariosTodosAtivos() {
		return listaFuncionariosTodosAtivos;
	}

	public void setListaFuncionariosTodosAtivos(List<Funcionario> listaFuncionariosTodosAtivos) {
		this.listaFuncionariosTodosAtivos = listaFuncionariosTodosAtivos;
	}

	public GrupoContatoID getGrupoContatoID() {
		return grupoContatoID;
	}

	public void setGrupoContatoID(GrupoContatoID grupoContatoID) {
		this.grupoContatoID = grupoContatoID;
	}

	public GrupoContato getGrupoContatoCadastro() {
		return grupoContatoCadastro;
	}

	public void setGrupoContatoCadastro(GrupoContato grupoContatoCadastro) {
		this.grupoContatoCadastro = grupoContatoCadastro;
	}
	
	public Grupo getGrupoCadastro() {
		return grupoCadastro;
	}

	public void setGrupoCadastro(Grupo grupoCadastro) {
		this.grupoCadastro = grupoCadastro;
	}

	public List<GrupoContato> getListaGrupoFunc() {
		return listaGrupoFunc;
	}

	public void setListaGrupoFunc(List<GrupoContato> listaGrupoFunc) {
		this.listaGrupoFunc = listaGrupoFunc;
	}
	
	public AutenticacaoBean getAutenticacaoBean() {
		return autenticacaoBean;
	}

	public void setAutenticacaoBean(AutenticacaoBean autenticacaoBean) {
		this.autenticacaoBean = autenticacaoBean;
	}

	public List<GrupoContato> getGrupoContatoSelecionados() {
		return grupoContatoSelecionados;
	}

	public void setGrupoContatoSelecionados(List<GrupoContato> grupoContatoSelecionados) {
		this.grupoContatoSelecionados = grupoContatoSelecionados;
	}

	public MensagemContato getMensagemContatoCadastro() {
		return mensagemContatoCadastro;
	}

	public void setMensagemContatoCadastro(MensagemContato mensagemContatoCadastro) {
		this.mensagemContatoCadastro = mensagemContatoCadastro;
	}

	public Long getTotalNaoLidas() {
		return totalNaoLidas;
	}

	public void setTotalNaoLidas(Long totalNaoLidas) {
		this.totalNaoLidas = totalNaoLidas;
	}

	public void novo() {
		grupoContatoCadastro = new GrupoContato();
	}
	
	public void salvar() {
	
		try {		
			grupoContatoID.setGrupo(grupoCadastro);
			grupoContatoCadastro.setGrupoContatoID(grupoContatoID);
			
			GrupoContatoDAO grupoContatoDAO = new GrupoContatoDAO();
			grupoContatoCadastro.setMensagemNaoLida(0);
			grupoContatoDAO.salvar(grupoContatoCadastro);
			
			GrupoContato grupoContatoSalvo = new GrupoContato();
			grupoContatoSalvo = grupoContatoCadastro;
			
			MensagemContatoDAO mensagemContatoDAO = new MensagemContatoDAO();
			
			listaGrupoContatos = grupoContatoDAO.buscarGrupoPorCodigo(grupoContatoCadastro.getGrupoContatoID().getGrupo().getCodigo());
			for (GrupoContato grupoContato : listaGrupoContatos) { 	
				
				//AQUI NÃO PERMITE QUE FAÇA O CONTATO DE VOCE MESMO
				if(grupoContatoSalvo.getGrupoContatoID().getFuncionario().equals(grupoContato.getGrupoContatoID().getFuncionario())) {
//					System.out.println("ENTROU");
					continue;
				}
								
				mensagemContatoCadastro = new MensagemContato();
				mensagemContatoCadastro.setUsuario(grupoContatoSalvo.getGrupoContatoID().getFuncionario());
				mensagemContatoCadastro.setContato(grupoContato.getGrupoContatoID().getFuncionario());
				mensagemContatoCadastro.setGrupo(grupoContatoCadastro.getGrupoContatoID().getGrupo());
				mensagemContatoCadastro.setMensagemNaoLida(0);//SETA MENSAGEM COMO ZERO
				
				mensagemContatoDAO.salvar(mensagemContatoCadastro);
//				System.out.println("CHEGOU AQUI");
				//INVERTE A TROCAR OS CONTATO COM USUARIO E VICE - VERSA
				mensagemContatoCadastro = new MensagemContato();
				mensagemContatoCadastro.setUsuario(grupoContato.getGrupoContatoID().getFuncionario());
				mensagemContatoCadastro.setContato(grupoContatoSalvo.getGrupoContatoID().getFuncionario());
				mensagemContatoCadastro.setGrupo(grupoContatoCadastro.getGrupoContatoID().getGrupo());
				mensagemContatoCadastro.setMensagemNaoLida(0);//SETA MENSAGEM COMO ZERO
				
				mensagemContatoDAO.salvar(mensagemContatoCadastro);				
				
//				System.out.println("TERMINOU");
				
			}					
				
			grupoContatoCadastro = new GrupoContato();		
			
			FacesUtil.adicionarMsgInfo("Contato de Grupo salvo com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar Contato de Grupo:" + ex.getMessage());
		}
	}	
	
	public void carregarPesquisa() {
		try {		
			if (codigo != null) {
				GrupoContatoDAO grupoContatoDAO = new GrupoContatoDAO();		
				listaGrupoContatos = grupoContatoDAO.listar(codigo);				
			}					
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar Contatos de Grupo:" + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {	
			if (codigo != null) {
				GrupoDAO grupoDAO = new GrupoDAO();
				grupoCadastro = grupoDAO.buscarPorCodigo(codigo);
			} 	
			
			grupoContatoCadastro = new GrupoContato();
			grupoContatoID = new GrupoContatoID();	
			
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			listaFuncionariosTodosAtivos = funcionarioDAO.listarTodosAtivo();			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados do Contato do Grupo:" + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			GrupoContatoDAO grupoContatoDAO = new GrupoContatoDAO();
			grupoContatoDAO.excluir(grupoContatoCadastro);		
			FacesUtil.adicionarMsgInfo("Contato do Grupo removido com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover Contato do Grupo:" + ex.getMessage());
		}	
	}
	
	public void editar() {
		try {			
			GrupoContatoDAO grupoContatoDAO = new GrupoContatoDAO();								
			grupoContatoDAO.editar(grupoContatoCadastro);						
			FacesUtil.adicionarMsgInfo("Contato de Grupo editado com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar Contato de Grupo." + ex.getMessage());
		}	
	}
	
	//PESQUISA DE TODOS OS FUNCIONARIOS DE UM GRUPO PASSADO COMO PARÂMETRO
	public void carregarPesquisaFuncGrupo() {
		try {	
			if(codigo != null) {
				GrupoContatoDAO grupoContatoDAO = new GrupoContatoDAO();
				listaGrupoContatosFiltrados = grupoContatoDAO.buscarGrupoPorCodigo(codigo);				
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar Contatos de Grupo:" + ex.getMessage());
		}
	}
	
	//PESQUISA DE TODOS OS GRUPO DE UM FUNCIONARIO PASSADO COMO PARÂMETRO
	public void carregarPesquisaGruposFunc() {
		
		//SETA FUNCIONÁRIO QUE ESTÁ LOGADO
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		Funcionario funcionario = funcionarioDAO.buscarPorCodigo(autenticacaoBean.getFuncionarioLogado().getCodigo());				
		fun_codigo = funcionario.getCodigo().intValue();
		
		try {		
			if (fun_codigo != null) {
				GrupoContatoDAO grupoContatoDAO = new GrupoContatoDAO();		
				listaGrupoFunc = grupoContatoDAO.listarGruposFunc(fun_codigo);
			}					
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar Contatos de Grupo:" + ex.getMessage());
		}
	}
	
	//MÉTODO PARA EXCLUIR PARTICIPANTES DO GRUPO
    public void excluirRegistrosSelecionados() throws IOException {   	  		 	
    	// VERIFICA SE HÁ REGISTROS SELECIONADOS
        if (grupoContatoSelecionados != null) {	
        	GrupoContatoDAO grupoContatoDAO = new GrupoContatoDAO();      	
        	for (GrupoContato grupoContato : grupoContatoSelecionados) {	
        		// Lógica de processamento para cada registro selecionado
	            MensagemContatoDAO mensagemContatoDAO = new MensagemContatoDAO();			
	            mensagemContatoDAO.excluiMensagemContatoGrupo(grupoContato.getGrupoContatoID().getFuncionario().getCodigo(), grupoContato.getGrupoContatoID().getGrupo().getCodigo());	            	            
	        	grupoContatoDAO.excluir(grupoContato);  	        	        	
            }      	
        	FacesUtil.adicionarMsgInfo("EXCLUSÃO REALIZADA COM SUCESSO");
        } else {
        	FacesUtil.adicionarMsgInfo("NENHUM REGISTRO SELECIONADO");
        }	
    }
    
    //EXIBE A QUANTIDADE DE MENSAGENS NÃO LIDAS DOS GRUPOS
  	public void carregarMensagensNaoLidas() {
  		try {	 			
  			GrupoContatoDAO grupoContatoDAO = new GrupoContatoDAO();
  			this.totalNaoLidas = grupoContatoDAO.somaMensagensNaoLida(autenticacaoBean.getFuncionarioLogado().getCodigo());
  			System.out.println("QUANTIDADE NÃO LIDA GRUPO: " + totalNaoLidas);		
  		}catch (Exception ex) {
  			ex.printStackTrace();
  			FacesUtil.adicionarMsgErro("Erro ao obter dados da mensagemContato:" + ex.getMessage());
  		}
  	}
}
