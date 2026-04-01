package br.com.aformula.dao;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.aformula.domain.Bairro;
import br.com.aformula.util.HibernateUtil;

public class BairroDAO {
	public void salvar(Bairro bairro){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(bairro);
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
	public List<Bairro> listar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Bairro> bairros = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Bairro.listar");
			bairros = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return bairros;
	}
	
	public Bairro buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Bairro bairro = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Bairro.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			bairro = (Bairro) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return bairro;
	}
	
	public void excluir(Bairro bairro){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(bairro);
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
	
	public void editar(Bairro bairro){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(bairro);
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
