package br.com.aformula.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.aformula.domain.AnaliseRepresentante;
import br.com.aformula.util.HibernateUtil;

public class AnaliseRepresentanteDAO {
	public void salvar(AnaliseRepresentante analiseRepresentante){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(analiseRepresentante);
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
	public List<AnaliseRepresentante> listar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<AnaliseRepresentante> analiseRepresentantes = null;
		
		try{
			Query consulta = sessao.getNamedQuery("AnaliseRepresentante.listar");
			analiseRepresentantes = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return analiseRepresentantes;
	}
	
	public AnaliseRepresentante buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		AnaliseRepresentante analiseRepresentante = null;
		
		try{
			Query consulta = sessao.getNamedQuery("AnaliseRepresentante.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			analiseRepresentante = (AnaliseRepresentante) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		
		return analiseRepresentante;
	}
	
	public void excluir(AnaliseRepresentante analiseRepresentante){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(analiseRepresentante);
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
	
	public void editar(AnaliseRepresentante analiseRepresentante){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(analiseRepresentante);
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

