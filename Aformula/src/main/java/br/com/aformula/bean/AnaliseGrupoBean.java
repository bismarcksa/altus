package br.com.aformula.bean;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.aformula.dao.AnaliseGrupoDAO;
import br.com.aformula.domain.AnaliseGrupo;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class AnaliseGrupoBean {

	public AnaliseGrupo analiseGrupoCadastro;
	private List<AnaliseGrupo> listaAnaliseGrupo;
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
			           mediaTotalAnual = BigDecimal.ZERO,
					   maiorFaturamentoAtual = BigDecimal.ZERO,
					   maiorFaturamentoAtualDesempenho = BigDecimal.ZERO,
					   menorFaturamentoAtual = BigDecimal.ZERO,
					   menorFaturamentoAtualDesempenho = BigDecimal.ZERO,
					   melhorDesempenhoAtual = BigDecimal.ZERO,
					   piorDesempenhoAtual = BigDecimal.ZERO,
					   metasBatida = BigDecimal.ZERO,
					   metasNaoBatida = BigDecimal.ZERO,
					   mesesAcimaMedia = BigDecimal.ZERO,
					   quantDesempenhoRuim = BigDecimal.ZERO,
					   diferencaFaturamento = BigDecimal.ZERO,
					   desempenhoMaiorFaturamentoAtual = BigDecimal.ZERO;
	private String maiorFaturamentoAtualMes, 
	               menorFaturamentoAtualMes;
	
	public AnaliseGrupo getAnaliseGrupoCadastro() {
		return analiseGrupoCadastro;
	}

	public void setAnaliseGrupoCadastro(AnaliseGrupo analiseGrupoCadastro) {
		this.analiseGrupoCadastro = analiseGrupoCadastro;
	}

	public List<AnaliseGrupo> getListaAnaliseGrupo() {
		return listaAnaliseGrupo;
	}

	public void setListaAnaliseGrupo(List<AnaliseGrupo> listaAnaliseGrupo) {
		this.listaAnaliseGrupo = listaAnaliseGrupo;
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

	public BigDecimal getMaiorFaturamentoAtual() {
		return maiorFaturamentoAtual;
	}

	public void setMaiorFaturamentoAtual(BigDecimal maiorFaturamentoAtual) {
		this.maiorFaturamentoAtual = maiorFaturamentoAtual;
	}

	public BigDecimal getMenorFaturamentoAtual() {
		return menorFaturamentoAtual;
	}

	public void setMenorFaturamentoAtual(BigDecimal menorFaturamentoAtual) {
		this.menorFaturamentoAtual = menorFaturamentoAtual;
	}

	public BigDecimal getMelhorDesempenhoAtual() {
		return melhorDesempenhoAtual;
	}

	public void setMelhorDesempenhoAtual(BigDecimal melhorDesempenhoAtual) {
		this.melhorDesempenhoAtual = melhorDesempenhoAtual;
	}

	public BigDecimal getPiorDesempenhoAtual() {
		return piorDesempenhoAtual;
	}

	public void setPiorDesempenhoAtual(BigDecimal piorDesempenhoAtual) {
		this.piorDesempenhoAtual = piorDesempenhoAtual;
	}

	public BigDecimal getMetasBatida() {
		return metasBatida;
	}

	public void setMetasBatida(BigDecimal metasBatida) {
		this.metasBatida = metasBatida;
	}

	public BigDecimal getMetasNaoBatida() {
		return metasNaoBatida;
	}

	public void setMetasNaoBatida(BigDecimal metasNaoBatida) {
		this.metasNaoBatida = metasNaoBatida;
	}

	public BigDecimal getMesesAcimaMedia() {
		return mesesAcimaMedia;
	}

	public void setMesesAcimaMedia(BigDecimal mesesAcimaMedia) {
		this.mesesAcimaMedia = mesesAcimaMedia;
	}

	public BigDecimal getQuantDesempenhoRuim() {
		return quantDesempenhoRuim;
	}

	public void setQuantDesempenhoRuim(BigDecimal quantDesempenhoRuim) {
		this.quantDesempenhoRuim = quantDesempenhoRuim;
	}

	public BigDecimal getDiferencaFaturamento() {
		return diferencaFaturamento;
	}

	public void setDiferencaFaturamento(BigDecimal diferencaFaturamento) {
		this.diferencaFaturamento = diferencaFaturamento;
	}

	public String getMaiorFaturamentoAtualMes() {
		return maiorFaturamentoAtualMes;
	}

	public void setMaiorFaturamentoAtualMes(String maiorFaturamentoAtualMes) {
		this.maiorFaturamentoAtualMes = maiorFaturamentoAtualMes;
	}

	public String getMenorFaturamentoAtualMes() {
		return menorFaturamentoAtualMes;
	}

	public void setMenorFaturamentoAtualMes(String menorFaturamentoAtualMes) {
		this.menorFaturamentoAtualMes = menorFaturamentoAtualMes;
	}
	
	public BigDecimal getDesempenhoMaiorFaturamentoAtual() {
		return desempenhoMaiorFaturamentoAtual;
	}

	public void setDesempenhoMaiorFaturamentoAtual(BigDecimal desempenhoMaiorFaturamentoAtual) {
		this.desempenhoMaiorFaturamentoAtual = desempenhoMaiorFaturamentoAtual;
	}

	public BigDecimal getMaiorFaturamentoAtualDesempenho() {
		return maiorFaturamentoAtualDesempenho;
	}

	public void setMaiorFaturamentoAtualDesempenho(BigDecimal maiorFaturamentoAtualDesempenho) {
		this.maiorFaturamentoAtualDesempenho = maiorFaturamentoAtualDesempenho;
	}

	public BigDecimal getMenorFaturamentoAtualDesempenho() {
		return menorFaturamentoAtualDesempenho;
	}

	public void setMenorFaturamentoAtualDesempenho(BigDecimal menorFaturamentoAtualDesempenho) {
		this.menorFaturamentoAtualDesempenho = menorFaturamentoAtualDesempenho;
	}

	public void novo() {
		analiseGrupoCadastro = new AnaliseGrupo();
	}
	
	public void salvar() {
		try {
			AnaliseGrupoDAO analiseGrupoDAO = new AnaliseGrupoDAO();						
			analiseGrupoDAO.salvar(analiseGrupoCadastro);			
			analiseGrupoCadastro = new AnaliseGrupo();
//			novo();			
			FacesUtil.adicionarMsgInfo("Análise salva com sucesso.");
			FacesContext.getCurrentInstance().getExternalContext().redirect("gestaoFechamento.xhtml");
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
		BigDecimal vetorAnoAnterior[] = new BigDecimal[12];;
		BigDecimal vetorAnoUltimo[] = new BigDecimal[12];
		
		try {
			AnaliseGrupoDAO analiseGrupoDAO = new AnaliseGrupoDAO();
			listaAnaliseGrupo = analiseGrupoDAO.listar();

			List<AnaliseGrupo> objetos = listaAnaliseGrupo; 					
			
			for (AnaliseGrupo o : objetos) {
				AnaliseGrupo aux = o;
				
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
				
				indice +=1;
				
				//PEGA OS DADOS DO PENÚLTIMO ANO
				if (indice == objetos.size() - 1) {						
					
					vetorAnoAnterior[0] = aux.getJaneiro();
					vetorAnoAnterior[1] = aux.getFevereiro();
					vetorAnoAnterior[2] = aux.getMarco();
					vetorAnoAnterior[3] = aux.getAbril();
					vetorAnoAnterior[4] = aux.getMaio();
					vetorAnoAnterior[5] = aux.getJunho();
					vetorAnoAnterior[6] = aux.getJulho();
					vetorAnoAnterior[7] = aux.getAgosto();
					vetorAnoAnterior[8] = aux.getSetembro();
					vetorAnoAnterior[9] = aux.getOutubro();
					vetorAnoAnterior[10] = aux.getNovembro();
					vetorAnoAnterior[11] = aux.getDezembro();
				
				}
				
				//PEGA OS DADOS DO ÚLTIMO ANO
				if (indice == objetos.size()) {
					 				
					vetorAnoUltimo[0] = aux.getJaneiro();
					vetorAnoUltimo[1] = aux.getFevereiro();
					vetorAnoUltimo[2] = aux.getMarco();
					vetorAnoUltimo[3] = aux.getAbril();
					vetorAnoUltimo[4] = aux.getMaio();
					vetorAnoUltimo[5] = aux.getJunho();
					vetorAnoUltimo[6] = aux.getJulho();
					vetorAnoUltimo[7] = aux.getAgosto();
					vetorAnoUltimo[8] = aux.getSetembro();
					vetorAnoUltimo[9] = aux.getOutubro();
					vetorAnoUltimo[10] = aux.getNovembro();
					vetorAnoUltimo[11] = aux.getDezembro();
					
					for (int i = 0; i < vetorAnoUltimo.length; i++) {
					    //VERIFICA SE O VETOR É MAIOR QUE O VALOR ATUAL DO FATURAMENTO
						if (vetorAnoUltimo[i].compareTo(maiorFaturamentoAtual) == 1) {	
							maiorFaturamentoAtual = vetorAnoUltimo[i];					    	
							maiorFaturamentoAtualDesempenho = (((vetorAnoUltimo[i].divide(vetorAnoAnterior[i],MathContext.DECIMAL128)).multiply(BigDecimal.valueOf(100))).subtract(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
		
					    	switch (i) {
					          case 0:  
					        	  maiorFaturamentoAtualMes = "JANEIRO";
					              break;
					          case 1:
					        	  maiorFaturamentoAtualMes = "FEVEREIRO";
					              break;
					          case 2:  
					        	  maiorFaturamentoAtualMes = "MARÇO";
					              break;
					          case 3:  
					        	  maiorFaturamentoAtualMes = "ABRIL";
					              break;
					          case 4:  
					        	  maiorFaturamentoAtualMes = "MAIO";
					              break;
					          case 5:  
					        	  maiorFaturamentoAtualMes = "JUNHO";
					              break;
					          case 6:
					        	  maiorFaturamentoAtualMes = "JULHO";  
					              break;
					          case 7:  
					        	  maiorFaturamentoAtualMes = "AGOSTO";
					              break;
					          case 8:  
					        	  maiorFaturamentoAtualMes = "SETEMBRO";
					              break;
					          case 9: 
					        	  maiorFaturamentoAtualMes = "OUTUBRO";
					              break;
					          case 10: 
					        	  maiorFaturamentoAtualMes = "NOVEMBRO";
					              break;
					          case 11: 
					        	  maiorFaturamentoAtualMes = "DEZEMBRO";
					              break;
							}
					    	
					    }
						
						//ATRIBUI AO PRIMERIO MÊS O MENOR VALOR, PARA DEPOIS COMPARAR
						if (i == 0) {
							menorFaturamentoAtual = vetorAnoUltimo[0];
							System.out.println("ENTROU NO PRIMEIRO MES...");
							System.out.println("MENOR VALOR: " + menorFaturamentoAtual);
							
							
						}else {					
							//VERIFICA SE O VETOR É MENOR QUE O VALOR ATUAL DO FATURAMENTO
							if ((vetorAnoUltimo[i].compareTo(menorFaturamentoAtual) == -1) && (vetorAnoUltimo[i].compareTo(BigDecimal.ZERO) == 1)) {					    								    						    	
						    	menorFaturamentoAtual = vetorAnoUltimo[i];
						    	System.out.println("MENOR VALOR AGORA É: " + menorFaturamentoAtual);
						    	System.out.println("VETOR É: " + i);
		
							    switch (i) {
							    	case 0:  
							    		menorFaturamentoAtualMes = "JANEIRO";
							            break;
							        case 1:
							        	menorFaturamentoAtualMes = "FEVEREIRO";
							            break;
							        case 2:  
							        	menorFaturamentoAtualMes = "MARÇO";
							            break;
							        case 3:  
							        	menorFaturamentoAtualMes = "ABRIL";
							            break;
							        case 4:  
							        	menorFaturamentoAtualMes = "MAIO";
							            break;
							        case 5:  
							        	menorFaturamentoAtualMes = "JUNHO";
							            break;
							        case 6:
							        	menorFaturamentoAtualMes = "JULHO";  
							            break;
							        case 7:  
							        	menorFaturamentoAtualMes = "AGOSTO";
							            break;
							        case 8:  
							        	menorFaturamentoAtualMes = "SETEMBRO";
							            break;
							        case 9: 
							        	menorFaturamentoAtualMes = "OUTUBRO";
							            break;
							        case 10: 
							        	menorFaturamentoAtualMes = "NOVEMBRO";
							            break;
							        case 11: 
							        	menorFaturamentoAtualMes = "DEZEMBRO";
							            break;
								}
							}
						}
						
						
						
					}
				}
					
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
			FacesUtil.adicionarMsgErro("Erro ao listar Análise:" + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {
			if (codigo != null) {
				AnaliseGrupoDAO analiseGrupoDAO = new AnaliseGrupoDAO();
				analiseGrupoCadastro = analiseGrupoDAO.buscarPorCodigo(codigo);
			}else {
				analiseGrupoCadastro = new AnaliseGrupo();
			} 
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados:" + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {			
			AnaliseGrupoDAO analiseGrupoDAO = new AnaliseGrupoDAO();
			analiseGrupoDAO.excluir(analiseGrupoCadastro);			
			FacesUtil.adicionarMsgInfo("Análise removida com sucesso.");
			FacesContext.getCurrentInstance().getExternalContext().redirect("gestaoFechamento.xhtml");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover Análise:" + ex.getMessage());
		}		
	}
	
	public void editar() {
		try {
			AnaliseGrupoDAO analiseGrupoDAO = new AnaliseGrupoDAO();		
			analiseGrupoDAO.editar(analiseGrupoCadastro);
			FacesUtil.adicionarMsgInfo("Análise editada com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar Análise:" + ex.getMessage());
		}	
	}		
}
