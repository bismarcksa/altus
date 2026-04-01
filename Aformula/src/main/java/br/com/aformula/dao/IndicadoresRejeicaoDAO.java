package br.com.aformula.dao;

import java.util.Collections;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.aformula.domain.IndicadoresRejeicao;
import br.com.aformula.util.HibernateUtil;

public class IndicadoresRejeicaoDAO {
	
	public float mediaLoja67 = 0, mediaLoja68  = 0, mediaLoja70  = 0, mediaGrupo  = 0;
	
	public void salvar(IndicadoresRejeicao indicadoresRejeicao){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(indicadoresRejeicao);
			transacao.commit();
		}catch(RuntimeException ex){
			if (transacao != null){
				transacao.rollback();
			}
			throw ex;
		} finally{
			sessao.close();
		}	
	}
	
	@SuppressWarnings("unchecked")
	public List<IndicadoresRejeicao> listar12Ultimos(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<IndicadoresRejeicao> indicadoresRejeicoes = null;
		
		try{
			Query consulta = sessao.getNamedQuery("IndicadoresRejeicao.listar");
			//RETORNA 12 PRIMEIROS REGISTROS
			consulta.setMaxResults(12);
			indicadoresRejeicoes = consulta.list();
			//INVERT A LISTA
			Collections.reverse(indicadoresRejeicoes);
			
			Integer i;
			
			for (i = 0; i < indicadoresRejeicoes.size(); i++) {
				mediaLoja67 += indicadoresRejeicoes.get(i).getFilial01().floatValue();
				mediaLoja68 += indicadoresRejeicoes.get(i).getFilial02().floatValue();
				mediaLoja70 += indicadoresRejeicoes.get(i).getFilial03().floatValue();
				mediaGrupo += indicadoresRejeicoes.get(i).getGrupo().floatValue();
				
			}
			
			mediaLoja67 = mediaLoja67/indicadoresRejeicoes.size();
			mediaLoja68 = mediaLoja68/indicadoresRejeicoes.size();
			mediaLoja70 = mediaLoja70/indicadoresRejeicoes.size();
			mediaGrupo = mediaGrupo/indicadoresRejeicoes.size();
			
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return indicadoresRejeicoes;
	}
	
	@SuppressWarnings("unchecked")
	public List<IndicadoresRejeicao> listar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<IndicadoresRejeicao> indicadoresRejeicoes = null;
		
		try{
			Query consulta = sessao.getNamedQuery("IndicadoresRejeicao.listarDesc");
			//RETORNA 12 PRIMEIROS REGISTROS
			//consulta.setMaxResults(12);
			indicadoresRejeicoes = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return indicadoresRejeicoes;
	}
	
	public IndicadoresRejeicao buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		IndicadoresRejeicao indicadoresRejeicao = null;
		
		try{
			Query consulta = sessao.getNamedQuery("IndicadoresRejeicao.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			indicadoresRejeicao = (IndicadoresRejeicao) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		
		return indicadoresRejeicao;
	}
	
	public void excluir(IndicadoresRejeicao indicadoresRejeicao){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(indicadoresRejeicao);
			transacao.commit();
		}catch(RuntimeException ex){
			if (transacao != null){
				transacao.rollback();
			}
			throw ex;
		} finally{
			sessao.close();
		}	
	}
	
	public void editar(IndicadoresRejeicao indicadoresRejeicao){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(indicadoresRejeicao);
			transacao.commit();
		}catch(RuntimeException ex){
			if (transacao != null){
				transacao.rollback();
			}
			throw ex;
		} finally{
			sessao.close();
		}	
	}
	
	//VERIFICA SE EXISTE REJEIÇÃO CADASTRADA
	public IndicadoresRejeicao rejeicaoExiste(Integer ano, String mes){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		IndicadoresRejeicao indicadoresRejeicao = null;
			
		try{
			Query consulta = sessao.getNamedQuery("IndicadoresRejeicao.rejeicaoExiste");
			consulta.setInteger("ano", ano);
			consulta.setText("mes", mes);
			indicadoresRejeicao = (IndicadoresRejeicao) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
			
		return indicadoresRejeicao;
	}
}
