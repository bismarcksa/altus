package br.com.aformula.dao;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.aformula.domain.GestaoPerformanceFunc;
import br.com.aformula.util.HibernateUtil;

public class GestaoPerformanceFuncDAO {
	public void salvar(GestaoPerformanceFunc gestaoPerformanceFunc){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(gestaoPerformanceFunc);
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
	public List<GestaoPerformanceFunc> listar(Long funcionario, Integer ano){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<GestaoPerformanceFunc> gestaoPerformancesFunc = null;
		
		try{
			Query consulta = sessao.getNamedQuery("GestaoPerformanceFunc.listar");
			consulta.setLong("funcionario", funcionario);
			consulta.setInteger("ano", ano);
			gestaoPerformancesFunc = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return gestaoPerformancesFunc;
	}
	
	@SuppressWarnings("unchecked")
	public List<GestaoPerformanceFunc> listarPerformanceFuncUsuario(Long funcionario){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<GestaoPerformanceFunc> gestaoPerformancesFunc = null;
		
		try{
			Query consulta = sessao.getNamedQuery("GestaoPerformanceFunc.listarPerformanceFuncUsuario");
			consulta.setLong("funcionario", funcionario);
			gestaoPerformancesFunc = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return gestaoPerformancesFunc;
	}
	
	public GestaoPerformanceFunc buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		GestaoPerformanceFunc gestaoPerformanceFunc = null;
		
		try{
			Query consulta = sessao.getNamedQuery("GestaoPerformanceFunc.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			gestaoPerformanceFunc = (GestaoPerformanceFunc) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}	
		return gestaoPerformanceFunc;
	}
	
	public void excluir(GestaoPerformanceFunc gestaoPerformanceFunc){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(gestaoPerformanceFunc);
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
	
	public void editar(GestaoPerformanceFunc gestaoPerformanceFunc){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(gestaoPerformanceFunc);
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
	
	//VERIFICA SE EXISTE PERFORMANCE FUNC CADASTRADA
	public GestaoPerformanceFunc performanceFuncExiste(Long funcionario, Integer ano, String mes){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		GestaoPerformanceFunc gestaoPerformanceFunc = null;
			
		try{
			Query consulta = sessao.getNamedQuery("GestaoPerformanceFunc.performanceFuncExiste");
			consulta.setLong("funcionario", funcionario);
			consulta.setInteger("ano", ano);
			consulta.setText("mes", mes);
			gestaoPerformanceFunc = (GestaoPerformanceFunc) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
			
		return gestaoPerformanceFunc;
	}
	
	public BigDecimal totalMetaPerformanceFunc(Long funcionario, Integer ano) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		BigDecimal totalMetaPerformance = BigDecimal.ZERO;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SUM(gpf.meta) AS totalMeta FROM GestaoPerformanceFunc gpf INNER JOIN gpf.gestaoPerformance gp WHERE gp.funcionario.codigo = :funcionario AND gp.ano = :ano");
	
			Query consulta = sessao.createQuery(sql.toString());

			consulta.setLong("funcionario", funcionario);			
			consulta.setInteger("ano", ano);
			totalMetaPerformance = (BigDecimal) consulta.uniqueResult();

			if (totalMetaPerformance == null) {
				totalMetaPerformance = BigDecimal.ZERO;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return totalMetaPerformance;
	}
	
	public BigDecimal totalVendasPerformanceFunc(Long funcionario, Integer ano) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		BigDecimal totalMetaPerformance = BigDecimal.ZERO;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SUM(gpf.venda) AS totalVendas FROM GestaoPerformanceFunc gpf INNER JOIN gpf.gestaoPerformance gp WHERE gp.funcionario.codigo = :funcionario AND gp.ano = :ano");
	
			Query consulta = sessao.createQuery(sql.toString());

			consulta.setLong("funcionario", funcionario);			
			consulta.setInteger("ano", ano);
			totalMetaPerformance = (BigDecimal) consulta.uniqueResult();

			if (totalMetaPerformance == null) {
				totalMetaPerformance = BigDecimal.ZERO;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return totalMetaPerformance;
	}
	
	public Double mediaMetaPercentual(Long funcionario, Integer ano) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Double totalMetaPerformance = 0d;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT AVG((gpf.venda/gpf.meta)*100) AS totalmediaMetaPercentual FROM GestaoPerformanceFunc gpf INNER JOIN gpf.gestaoPerformance gp WHERE gp.funcionario.codigo = :funcionario AND gp.ano = :ano");
	
			Query consulta = sessao.createQuery(sql.toString());

			consulta.setLong("funcionario", funcionario);			
			consulta.setInteger("ano", ano);
			totalMetaPerformance = (Double) consulta.uniqueResult();

			if (totalMetaPerformance == null) {
				totalMetaPerformance = 0d;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return totalMetaPerformance;
	}
	
	public Double mediaRejeicaoPercentual(Long funcionario, Integer ano) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Double totalMetaPerformance = 0d;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT AVG(gpf.rejeicao) AS totalmediaRejeicaoPercentual FROM GestaoPerformanceFunc gpf INNER JOIN gpf.gestaoPerformance gp WHERE gp.funcionario.codigo = :funcionario AND gp.ano = :ano");
	
			Query consulta = sessao.createQuery(sql.toString());

			consulta.setLong("funcionario", funcionario);			
			consulta.setInteger("ano", ano);
			totalMetaPerformance = (Double) consulta.uniqueResult();

			if (totalMetaPerformance == null) {
				totalMetaPerformance = 0d;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return totalMetaPerformance;
	}
}
