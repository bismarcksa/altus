package br.com.aformula.dao;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.aformula.domain.Ocorrencia;
import br.com.aformula.util.HibernateUtil;

public class OcorrenciaDAO {
	public void salvar(Ocorrencia ocorrencia){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(ocorrencia);
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
	public List<Ocorrencia> listar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Ocorrencia> ocorrencias = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Ocorrencia.listar");
			ocorrencias = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return ocorrencias;
	}
	
	public Ocorrencia buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Ocorrencia ocorrencia = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Ocorrencia.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			ocorrencia = (Ocorrencia) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return ocorrencia;
	}
	
	public void excluir(Ocorrencia ocorrencia){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(ocorrencia);
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
	
	public void editar(Ocorrencia ocorrencia){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(ocorrencia);
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
