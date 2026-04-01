package br.com.aformula.bean;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.model.file.UploadedFile;
import br.com.aformula.dao.FilialDAO;
import br.com.aformula.dao.FuncionarioDAO;
import br.com.aformula.domain.Filial;
import br.com.aformula.domain.Funcionario;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class FuncionarioBean implements Serializable {
    private static final long serialVersionUID = 1L;

	public Funcionario funcionarioCadastro;
	private List<Funcionario> listaFuncionarios;
	private List<Funcionario> listaFuncionariosFiltrados;
	private String acao;
	private Long codigo;
	
	private String fotoAtual;
	private String caminhoDestino;
	
	private UploadedFile arquivoImagem;
	private List<Filial> listaFiliais;
	
	public Funcionario getFuncionarioCadastro() {
		return funcionarioCadastro;
	}

	public void setFuncionarioCadastro(Funcionario funcionarioCadastro) {
		this.funcionarioCadastro = funcionarioCadastro;
	}

	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public List<Funcionario> getListaFuncionariosFiltrados() {
		return listaFuncionariosFiltrados;
	}

	public void setListaFuncionariosFiltrados(List<Funcionario> listaFuncionariosFiltrados) {
		this.listaFuncionariosFiltrados = listaFuncionariosFiltrados;
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

	public List<Filial> getListaFiliais() {
		return listaFiliais;
	}

	public void setListaFiliais(List<Filial> listaFiliais) {
		this.listaFiliais = listaFiliais;
	}

	public UploadedFile getArquivoImagem() {
		return arquivoImagem;
	}

	public void setArquivoImagem(UploadedFile arquivoImagem) {
		this.arquivoImagem = arquivoImagem;
	}

	public void novo() {
		funcionarioCadastro = new Funcionario();
	}
	
	public void salvar() {
		try {
			// Verificar se a foto foi carregada
//	    	System.out.println("CHEGA AQUI IMAGEM: " + arquivoImagem);
	    	if (arquivoImagem != null && arquivoImagem.getSize() > 0) {
	            uploadFoto(); // Chama o método de upload para garantir que o arquivo seja salvo
	        }
			
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarioCadastro.setSenha(DigestUtils.md5Hex(funcionarioCadastro.getSenha()));						

			funcionarioDAO.salvar(funcionarioCadastro);	
			
			if (funcionarioCadastro.getFuncao().equals("ENTREGADOR")) {
				
				EntregadorBean entregadorBean = new EntregadorBean();			
				entregadorBean.novo();
				entregadorBean.entregadorCadastro.setNome(funcionarioCadastro.getNome());
				entregadorBean.entregadorCadastro.setVinculo("CELETISTA");
				entregadorBean.entregadorCadastro.setStatus(true);
				entregadorBean.salvar();			
			}			
			
			funcionarioCadastro = new Funcionario();
			
			FacesUtil.adicionarMsgInfo("Funcionário salvo com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar funcionário:" + ex.getMessage());
		}	
	}
	
	public void carregarPesquisa() {
		try {
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			listaFuncionarios = funcionarioDAO.listar();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar funcionário:" + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {		
			if (codigo != null) {
				FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
				funcionarioCadastro = funcionarioDAO.buscarPorCodigo(codigo);
				
				if(funcionarioCadastro.getFoto() != null) {
					fotoAtual = funcionarioCadastro.getFoto().toString();
				}
			}else {
				funcionarioCadastro = new Funcionario();
			} 
				
			FilialDAO filialDAO = new FilialDAO();
			listaFiliais = filialDAO.listar();				
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados do funcionário:" + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarioDAO.excluir(funcionarioCadastro);
			
			FacesUtil.adicionarMsgInfo("Funcionário removido com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover funcionário:" + ex.getMessage());
		}		
	}
	
	public void editar() {
	    try {	            		    	
	        // Verificar se a foto foi carregada	    
	    	if (arquivoImagem != null && arquivoImagem.getSize() > 0) {
	            uploadFoto(); // Chama o método de upload para garantir que o arquivo seja salvo
	        }
	        
	        // Se a senha não estiver criptografada, criptografa antes de salvar
	        if (funcionarioCadastro.getSenha().length() != 32) {
	            funcionarioCadastro.setSenha(DigestUtils.md5Hex(funcionarioCadastro.getSenha()));
	        }
	        
	        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
	        funcionarioDAO.editar(funcionarioCadastro);  // Salva o cadastro com a foto
	        FacesUtil.adicionarMsgInfo("Funcionário editado com sucesso!");
	        
	        if ((fotoAtual != caminhoDestino) && (caminhoDestino != null) && (!fotoAtual.equals("C:/Aformula_Imagens/usuario.png"))) {
	        	File arquivo = new File(fotoAtual);
	            arquivo.delete();
	        }	               
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        FacesUtil.adicionarMsgErro("Erro ao editar funcionário: " + ex.getMessage());
	    }
	}

	@SuppressWarnings("unused")
	public void primeiroAcesso() {
		try {
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			Funcionario funcionario = new Funcionario();
			funcionario = funcionarioDAO.buscarPorCPFNascimento(funcionarioCadastro.getCpf(),funcionarioCadastro.getData_nascimento());	
			funcionarioDAO.editar(funcionarioCadastro);

			FacesUtil.adicionarMsgInfo("Funcionário criado com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar funcionário:" + ex.getMessage());
		}
	}
	
	public void uploadFoto() {
		try {
			//ESSA VARIAVEL VAI SER CONCATENADA COMO NOME DO ARQUIVO, EVITANDO ARQUIVO COMNOMES IGUAIS
			LocalDateTime agora = LocalDateTime.now();	   
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmm");
	        String agoraFormatada = agora.format(formatter);
			
	        if (arquivoImagem != null && arquivoImagem.getSize() > 0) {
	            // Definir o caminho para onde a imagem será salva
	            caminhoDestino = "C:\\Aformula_Imagens\\" + agoraFormatada + "_" + arquivoImagem.getFileName();

	            // Salvar o arquivo na pasta
	            InputStream input = arquivoImagem.getInputStream();
	            Files.copy(input, Paths.get(caminhoDestino), StandardCopyOption.REPLACE_EXISTING);
	            
	            //ESSA ALTERAÇÃO AQUI É PARA GRAVAR O CAMINHO RELATIVO PARA APLICAÇÃO LER A IMAGEM CORRETAMENTE
	            caminhoDestino = "C:/Aformula_Imagens/" + agoraFormatada + "_" + arquivoImagem.getFileName();
	            // Definir o caminho da imagem no objeto funcionarioCadastro	        
	            funcionarioCadastro.setFoto(caminhoDestino);
	            FacesUtil.adicionarMsgInfo("Foto do funcionário carregada com sucesso!");
	        } else {
	            FacesUtil.adicionarMsgErro("Por favor, selecione um arquivo para fazer o upload.");
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        FacesUtil.adicionarMsgErro("Erro ao fazer upload da foto: " + ex.getMessage());
	    }
    }
}
