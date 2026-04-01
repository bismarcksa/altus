package br.com.aformula.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.aformula.domain.IndicadoresCortesia;
import br.com.aformula.util.HibernateUtil;

public class IndicadoresCortesiaDAO {
	public void salvar(IndicadoresCortesia indicadoresCortesia){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(indicadoresCortesia);
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
	public List<IndicadoresCortesia> listar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<IndicadoresCortesia> indicadoresCortesias = null;
		
		try{
			Query consulta = sessao.getNamedQuery("IndicadoresCortesia.listar");
			indicadoresCortesias = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return indicadoresCortesias;
	}
	
	public IndicadoresCortesia buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		IndicadoresCortesia indicadoresCortesia = null;
		
		try{
			Query consulta = sessao.getNamedQuery("IndicadoresCortesia.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			indicadoresCortesia = (IndicadoresCortesia) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		
		return indicadoresCortesia;
	}
	
	public void excluir(IndicadoresCortesia indicadoresCortesia){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(indicadoresCortesia);
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
	
	public void editar(IndicadoresCortesia indicadoresCortesia){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(indicadoresCortesia);
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

