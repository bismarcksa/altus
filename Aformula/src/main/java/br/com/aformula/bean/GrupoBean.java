package br.com.aformula.bean;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.file.UploadedFile;
import br.com.aformula.dao.GrupoDAO;
import br.com.aformula.domain.Grupo;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class GrupoBean {

	public Grupo grupoCadastro;
	private List<Grupo> listaGrupos;
	private List<Grupo> listaGruposFiltrados;
	private String acao;
	private Integer codigo;	
	
	private String fotoAtual;
	private String caminhoDestino;
	
	private UploadedFile arquivoImagem;
	
	public Grupo getGrupoCadastro() {
		return grupoCadastro;
	}

	public void setGrupoCadastro(Grupo grupoCadastro) {
		this.grupoCadastro = grupoCadastro;
	}

	public List<Grupo> getListaGrupos() {
		return listaGrupos;
	}

	public void setListaGrupos(List<Grupo> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	public List<Grupo> getListaGruposFiltrados() {
		return listaGruposFiltrados;
	}

	public void setListaGruposFiltrados(List<Grupo> listaGruposFiltrados) {
		this.listaGruposFiltrados = listaGruposFiltrados;
	}
	
	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public UploadedFile getArquivoImagem() {
		return arquivoImagem;
	}

	public void setArquivoImagem(UploadedFile arquivoImagem) {
		this.arquivoImagem = arquivoImagem;
	}

	public String getFotoAtual() {
		return fotoAtual;
	}

	public void setFotoAtual(String fotoAtual) {
		this.fotoAtual = fotoAtual;
	}

	public String getCaminhoDestino() {
		return caminhoDestino;
	}

	public void setCaminhoDestino(String caminhoDestino) {
		this.caminhoDestino = caminhoDestino;
	}

	public void novo() {
		grupoCadastro = new Grupo();
	}
	
	public void salvar() {
		try {
			// Verificar se a foto foi carregada
	    	if (arquivoImagem != null && arquivoImagem.getSize() > 0) {
	            uploadFoto(); // Chama o método de upload para garantir que o arquivo seja salvo
	        }
	    	
	    	grupoCadastro.setData_cadastro(new Date());
			
			GrupoDAO grupoDAO = new GrupoDAO();				
			grupoDAO.salvar(grupoCadastro);			
			grupoCadastro = new Grupo();
			
			FacesUtil.adicionarMsgInfo("Grupo salvo com sucesso");
			FacesContext.getCurrentInstance().getExternalContext().redirect("grupoPesquisa.xhtml");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar grupo:" + ex.getMessage());
		}	
	}
	
	public void carregarPesquisa() {
		try {
			GrupoDAO grupoDAO = new GrupoDAO();
			listaGrupos = grupoDAO.listar();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar grupo:" + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {	
			if (codigo != null) {
				GrupoDAO grupoDAO = new GrupoDAO();
				grupoCadastro = grupoDAO.buscarPorCodigo(codigo);						
			}else {
				grupoCadastro = new Grupo();
			} 
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados da grupo:" + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			GrupoDAO grupoDAO = new GrupoDAO();
			grupoDAO.excluir(grupoCadastro);
			
			FacesUtil.adicionarMsgInfo("Grupo removido com sucesso");
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("grupoPesquisa.xhtml");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover grupo:" + ex.getMessage());
		}
		
	}
	
	public void editar() {
		try {
			// Verificar se a foto foi carregada
	    	if (arquivoImagem != null && arquivoImagem.getSize() > 0) {
	            uploadFoto(); // Chama o método de upload para garantir que o arquivo seja salvo
	        }
			
			GrupoDAO grupoDAO = new GrupoDAO();						
			grupoDAO.editar(grupoCadastro);

			FacesUtil.adicionarMsgInfo("Grupo editado com sucesso");
			
			if ((fotoAtual != caminhoDestino) && (fotoAtual != null)) {	   
	            File arquivo = new File(fotoAtual);
	            arquivo.delete();
	        }	
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar grupo:" + ex.getMessage());
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
	            
	            //ESSA ALTERAÇÃOA QUI É PARA GRAVAR O CAMINHO RELATIVO PARA APLICAÇÃO LER A IMAGEM CORRETAMENTE
	            caminhoDestino = "C:/Aformula_Imagens/" + agoraFormatada + "_" + arquivoImagem.getFileName();
	            // Definir o caminho da imagem no objeto funcionarioCadastro	        
	            grupoCadastro.setFoto(caminhoDestino);
//	            FacesUtil.adicionarMsgInfo("Foto do grupo carregada com sucesso!");
	        } else {
	            FacesUtil.adicionarMsgErro("Por favor, selecione um arquivo para fazer o upload.");
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        FacesUtil.adicionarMsgErro("Erro ao fazer upload da foto: " + ex.getMessage());
	    }
    }
}
