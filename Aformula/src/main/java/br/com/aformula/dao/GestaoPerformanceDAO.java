package br.com.aformula.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.aformula.domain.GestaoPerformance;
import br.com.aformula.util.HibernateUtil;

public class GestaoPerformanceDAO {
	public void salvar(GestaoPerformance gestaoPerformance){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(gestaoPerformance);
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
	public List<GestaoPerformance> listar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<GestaoPerformance> gestaoPerformances = null;
		
		try{
			Query consulta = sessao.getNamedQuery("GestaoPerformance.listar");
			gestaoPerformances = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return gestaoPerformances;
	}
	
	public GestaoPerformance buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		GestaoPerformance gestaoPerformance = null;
		
		try{
			Query consulta = sessao.getNamedQuery("GestaoPerformance.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			gestaoPerformance = (GestaoPerformance) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}	
		return gestaoPerformance;
	}
	
	public void excluir(GestaoPerformance gestaoPerformance){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(gestaoPerformance);
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
	
	public void editar(GestaoPerformance gestaoPerformance){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(gestaoPerformance);
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
	
	//VERIFICA SE EXISTE PERFORMANCE CADASTRADA
	public GestaoPerformance performanceExiste(Long funcionario, Integer ano){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		GestaoPerformance gestaoPerformance = null;
		
		try{
			Query consulta = sessao.getNamedQuery("GestaoPerformance.performanceExiste");
			consulta.setLong("funcionario", funcionario);
			consulta.setInteger("ano", ano);
			gestaoPerformance = (GestaoPerformance) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		
		return gestaoPerformance;
	}
}
