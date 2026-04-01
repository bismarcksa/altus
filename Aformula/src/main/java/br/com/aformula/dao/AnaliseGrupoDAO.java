package br.com.aformula.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.aformula.domain.AnaliseGrupo;
import br.com.aformula.util.HibernateUtil;

public class AnaliseGrupoDAO {
	public void salvar(AnaliseGrupo analiseGrupo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(analiseGrupo);
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
	public List<AnaliseGrupo> listar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<AnaliseGrupo> analiseGrupos = null;
		
		try{
			Query consulta = sessao.getNamedQuery("AnaliseGrupo.listar");
			analiseGrupos = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return analiseGrupos;
	}
	
	public AnaliseGrupo buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		AnaliseGrupo analiseGrupo = null;
		
		try{
			Query consulta = sessao.getNamedQuery("AnaliseGrupo.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			analiseGrupo = (AnaliseGrupo) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		
		return analiseGrupo;
	}
	
	public void excluir(AnaliseGrupo analiseGrupo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(analiseGrupo);
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
	
	public void editar(AnaliseGrupo analiseGrupo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(analiseGrupo);
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

