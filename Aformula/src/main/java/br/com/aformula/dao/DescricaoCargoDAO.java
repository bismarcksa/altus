package br.com.aformula.dao;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.aformula.domain.DescricaoCargo;
import br.com.aformula.util.HibernateUtil;

public class DescricaoCargoDAO {
	public void salvar(DescricaoCargo descricaoCargo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(descricaoCargo);
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
	public List<DescricaoCargo> listar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<DescricaoCargo> descricaoCargos = null;
		
		try{
			Query consulta = sessao.getNamedQuery("DescricaoCargo.listar");
			descricaoCargos = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return descricaoCargos;
	}
	
	public DescricaoCargo buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		DescricaoCargo descricaoCargo = null;
		
		try{
			Query consulta = sessao.getNamedQuery("DescricaoCargo.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			descricaoCargo = (DescricaoCargo) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return descricaoCargo;
	}
	
	public void excluir(DescricaoCargo descricaoCargo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(descricaoCargo);
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
	
	public void editar(DescricaoCargo descricaoCargo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(descricaoCargo);
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
