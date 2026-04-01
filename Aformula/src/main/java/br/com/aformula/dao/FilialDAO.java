package br.com.aformula.dao;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.aformula.domain.Filial;
import br.com.aformula.util.HibernateUtil;

public class FilialDAO {
	public void salvar(Filial filial){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(filial);
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
	public List<Filial> listar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Filial> filiais = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Filial.listar");
			filiais = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return filiais;
	}
	
	@SuppressWarnings("unchecked")
	public List<Filial> listarAtivo(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Filial> filiais = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Filial.listarAtivo");
			filiais = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return filiais;
	}
	
	public Filial buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Filial filial = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Filial.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			filial = (Filial) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return filial;
	}
	
	public void excluir(Filial filial){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(filial);
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
	
	public void editar(Filial filial){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(filial);
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
