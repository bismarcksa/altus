package br.com.aformula.dao;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.aformula.domain.Grupo;
import br.com.aformula.util.HibernateUtil;

public class GrupoDAO {
	public void salvar(Grupo grupo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(grupo);
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
	public List<Grupo> listar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Grupo> grupos = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Grupo.listar");
			grupos = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return grupos;
	}
	
	public Grupo buscarPorCodigo(Integer codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Grupo grupo = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Grupo.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			grupo = (Grupo) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return grupo;
	}
	
	public void excluir(Grupo grupo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(grupo);
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
	
	public void editar(Grupo grupo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(grupo);
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
