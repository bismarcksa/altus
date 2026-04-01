package br.com.aformula.dao;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.aformula.domain.MetasLoja68;
import br.com.aformula.util.HibernateUtil;

public class MetasLoja68DAO {
	public void salvar(MetasLoja68 metasLoja68){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(metasLoja68);
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
	public List<MetasLoja68> listar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<MetasLoja68> metasLoja68s = null;
		
		try{
			Query consulta = sessao.getNamedQuery("MetasLoja68.listar");
			metasLoja68s = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return metasLoja68s;
	}
	
	public MetasLoja68 buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		MetasLoja68 metasLoja68 = null;
		
		try{
			Query consulta = sessao.getNamedQuery("MetasLoja68.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			metasLoja68 = (MetasLoja68) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		
		return metasLoja68;
	}
	
	public void excluir(MetasLoja68 metasLoja68){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(metasLoja68);
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
	
	public void editar(MetasLoja68 metasLoja68){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(metasLoja68);
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
	
	public BigDecimal totalMetaLoja68(Integer ano) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		BigDecimal totalMetaLoja68 = BigDecimal.ZERO;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SUM(loja68.meta) AS totalMeta FROM MetasLoja68 loja68 WHERE loja68.ano = :ano");
	
			Query consulta = sessao.createQuery(sql.toString());
			
			consulta.setInteger("ano", ano);
			totalMetaLoja68 = (BigDecimal) consulta.uniqueResult();

			if (totalMetaLoja68 == null) {
				totalMetaLoja68 = BigDecimal.ZERO;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return totalMetaLoja68;

		
	}
	
	public BigDecimal totalVendasLoja68(Integer ano) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		BigDecimal totalVendasLoja68 = BigDecimal.ZERO;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SUM(loja68.vendas) AS totalVendas FROM MetasLoja68 loja68 WHERE loja68.ano = :ano");
	
			Query consulta = sessao.createQuery(sql.toString());
			
			consulta.setInteger("ano", ano);
			totalVendasLoja68 = (BigDecimal) consulta.uniqueResult();

			if (totalVendasLoja68 == null) {
				totalVendasLoja68 = BigDecimal.ZERO;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return totalVendasLoja68;
	}
	
	public BigDecimal mediaMetaPercentualLoja68(Integer ano) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		BigDecimal totalMetaLoja68 = BigDecimal.ZERO;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ((SUM(loja68.vendas)/SUM(loja68.meta))*100) AS totalmediaRejeicaoPercentual FROM MetasLoja68 loja68 WHERE loja68.ano = :ano");
	
			Query consulta = sessao.createQuery(sql.toString());
			
			consulta.setInteger("ano", ano);
			totalMetaLoja68 = (BigDecimal) consulta.uniqueResult();

			if (totalMetaLoja68 == null) {
				totalMetaLoja68 = BigDecimal.ZERO;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return totalMetaLoja68;
	}
}
