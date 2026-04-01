package br.com.aformula.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.model.file.UploadedFile;
import br.com.aformula.dao.FuncionarioDAO;
import br.com.aformula.dao.GrupoContatoDAO;
import br.com.aformula.dao.MensagemContatoDAO;
import br.com.aformula.dao.MensagemDAO;
import br.com.aformula.domain.Mensagem;
import br.com.aformula.domain.MensagemContato;
import br.com.aformula.domain.Funcionario;
import br.com.aformula.domain.Grupo;
import br.com.aformula.domain.GrupoContato;
import br.com.aformula.util.FacesUtil;
import br.com.aformula.util.PrivateChatEndpoint;

@ManagedBean
@ViewScoped
public class MensagemBean implements Serializable{
	private static final long serialVersionUID = 1L;

	public Mensagem mensagemCadastro;
	private List<String> listaContatos;// RECEBE A LISTA DE FUNCIONÁRIOS
	private List<Mensagem> listaMensagensFiltrados;
	private String acao;
	private Long codigo;
	public Funcionario contatoSelecionado;
	public Grupo grupoSelecionado;
	public MensagemContato mensagemContatoCadastro;
	public GrupoContato grupoContatoCadastro;
	private UploadedFile arquivoAnexo;
	private String caminhoDestino;
	private List<GrupoContato> listaGrupoContatos;
	
	 private boolean exibirContato = true; //EXIBE A BARRA DE CONTATO NO LADO DIREITO
	
	@ManagedProperty(value = "#{autenticacaoBean}")
	private AutenticacaoBean autenticacaoBean;			
	
	public Mensagem getMensagemCadastro() {
		return mensagemCadastro;
	}

	public void setMensagemCadastro(Mensagem mensagemCadastro) {
		this.mensagemCadastro = mensagemCadastro;
	}

	public List<String> getListaContatos() {
		return listaContatos;
	}

	public void setListaContatos(List<String> listaContatos) {
		this.listaContatos = listaContatos;
	}

	public List<Mensagem> getListaMensagensFiltrados() {
		return listaMensagensFiltrados;
	}

	public void setListaMensagensFiltrados(List<Mensagem> listaMensagensFiltrados) {
		this.listaMensagensFiltrados = listaMensagensFiltrados;
	}

	public AutenticacaoBean getAutenticacaoBean() {
		return autenticacaoBean;
	}

	public void setAutenticacaoBean(AutenticacaoBean autenticacaoBean) {
		this.autenticacaoBean = autenticacaoBean;
	}

	public Funcionario getContatoSelecionado() {
		return contatoSelecionado;
	}

	public void setContatoSelecionado(Funcionario contatoSelecionado) {
		this.contatoSelecionado = contatoSelecionado;
	}

	public Grupo getGrupoSelecionado() {
		return grupoSelecionado;
	}

	public void setGrupoSelecionado(Grupo grupoSelecionado) {
		this.grupoSelecionado = grupoSelecionado;
	}

	public MensagemContato getMensagemContatoCadastro() {
		return mensagemContatoCadastro;
	}

	public void setMensagemContatoCadastro(MensagemContato mensagemContatoCadastro) {
		this.mensagemContatoCadastro = mensagemContatoCadastro;
	}

	public GrupoContato getGrupoContatoCadastro() {
		return grupoContatoCadastro;
	}

	public void setGrupoContatoCadastro(GrupoContato grupoContatoCadastro) {
		this.grupoContatoCadastro = grupoContatoCadastro;
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

	public UploadedFile getArquivoAnexo() {
		return arquivoAnexo;
	}

	public void setArquivoAnexo(UploadedFile arquivoAnexo) {
		this.arquivoAnexo = arquivoAnexo;
	}

	public String getCaminhoDestino() {
		return caminhoDestino;
	}

	public void setCaminhoDestino(String caminhoDestino) {
		this.caminhoDestino = caminhoDestino;
	}
	
	public boolean isExibirContato() {
		return exibirContato;
	}

	public void setExibirContato(boolean exibirContato) {
		this.exibirContato = exibirContato;
	}
	
	public List<GrupoContato> getListaGrupoContatos() {
		return listaGrupoContatos;
	}

	public void setListaGrupoContatos(List<GrupoContato> listaGrupoContatos) {
		this.listaGrupoContatos = listaGrupoContatos;
	}

	public void novo() {
		mensagemCadastro = new Mensagem();
	}
	
	public void salvar() {
		if ((contatoSelecionado != null) || (grupoSelecionado != null)) {
			try {
				//SETA FUNCIONÁRIO QUE FEZ O REGISTRO
				FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
				Funcionario funcionario = funcionarioDAO.buscarPorCodigo(autenticacaoBean.getFuncionarioLogado().getCodigo());				
				mensagemCadastro.setRemetente(funcionario);
				
				//VERIFICA SE O DESTINO É GRUPO OU CONTATO
				if(exibirContato)
					mensagemCadastro.setDestinatario(contatoSelecionado);
				else
					mensagemCadastro.setGrupo(grupoSelecionado);
				
				//DATA DE ENVIO COM SENDO AGORA
				mensagemCadastro.setData_envio(new Date());
				
				// Verificar se o arquivo foi carregado
		        if (arquivoAnexo != null && arquivoAnexo.getSize() > 0) {
		            uploadArquivo(); // Chama o método de upload para garantir que o arquivo seja salvo
		        }
		        
		        //CHECA SE NÃO TEM ANEXO E SE MENSAGEM É VAZIA
		        if ((mensagemCadastro.getAnexo() == null) && (mensagemCadastro.getMensagem().isEmpty())) {
					FacesUtil.adicionarMsgAlerta("Mensagem deve ser preenchida.");
					return;
		        }			
		        
				MensagemDAO mensagemDAO = new MensagemDAO();		
				mensagemDAO.salvar(mensagemCadastro);	
				
//				System.out.println("EXIBE CONTATO: " + exibirContato);
//				FacesUtil.adicionarMsgAlerta("EXIBE CONTATO: " + exibirContato);
				
//				PrivateChatEndpoint.enviarMensagemPrivada(contatoSelecionado.getLogin(), funcionario.getLogin(), mensagemCadastro.getMensagem());
				
				if (exibirContato) {
				    // envia mensagem por websocket se destinatário estiver online
//					System.out.println("VAMOS VER: " + contatoSelecionado.getLogin() + "  --  " + contatoSelecionado.getNome());
					
					UsuarioOnlineBean usuariosOnline = new UsuarioOnlineBean();	
					System.out.println("ONLINE: " + usuariosOnline.getUsuariosOnline());
					
				    if (new UsuarioOnlineBean().getUsuariosOnline().contains(funcionario.getLogin())) {
//				    	System.out.println("ENTROOOOOOOU: " + contatoSelecionado.getLogin());
//				    	System.out.println("EITA: " + funcionario.getLogin());
//				    	System.out.println("OXEEEE: " + mensagemCadastro.getMensagem());
				    	
				        PrivateChatEndpoint.enviarMensagemPrivada(contatoSelecionado.getLogin(), funcionario.getLogin(), mensagemCadastro.getMensagem());
				    }else {
				    	System.out.println("USUARO NAO LOGADO");
				    }
				}
								
				//VERIFICA SE O DESTINO É GRUPO OU CONTATO
				if(exibirContato) {
					filtrarMensagens(contatoSelecionado);
//					System.out.println("CONTATO: " + contatoSelecionado);
					//ALTERA O NUMERO DE MENSAGENS NÃO LIDAS
					MensagemContatoDAO mensagemContatoDAO = new MensagemContatoDAO();
					//AQUI EU TROQUEI A ORDEM DOS PARAMETROS PRA GRAVAR NA POSIÇÃO CERTA NA TABELA DE MENSAGEM CONTATO
					mensagemContatoCadastro = mensagemContatoDAO.buscarContatoPorCodigo(contatoSelecionado.getCodigo(), funcionario.getCodigo());
					Integer mensagem_nao_lida = mensagemContatoCadastro.getMensagemNaoLida() + 1;			
					mensagemContatoCadastro.setMensagemNaoLida(mensagem_nao_lida);				
					MensagemContatoDAO mensagemContato2DAO = new MensagemContatoDAO();				
					mensagemContato2DAO.editar(mensagemContatoCadastro);
				} else {	
					filtrarMensagensGrupo(grupoSelecionado);					
					
					//ALTERA O NUMERO DE MENSAGENS NÃO LIDAS
					MensagemContatoDAO mensagemContatoDAO = new MensagemContatoDAO();									
									
					GrupoContatoDAO grupoContatoDAO = new GrupoContatoDAO();
					
					listaGrupoContatos = grupoContatoDAO.buscarGrupoPorCodigo(grupoSelecionado.getCodigo());
					for (GrupoContato grupoContato : listaGrupoContatos) { 	
				
						//AQUI NÃO PERMITE QUE FAÇA O CONTATO DE VOCE MESMO
						if(grupoContato.getGrupoContatoID().getFuncionario().equals(funcionario)) {
//							System.out.println("ENTROU");
							continue;
						}						
						
						//AQUI EU TROQUEI A ORDEM DOS PARAMETROS PRA GRAVAR NA POSIÇÃO CERTA NA TABELA DE MENSAGEM CONTATO
						mensagemContatoCadastro = mensagemContatoDAO.buscarContatoPorCodigoGrupo(grupoContato.getGrupoContatoID().getFuncionario().getCodigo(), funcionario.getCodigo(), grupoSelecionado.getCodigo());
						Integer mensagem_nao_lida = mensagemContatoCadastro.getMensagemNaoLida() + 1;	
						mensagemContatoCadastro.setMensagemNaoLida(mensagem_nao_lida);						
						MensagemContatoDAO mensagemContato2DAO = new MensagemContatoDAO();				
						mensagemContato2DAO.editar(mensagemContatoCadastro);
		
						
						grupoContatoCadastro = grupoContatoDAO.buscarContatoPorCodigoGrupo(grupoContato.getGrupoContatoID().getFuncionario().getCodigo(), grupoSelecionado.getCodigo());								
						Integer mensagem_nao_lida_grupo = grupoContatoCadastro.getMensagemNaoLida() + 1;						
						grupoContatoCadastro.setMensagemNaoLida(mensagem_nao_lida_grupo);
						GrupoContatoDAO grupoContato2DAO = new GrupoContatoDAO();							 						
						grupoContato2DAO.editar(grupoContatoCadastro);	
					}		
				}
								
				mensagemCadastro = new Mensagem();
	
//				FacesUtil.adicionarMsgInfo("Mensagem salva com sucesso");
			}catch (Exception ex) {
				ex.printStackTrace();
				FacesUtil.adicionarMsgErro("Erro ao salvar mensagem:" + ex.getMessage());
			}
		 } else {
			 FacesUtil.adicionarMsgAlerta("CONTATO NÃO SELECIONADO");
			 return;
	     }
	}
	
	public void carregarPesquisa() {
		try {
			//SETA FUNCIONÁRIO QUE FEZ O REGISTRO
//			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
//			Funcionario funcionario = funcionarioDAO.buscarPorCodigo(autenticacaoBean.getFuncionarioLogado().getCodigo());				
//			long codigoUsuario = funcionario.getCodigo();
			
//			MensagemDAO mensagemDAO = new MensagemDAO();
//			listaContatos = mensagemDAO.listarContato(codigoUsuario);
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar contatos:" + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {		
			if (codigo != null) {
				MensagemDAO mensagemDAO = new MensagemDAO();
				mensagemCadastro = mensagemDAO.buscarPorCodigo(codigo);
			}else {
				mensagemCadastro = new Mensagem();
			} 			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados da mensagem:" + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			MensagemDAO mensagemDAO = new MensagemDAO();
			mensagemDAO.excluir(mensagemCadastro);
			
			FacesUtil.adicionarMsgInfo("Mensagem removida com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover filial:" + ex.getMessage());
		}		
	}
	
	public void editar() {
		try {
			MensagemDAO mensagemDAO = new MensagemDAO();						
			mensagemDAO.editar(mensagemCadastro);

			FacesUtil.adicionarMsgInfo("Mensagem editado com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar filial:" + ex.getMessage());
		}	
	}	
	
	public List<Mensagem> filtrarMensagens(Funcionario destinatario) {
		try {
			//SETA FUNCIONÁRIO QUE FEZ O REGISTRO
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			Funcionario funcionario = funcionarioDAO.buscarPorCodigo(autenticacaoBean.getFuncionarioLogado().getCodigo());				
			long remetente = funcionario.getCodigo();
			
			if (destinatario != null) {
				contatoSelecionado = destinatario;
			}
			
			//ALTERA O NUMERO DE MENSAGENS NÃO LIDAS
			MensagemContatoDAO mensagemContatoDAO = new MensagemContatoDAO();
			mensagemContatoCadastro = mensagemContatoDAO.buscarContatoPorCodigo(funcionario.getCodigo(), contatoSelecionado.getCodigo());
			Integer mensagem_nao_lida = 0;			
			mensagemContatoCadastro.setMensagemNaoLida(mensagem_nao_lida);				
			MensagemContatoDAO mensagemContato2DAO = new MensagemContatoDAO();				
			mensagemContato2DAO.editar(mensagemContatoCadastro);		
			
			MensagemDAO mensagemDAO = new MensagemDAO();
			listaMensagensFiltrados = mensagemDAO.listarMensagensFiltradas(remetente, destinatario.getCodigo());		
			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar mensagem:" + ex.getMessage());
		}	
		return listaMensagensFiltrados;
	}
	
	public boolean isEnviadaPorUsuario(Mensagem mensagem) {
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		Funcionario usuarioAtual = funcionarioDAO.buscarPorCodigo(autenticacaoBean.getFuncionarioLogado().getCodigo());						
		
		if (mensagem == null || mensagem.getRemetente() == null) {
	        return false;
	    }
	    return mensagem.getRemetente().equals(usuarioAtual); // Comparando remetente com usuário logado
	}
	
	public void uploadArquivo() {			
		try {
			//ESSA VARIAVEL VAI SER CONCATENADA COMO NOME DO ARQUIVO, EVITANDO ARQUIVO COMNOMES IGUAIS
			LocalDateTime agora = LocalDateTime.now();	   
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmm");
	        String agoraFormatada = agora.format(formatter);
			
	        if (arquivoAnexo != null && arquivoAnexo.getSize() > 0) {
	            // Definir o caminho para onde a imagem será salva
	            caminhoDestino = "C:\\Aformula_Anexos\\" + agoraFormatada + "_" + arquivoAnexo.getFileName();
//	            System.out.println("CAMINHO COM NOME DO ARQUIVO GRAVADO: "+ caminhoDestino);
	            // Salvar o arquivo na pasta
	            InputStream input = arquivoAnexo.getInputStream();
	            Files.copy(input, Paths.get(caminhoDestino), StandardCopyOption.REPLACE_EXISTING);
	            
	            //ESSA ALTERAÇÃO AQUI É PARA GRAVAR O CAMINHO RELATIVO PARA APLICAÇÃO LER A IMAGEM CORRETAMENTE
	            caminhoDestino = "C:/Aformula_Anexos/" + agoraFormatada + "_" + arquivoAnexo.getFileName();
	            // Definir o caminho da imagem no objeto funcionarioCadastro	        
	            mensagemCadastro.setAnexo(caminhoDestino);
//	            FacesUtil.adicionarMsgInfo("Anexo carregado com sucesso!");
	        } else {
	            FacesUtil.adicionarMsgErro("Por favor, selecione um arquivo para fazer o upload.");
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        FacesUtil.adicionarMsgErro("Erro ao fazer upload da foto: " + ex.getMessage());
	    }
    }
	
	
	public void baixarAnexo(Mensagem mensagem) {	   
		if (mensagem.getAnexo() != null) {
	        File arquivo = new File(mensagem.getAnexo());
	        if (arquivo.exists()) {
	            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	            externalContext.responseReset();
	            externalContext.setResponseContentType("application/octet-stream");
	            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + arquivo.getName() + "\"");
	            try (InputStream inputStream = new FileInputStream(arquivo);
	                 OutputStream outputStream = externalContext.getResponseOutputStream()) {
	                byte[] buffer = new byte[1024];
	                int bytesRead;
	                while ((bytesRead = inputStream.read(buffer)) != -1) {
	                    outputStream.write(buffer, 0, bytesRead);
	                }
	                FacesContext.getCurrentInstance().responseComplete();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}
	
	public static String extrairNomeArquivo(String caminho) {
        if (caminho != null && !caminho.isEmpty()) {
            return caminho.substring(caminho.lastIndexOf("/") + 1); // Para caminhos com "/" - esse + 11 é para remover o data que é inserir no nome do arquivo
            // return caminho.substring(caminho.lastIndexOf("\\") + 1); // Para caminhos no Windows com "\"
        }
        return null;
    }
	
	public String extrairNomeSemPrefixo(String caminho) {
	    String nomeArquivo = extrairNomeArquivo(caminho); // supondo que esse método já te dá o nome com prefixo
	    if (nomeArquivo != null && nomeArquivo.length() > 11) {
	        return nomeArquivo.substring(11);
	    } else {
	        return nomeArquivo; // caso o nome seja menor que 11 caracteres, devolve como está
	    }
	}
	
	public List<Mensagem> filtrarMensagensGrupo(Grupo grupo) {
		try {
			//SETA FUNCIONÁRIO QUE FEZ O REGISTRO
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			Funcionario funcionario = funcionarioDAO.buscarPorCodigo(autenticacaoBean.getFuncionarioLogado().getCodigo());				
			
			if (grupo != null) {
				grupoSelecionado = grupo;
			}	
			
			//ALTERA O NUMERO DE MENSAGENS NÃO LIDAS
			MensagemContatoDAO mensagemContatoDAO = new MensagemContatoDAO();			
			mensagemContatoDAO.zeraMensamgeVista(funcionario.getCodigo(), grupoSelecionado.getCodigo());
		
			
			GrupoContatoDAO grupoContatoDAO = new GrupoContatoDAO();
			grupoContatoCadastro = grupoContatoDAO.buscarContatoPorCodigoGrupo(funcionario.getCodigo(), grupoSelecionado.getCodigo());								
			Integer mensagem_nao_lida_grupo = 0;						
			grupoContatoCadastro.setMensagemNaoLida(mensagem_nao_lida_grupo);
			GrupoContatoDAO grupoContato2DAO = new GrupoContatoDAO();							 						
			grupoContato2DAO.editar(grupoContatoCadastro);	

			MensagemDAO mensagemDAO = new MensagemDAO();
			listaMensagensFiltrados = mensagemDAO.listarMensagensFiltradasGrupo(grupo.getCodigo());		
			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar mensagem Grupo:" + ex.getMessage());
		}	
		return listaMensagensFiltrados;
	}
	
	//metodo para exibir a barra do contato
	public void selecionarContato() {
	    this.exibirContato = true;
	}
	
	//metodo para exibir a barra do contato
	public void selecionarGrupo() {
	    this.exibirContato = false;
	}
	
	
	public String extrairExtensao(String nomeArquivo) {
	    if (nomeArquivo == null || !nomeArquivo.contains(".")) {
	        return "default"; // ícone padrão
	    }
	    return nomeArquivo.substring(nomeArquivo.lastIndexOf(".") + 1).toLowerCase();
	}
	
}
