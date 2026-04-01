package br.com.aformula.dao;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.aformula.domain.MetasLoja67;
import br.com.aformula.util.HibernateUtil;

public class MetasLoja67DAO {
	public void salvar(MetasLoja67 metasLoja67){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(metasLoja67);
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
	public List<MetasLoja67> listar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<MetasLoja67> metasLoja67s = null;
		
		try{
			Query consulta = sessao.getNamedQuery("MetasLoja67.listar");
			metasLoja67s = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return metasLoja67s;
	}
	
	public MetasLoja67 buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		MetasLoja67 metasLoja67 = null;
		
		try{
			Query consulta = sessao.getNamedQuery("MetasLoja67.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			metasLoja67 = (MetasLoja67) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		
		return metasLoja67;
	}
	
	public void excluir(MetasLoja67 metasLoja67){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(metasLoja67);
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
	
	public void editar(MetasLoja67 metasLoja67){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(metasLoja67);
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
	
	public BigDecimal totalMetaLoja67(Integer ano) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		BigDecimal totalMetaLoja67 = BigDecimal.ZERO;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SUM(loja67.meta) AS totalMeta FROM MetasLoja67 loja67 WHERE loja67.ano = :ano");
	
			Query consulta = sessao.createQuery(sql.toString());
			
			consulta.setInteger("ano", ano);
			totalMetaLoja67 = (BigDecimal) consulta.uniqueResult();

			if (totalMetaLoja67 == null) {
				totalMetaLoja67 = BigDecimal.ZERO;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return totalMetaLoja67;

		
	}
	
	public BigDecimal totalVendasLoja67(Integer ano) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		BigDecimal totalVendasLoja67 = BigDecimal.ZERO;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SUM(loja67.vendas) AS totalVendas FROM MetasLoja67 loja67 WHERE loja67.ano = :ano");
	
			Query consulta = sessao.createQuery(sql.toString());
			
			consulta.setInteger("ano", ano);
			totalVendasLoja67 = (BigDecimal) consulta.uniqueResult();

			if (totalVendasLoja67 == null) {
				totalVendasLoja67 = BigDecimal.ZERO;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return totalVendasLoja67;
	}
	
	public BigDecimal mediaMetaPercentualLoja67(Integer ano) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		BigDecimal totalMetaLoja67 = BigDecimal.ZERO;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ((SUM(loja67.vendas)/SUM(loja67.meta))*100) AS totalmediaRejeicaoPercentual FROM MetasLoja67 loja67 WHERE loja67.ano = :ano");
	
			Query consulta = sessao.createQuery(sql.toString());
			
			consulta.setInteger("ano", ano);
			totalMetaLoja67 = (BigDecimal) consulta.uniqueResult();

			if (totalMetaLoja67 == null) {
				totalMetaLoja67 = BigDecimal.ZERO;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return totalMetaLoja67;
	}
}
