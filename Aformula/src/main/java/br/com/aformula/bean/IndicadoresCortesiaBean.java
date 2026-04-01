package br.com.aformula.bean;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.aformula.dao.IndicadoresCortesiaDAO;
import br.com.aformula.domain.IndicadoresCortesia;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class IndicadoresCortesiaBean {

	public IndicadoresCortesia indicadoresCortesiaCadastro;
	private List<IndicadoresCortesia> listaIndicadoresCortesia;
	private String acao;
	private Long codigo;

	private BigDecimal mediaJaneiro = BigDecimal.ZERO, 
			   		   mediaFevereiro = BigDecimal.ZERO, 
			           mediaMarco = BigDecimal.ZERO, 
			           mediaAbril = BigDecimal.ZERO, 
			           mediaMaio = BigDecimal.ZERO, 
			           mediaJunho = BigDecimal.ZERO, 
			           mediaJulho = BigDecimal.ZERO,
			           mediaAgosto = BigDecimal.ZERO, 
			           mediaSetembro = BigDecimal.ZERO, 
			           mediaOutubro = BigDecimal.ZERO, 
			           mediaNovembro = BigDecimal.ZERO, 
			           mediaDezembro = BigDecimal.ZERO,
			           mediaTotalAnual = BigDecimal.ZERO;
	
	public IndicadoresCortesia getIndicadoresCortesiaCadastro() {
		return indicadoresCortesiaCadastro;
	}

	public void setIndicadoresCortesiaCadastro(IndicadoresCortesia indicadoresCortesiaCadastro) {
		this.indicadoresCortesiaCadastro = indicadoresCortesiaCadastro;
	}

	public List<IndicadoresCortesia> getListaIndicadoresCortesia() {
		return listaIndicadoresCortesia;
	}

	public void setListaIndicadoresCortesia(List<IndicadoresCortesia> listaIndicadoresCortesia) {
		this.listaIndicadoresCortesia = listaIndicadoresCortesia;
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

	public BigDecimal getMediaJaneiro() {
		return mediaJaneiro;
	}

	public void setMediaJaneiro(BigDecimal mediaJaneiro) {
		this.mediaJaneiro = mediaJaneiro;
	}
	
	public BigDecimal getMediaFevereiro() {
		return mediaFevereiro;
	}

	public void setMediaFevereiro(BigDecimal mediaFevereiro) {
		this.mediaFevereiro = mediaFevereiro;
	}

	public BigDecimal getMediaMarco() {
		return mediaMarco;
	}

	public void setMediaMarco(BigDecimal mediaMarco) {
		this.mediaMarco = mediaMarco;
	}

	public BigDecimal getMediaAbril() {
		return mediaAbril;
	}

	public void setMediaAbril(BigDecimal mediaAbril) {
		this.mediaAbril = mediaAbril;
	}

	public BigDecimal getMediaMaio() {
		return mediaMaio;
	}

	public void setMediaMaio(BigDecimal mediaMaio) {
		this.mediaMaio = mediaMaio;
	}

	public BigDecimal getMediaJunho() {
		return mediaJunho;
	}

	public void setMediaJunho(BigDecimal mediaJunho) {
		this.mediaJunho = mediaJunho;
	}

	public BigDecimal getMediaJulho() {
		return mediaJulho;
	}

	public void setMediaJulho(BigDecimal mediaJulho) {
		this.mediaJulho = mediaJulho;
	}

	public BigDecimal getMediaAgosto() {
		return mediaAgosto;
	}

	public void setMediaAgosto(BigDecimal mediaAgosto) {
		this.mediaAgosto = mediaAgosto;
	}

	public BigDecimal getMediaSetembro() {
		return mediaSetembro;
	}

	public void setMediaSetembro(BigDecimal mediaSetembro) {
		this.mediaSetembro = mediaSetembro;
	}

	public BigDecimal getMediaOutubro() {
		return mediaOutubro;
	}

	public void setMediaOutubro(BigDecimal mediaOutubro) {
		this.mediaOutubro = mediaOutubro;
	}

	public BigDecimal getMediaNovembro() {
		return mediaNovembro;
	}

	public void setMediaNovembro(BigDecimal mediaNovembro) {
		this.mediaNovembro = mediaNovembro;
	}

	public BigDecimal getMediaDezembro() {
		return mediaDezembro;
	}

	public void setMediaDezembro(BigDecimal mediaDezembro) {
		this.mediaDezembro = mediaDezembro;
	}

	public BigDecimal getMediaTotalAnual() {
		return mediaTotalAnual;
	}

	public void setMediaTotalAnual(BigDecimal mediaTotalAnual) {
		this.mediaTotalAnual = mediaTotalAnual;
	}

	public void novo() {
		indicadoresCortesiaCadastro = new IndicadoresCortesia();
	}
	
	public void salvar() {
		try {
			IndicadoresCortesiaDAO indicadoresCortesiaDAO = new IndicadoresCortesiaDAO();						
			indicadoresCortesiaDAO.salvar(indicadoresCortesiaCadastro);			
			indicadoresCortesiaCadastro = new IndicadoresCortesia();
//			novo();			
			FacesUtil.adicionarMsgInfo("Cortesia salva com sucesso.");
			FacesContext.getCurrentInstance().getExternalContext().redirect("gestaoIndicadores.xhtml");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar Cortesia:" + ex.getMessage());
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
			IndicadoresCortesiaDAO indicadoresCortesiaDAO = new IndicadoresCortesiaDAO();
			listaIndicadoresCortesia = indicadoresCortesiaDAO.listar();

			List<IndicadoresCortesia> objetos = listaIndicadoresCortesia; 		
			
			for (IndicadoresCortesia o : objetos) {
				IndicadoresCortesia aux = o;
				
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
				
				//CALCULO DE MÉDIA DOS MESES
				mediaJaneiro = mediaJaneiro.add(aux.getJaneiro());				
				mediaFevereiro = mediaFevereiro.add(aux.getFevereiro());
				mediaMarco = mediaMarco.add(aux.getMarco());
				mediaAbril = mediaAbril.add(aux.getAbril());
				mediaMaio = mediaMaio.add(aux.getMaio());
				mediaJunho = mediaJunho.add(aux.getJunho());
				mediaJulho = mediaJulho.add(aux.getJulho());
				mediaAgosto = mediaAgosto.add(aux.getAgosto());
				mediaSetembro = mediaSetembro.add(aux.getSetembro());
				mediaOutubro = mediaOutubro.add(aux.getOutubro());
				mediaNovembro = mediaNovembro.add(aux.getNovembro());
				mediaDezembro = mediaDezembro.add(aux.getDezembro());
			}
			
			if (indice > 0) {
				mediaJaneiro = mediaJaneiro.divide(BigDecimal.valueOf(indice), 2, RoundingMode.HALF_UP);
				mediaFevereiro = mediaFevereiro.divide(BigDecimal.valueOf(indice), 2, RoundingMode.HALF_UP);
				mediaMarco = mediaMarco.divide(BigDecimal.valueOf(indice), 2, RoundingMode.HALF_UP);
				mediaAbril = mediaAbril.divide(BigDecimal.valueOf(indice), 2, RoundingMode.HALF_UP);
				mediaMaio = mediaMaio.divide(BigDecimal.valueOf(indice), 2, RoundingMode.HALF_UP);
				mediaJunho = mediaJunho.divide(BigDecimal.valueOf(indice), 2, RoundingMode.HALF_UP);
				mediaJulho = mediaJulho.divide(BigDecimal.valueOf(indice), 2, RoundingMode.HALF_UP);
				mediaAgosto = mediaAgosto.divide(BigDecimal.valueOf(indice), 2, RoundingMode.HALF_UP);
				mediaSetembro = mediaSetembro.divide(BigDecimal.valueOf(indice), 2, RoundingMode.HALF_UP);
				mediaOutubro = mediaOutubro.divide(BigDecimal.valueOf(indice), 2, RoundingMode.HALF_UP);
				mediaNovembro = mediaNovembro.divide(BigDecimal.valueOf(indice), 2, RoundingMode.HALF_UP);
				mediaDezembro = mediaDezembro.divide(BigDecimal.valueOf(indice), 2, RoundingMode.HALF_UP);
			}
			//INVERTE A ORDEM DOS OBJETOS
			Collections.reverse(objetos);
			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar Cortesia:" + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {
			if (codigo != null) {
				IndicadoresCortesiaDAO indicadoresCortesiaDAO = new IndicadoresCortesiaDAO();
				indicadoresCortesiaCadastro = indicadoresCortesiaDAO.buscarPorCodigo(codigo);
			}else {
				indicadoresCortesiaCadastro = new IndicadoresCortesia();
			} 
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados:" + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {			
			IndicadoresCortesiaDAO indicadoresCortesiaDAO = new IndicadoresCortesiaDAO();
			indicadoresCortesiaDAO.excluir(indicadoresCortesiaCadastro);			
			FacesUtil.adicionarMsgInfo("Cortesia removida com sucesso.");
			FacesContext.getCurrentInstance().getExternalContext().redirect("gestaoIndicadores.xhtml");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover Cortesia:" + ex.getMessage());
		}		
	}
	
	public void editar() {
		try {
			IndicadoresCortesiaDAO indicadoresCortesiaDAO = new IndicadoresCortesiaDAO();		
			indicadoresCortesiaDAO.editar(indicadoresCortesiaCadastro);
			FacesUtil.adicionarMsgInfo("Cortesia editada com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar Cortesia:" + ex.getMessage());
		}	
	}		
}
