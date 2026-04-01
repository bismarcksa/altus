package br.com.aformula.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.aformula.domain.AnaliseRepresentanteGrupo;
import br.com.aformula.util.HibernateUtil;

public class AnaliseRepresentanteGrupoDAO {
	public void salvar(AnaliseRepresentanteGrupo analiseRepresentanteGrupo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(analiseRepresentanteGrupo);
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
	public List<AnaliseRepresentanteGrupo> listar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<AnaliseRepresentanteGrupo> analiseRepresentanteGrupos = null;
		
		try{
			Query consulta = sessao.getNamedQuery("AnaliseRepresentanteGrupo.listar");
			analiseRepresentanteGrupos = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return analiseRepresentanteGrupos;
	}
	
	public AnaliseRepresentanteGrupo buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		AnaliseRepresentanteGrupo analiseRepresentanteGrupo = null;
		
		try{
			Query consulta = sessao.getNamedQuery("AnaliseRepresentanteGrupo.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			analiseRepresentanteGrupo = (AnaliseRepresentanteGrupo) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		
		return analiseRepresentanteGrupo;
	}
	
	public void excluir(AnaliseRepresentanteGrupo analiseRepresentanteGrupo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(analiseRepresentanteGrupo);
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
	
	public void editar(AnaliseRepresentanteGrupo analiseRepresentanteGrupo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(analiseRepresentanteGrupo);
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
