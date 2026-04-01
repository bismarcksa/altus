package br.com.aformula.dao;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.aformula.domain.ControlePontoDetalhe;
import br.com.aformula.util.HibernateUtil;

public class ControlePontoDetalheDAO {
	public void salvar(ControlePontoDetalhe controlePontoDetalhe){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(controlePontoDetalhe);
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
	public List<ControlePontoDetalhe> listar(String competencia, Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<ControlePontoDetalhe> controlePontoDetalhes = null;
		
		try{
			Query consulta = sessao.getNamedQuery("ControlePontoDetalhe.listar");
			consulta.setText("competencia", competencia);
			consulta.setLong("codigo", codigo);
			controlePontoDetalhes = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return controlePontoDetalhes;
	}
	
	public ControlePontoDetalhe buscarPorCodigo(String competencia, Long codigo, Date data){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		ControlePontoDetalhe controlePontoDetalhe = null;
		
		try{
			Query consulta = sessao.getNamedQuery("ControlePontoDetalhe.buscarPorCodigo");
			consulta.setText("competencia", competencia);
			consulta.setLong("codigo", codigo);
			consulta.setDate("data", data);
			controlePontoDetalhe = (ControlePontoDetalhe) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		
		return controlePontoDetalhe;
	}
	
	public void excluir(ControlePontoDetalhe controlePontoDetalhe){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(controlePontoDetalhe);
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
	
	public void editar(ControlePontoDetalhe controlePontoDetalhe){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(controlePontoDetalhe);
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
