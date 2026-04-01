package br.com.aformula.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.aformula.domain.Configuracoes;
import br.com.aformula.util.HibernateUtil;

public class ConfiguracoesDAO {
	
	public Configuracoes buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Configuracoes configuracoes = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Configuracoes.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			configuracoes = (Configuracoes) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		
		return configuracoes;
	}
	
	public void editar(Configuracoes configuracoes){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(configuracoes);
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
}
