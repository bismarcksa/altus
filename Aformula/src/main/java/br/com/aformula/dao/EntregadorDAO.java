package br.com.aformula.dao;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.aformula.domain.Entregador;
import br.com.aformula.util.HibernateUtil;

public class EntregadorDAO {
	public void salvar(Entregador entregador){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(entregador);
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
	public List<Entregador> listar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Entregador> entregadores = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Entregador.listar");
			entregadores = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return entregadores;
	}
	
	@SuppressWarnings("unchecked")
	public List<Entregador> listarAtivo(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Entregador> entregadores = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Entregador.listarAtivo");
			entregadores = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return entregadores;
	}
	
	public Entregador buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Entregador entregador = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Entregador.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			entregador = (Entregador) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return entregador;
	}
	
	public void excluir(Entregador entregador){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(entregador);
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
	
	public void editar(Entregador entregador){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(entregador);
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
