package br.com.aformula.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.aformula.domain.ProducaoPCP;
import br.com.aformula.util.HibernateUtil;

public class ProducaoPCPDAO {
	public void salvar(ProducaoPCP ProducaoPCP){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(ProducaoPCP);
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
	public List<ProducaoPCP> listar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<ProducaoPCP> ProducaoPCPs = null;
		
		try{
			Query consulta = sessao.getNamedQuery("ProducaoPCP.listar");
			ProducaoPCPs = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		
		return ProducaoPCPs;
	}	
	
	public void excluir(ProducaoPCP producaoPCP){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(producaoPCP);
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
	
	public ProducaoPCP buscarPorCodigo(Long funcionario, Integer dav, String identificador){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		ProducaoPCP producaoPCP = null;
		
		try{
			Query consulta = sessao.getNamedQuery("ProducaoPCP.buscarPorCodigo");
			consulta.setLong("funcionario", funcionario);
			consulta.setInteger("dav", dav);
			consulta.setString("identificador", identificador);
			producaoPCP = (ProducaoPCP) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}	
		return producaoPCP;
	}
	
	public void editar(ProducaoPCP producaoPCP){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(producaoPCP);
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
