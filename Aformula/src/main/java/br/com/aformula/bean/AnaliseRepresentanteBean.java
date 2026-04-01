package br.com.aformula.bean;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import br.com.aformula.dao.AnaliseRepresentanteDAO;
import br.com.aformula.domain.AnaliseRepresentante;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class AnaliseRepresentanteBean {

	public AnaliseRepresentante analiseRepresentanteCadastro;
	private List<AnaliseRepresentante> listaAnaliseRepresentante;
	private String acao;
	private Long codigo;

	public AnaliseRepresentante getAnaliseRepresentanteCadastro() {
		return analiseRepresentanteCadastro;
	}

	public void setAnaliseRepresentanteCadastro(AnaliseRepresentante analiseRepresentanteCadastro) {
		this.analiseRepresentanteCadastro = analiseRepresentanteCadastro;
	}

	public List<AnaliseRepresentante> getListaAnaliseRepresentante() {
		return listaAnaliseRepresentante;
	}

	public void setListaAnaliseRepresentante(List<AnaliseRepresentante> listaAnaliseRepresentante) {
		this.listaAnaliseRepresentante = listaAnaliseRepresentante;
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

	public void novo() {
		analiseRepresentanteCadastro = new AnaliseRepresentante();
	}
	
	public void salvar() {
		try {
			AnaliseRepresentanteDAO analiseRepresentanteDAO = new AnaliseRepresentanteDAO();
						
			analiseRepresentanteDAO.salvar(analiseRepresentanteCadastro);
			
			analiseRepresentanteCadastro = new AnaliseRepresentante();
//			novo();
			FacesUtil.adicionarMsgInfo("Análise salva com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar Análise:" + ex.getMessage());
		}	
	}
	
	
	
	
	
	public void carregarPesquisa() {
		BigDecimal valorJaneiro = BigDecimal.ZERO, 
				   valorFevereiro = BigDecimal.ZERO, 
				   valorMarco = BigDecimal.ZERO, 
				   valorAbril = BigDecimal.ZERO, 
				   valorMaio = BigDecimal.ZERO, 
				   valorJunho = BigDecimal.ZERO, 
				   valorJulho = BigDecimal.ZERO,
				   valorAgosto = BigDecimal.ZERO, 
				   valorSetembro = BigDecimal.ZERO, 
				   valorOutubro = BigDecimal.ZERO, 
				   valorNovembro = BigDecimal.ZERO, 
				   valorDezembro = BigDecimal.ZERO,
				   valorTotalAnual = BigDecimal.ZERO;
		
		Integer indice = 0;
		try {
			AnaliseRepresentanteDAO analiseRepresentanteDAO = new AnaliseRepresentanteDAO();
			listaAnaliseRepresentante = analiseRepresentanteDAO.listar();

			List<AnaliseRepresentante> objetos = listaAnaliseRepresentante; 
			
			
			for (AnaliseRepresentante o : objetos) {
				AnaliseRepresentante aux = o;
				
				aux.setTotalAnual(aux.getJaneiro().add(aux.getFevereiro().add(aux.getMarco().add(aux.getAbril().add(aux.getMaio().add(aux.getJunho().add(aux.getJulho().add(aux.getAgosto().add(aux.getSetembro().add(aux.getOutubro().add(aux.getNovembro().add(aux.getDezembro()))))))))))));
								
				if (indice == 0) {
					aux.setValorJaneiro(valorJaneiro);
					aux.setValorFevereiro(valorFevereiro);
					aux.setValorMarco(valorMarco);
					aux.setValorAbril(valorAbril);
					aux.setValorMaio(valorMaio);
					aux.setValorJunho(valorJunho);
					aux.setValorJulho(valorJulho);
					aux.setValorAgosto(valorAgosto);
					aux.setValorSetembro(valorSetembro);
					aux.setValorOutubro(valorOutubro);
					aux.setValorNovembro(valorNovembro);
					aux.setValorDezembro(valorDezembro);
					aux.setValorTotalAnual(valorTotalAnual);
				}else {
					aux.setValorJaneiro(((aux.getJaneiro().divide(valorJaneiro,MathContext.DECIMAL128)).multiply(BigDecimal.valueOf(100))).subtract(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
					aux.setValorFevereiro(((aux.getFevereiro().divide(valorFevereiro,MathContext.DECIMAL128)).multiply(BigDecimal.valueOf(100))).subtract(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
					aux.setValorMarco(((aux.getMarco().divide(valorMarco,MathContext.DECIMAL128)).multiply(BigDecimal.valueOf(100))).subtract(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
					aux.setValorAbril(((aux.getAbril().divide(valorAbril,MathContext.DECIMAL128)).multiply(BigDecimal.valueOf(100))).subtract(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
					aux.setValorMaio(((aux.getMaio().divide(valorMaio,MathContext.DECIMAL128)).multiply(BigDecimal.valueOf(100))).subtract(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
					aux.setValorJunho(((aux.getJunho().divide(valorJunho,MathContext.DECIMAL128)).multiply(BigDecimal.valueOf(100))).subtract(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
					aux.setValorJulho(((aux.getJulho().divide(valorJulho,MathContext.DECIMAL128)).multiply(BigDecimal.valueOf(100))).subtract(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
					aux.setValorAgosto(((aux.getAgosto().divide(valorAgosto,MathContext.DECIMAL128)).multiply(BigDecimal.valueOf(100))).subtract(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
					aux.setValorSetembro(((aux.getSetembro().divide(valorSetembro,MathContext.DECIMAL128)).multiply(BigDecimal.valueOf(100))).subtract(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
					aux.setValorOutubro(((aux.getOutubro().divide(valorOutubro,MathContext.DECIMAL128)).multiply(BigDecimal.valueOf(100))).subtract(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
					aux.setValorNovembro(((aux.getNovembro().divide(valorNovembro,MathContext.DECIMAL128)).multiply(BigDecimal.valueOf(100))).subtract(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
					aux.setValorDezembro(((aux.getDezembro().divide(valorDezembro,MathContext.DECIMAL128)).multiply(BigDecimal.valueOf(100))).subtract(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
					aux.setValorTotalAnual(((aux.getTotalAnual().divide(valorTotalAnual,MathContext.DECIMAL128)).multiply(BigDecimal.valueOf(100))).subtract(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_EVEN));							
				
				}
				
				
				valorJaneiro = aux.getJaneiro();
				valorFevereiro = aux.getFevereiro(); 
				valorMarco = aux.getMarco();
				valorAbril = aux.getAbril();
				valorMaio = aux.getMaio();
				valorJunho = aux.getJunho();
				valorJulho = aux.getJulho();
				valorAgosto = aux.getAgosto();
				valorSetembro = aux.getSetembro();
				valorOutubro = aux.getOutubro();
				valorNovembro = aux.getNovembro();
				valorDezembro = aux.getDezembro();
				valorTotalAnual = aux.getTotalAnual();
				indice +=1;
			}
			
			//INVERTE A ORDEM DOS OBJETOS
			Collections.reverse(objetos);
			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar Análise:" + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {
			if (codigo != null) {
				AnaliseRepresentanteDAO analiseRepresentanteDAO = new AnaliseRepresentanteDAO();
				analiseRepresentanteCadastro = analiseRepresentanteDAO.buscarPorCodigo(codigo);

			}else {
				analiseRepresentanteCadastro = new AnaliseRepresentante();
			} 
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados:" + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			AnaliseRepresentanteDAO analiseRepresentanteDAO = new AnaliseRepresentanteDAO();
			analiseRepresentanteDAO.excluir(analiseRepresentanteCadastro);

			FacesUtil.adicionarMsgInfo("Análise removida com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover Análise:" + ex.getMessage());
		}
		
	}
	
	public void editar() {
		try {
			AnaliseRepresentanteDAO analiseRepresentanteDAO = new AnaliseRepresentanteDAO();
		
			analiseRepresentanteDAO.editar(analiseRepresentanteCadastro);

			FacesUtil.adicionarMsgInfo("Análise editada com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar Análise:" + ex.getMessage());
		}	
	}
}
