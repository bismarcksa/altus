package br.com.aformula.dao;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.aformula.domain.Entrega;
import br.com.aformula.util.HibernateUtil;

public class EntregaDAO {
	public void salvar(Entrega entrega){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(entrega);
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
	public List<Entrega> listar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Entrega> entregas = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Entrega.listar");
			entregas = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return entregas;
	}
	
	@SuppressWarnings("unchecked")
	public List<Entrega> listarEntregaExtra(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Entrega> entregas = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Entrega.listarEntregaExtra");
			entregas = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return entregas;
	}
	
	@SuppressWarnings("unchecked")
	public List<Entrega> listarEntregaFin(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Entrega> entregas = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Entrega.listarEntregaFin");
			entregas = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return entregas;
	}
	
	public Entrega buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Entrega entrega = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Entrega.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			entrega = (Entrega) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return entrega;
	}
	
	public void excluir(Entrega entrega){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(entrega);
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
	
	public void editar(Entrega entrega){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(entrega);
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
