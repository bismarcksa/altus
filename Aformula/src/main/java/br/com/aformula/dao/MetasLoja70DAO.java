package br.com.aformula.dao;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.aformula.domain.MetasLoja70;
import br.com.aformula.util.HibernateUtil;

public class MetasLoja70DAO {
	public void salvar(MetasLoja70 metasLoja70){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(metasLoja70);
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
	public List<MetasLoja70> listar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<MetasLoja70> metasLoja70s = null;
		
		try{
			Query consulta = sessao.getNamedQuery("MetasLoja70.listar");
			metasLoja70s = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return metasLoja70s;
	}
	
	public MetasLoja70 buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		MetasLoja70 metasLoja70 = null;
		
		try{
			Query consulta = sessao.getNamedQuery("MetasLoja70.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			metasLoja70 = (MetasLoja70) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		
		return metasLoja70;
	}
	
	public void excluir(MetasLoja70 metasLoja70){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(metasLoja70);
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
	
	public void editar(MetasLoja70 metasLoja70){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(metasLoja70);
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
	
	public BigDecimal totalMetaLoja70(Integer ano) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		BigDecimal totalMetaLoja70 = BigDecimal.ZERO;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SUM(loja70.meta) AS totalMeta FROM MetasLoja70 loja70 WHERE loja70.ano = :ano");
	
			Query consulta = sessao.createQuery(sql.toString());
			
			consulta.setInteger("ano", ano);
			totalMetaLoja70 = (BigDecimal) consulta.uniqueResult();

			if (totalMetaLoja70 == null) {
				totalMetaLoja70 = BigDecimal.ZERO;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return totalMetaLoja70;

		
	}
	
	public BigDecimal totalVendasLoja70(Integer ano) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		BigDecimal totalVendasLoja70 = BigDecimal.ZERO;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SUM(loja70.vendas) AS totalVendas FROM MetasLoja70 loja70 WHERE loja70.ano = :ano");
	
			Query consulta = sessao.createQuery(sql.toString());
			
			consulta.setInteger("ano", ano);
			totalVendasLoja70 = (BigDecimal) consulta.uniqueResult();

			if (totalVendasLoja70 == null) {
				totalVendasLoja70 = BigDecimal.ZERO;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return totalVendasLoja70;
	}
	
	public BigDecimal mediaMetaPercentualLoja70(Integer ano) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		BigDecimal totalMetaLoja70 = BigDecimal.ZERO;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ((SUM(loja70.vendas)/SUM(loja70.meta))*100) AS totalmediaRejeicaoPercentual FROM MetasLoja70 loja70 WHERE loja70.ano = :ano");
	
			Query consulta = sessao.createQuery(sql.toString());
			
			consulta.setInteger("ano", ano);
			totalMetaLoja70 = (BigDecimal) consulta.uniqueResult();

			if (totalMetaLoja70 == null) {
				totalMetaLoja70 = BigDecimal.ZERO;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return totalMetaLoja70;
	}
}
