package br.com.aformula.dao;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.aformula.domain.MetasGrupo;
import br.com.aformula.util.HibernateUtil;

public class MetasGrupoDAO {
	public void salvar(MetasGrupo metasGrupo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(metasGrupo);
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
	public List<MetasGrupo> listar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<MetasGrupo> metasGrupos = null;
		
		try{
			Query consulta = sessao.getNamedQuery("MetasGrupo.listar");
			metasGrupos = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return metasGrupos;
	}
	
	public MetasGrupo buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		MetasGrupo metasGrupo = null;
		
		try{
			Query consulta = sessao.getNamedQuery("MetasGrupo.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			metasGrupo = (MetasGrupo) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		
		return metasGrupo;
	}
	
	public void excluir(MetasGrupo metasGrupo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(metasGrupo);
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
	
	public void editar(MetasGrupo metasGrupo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(metasGrupo);
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
	
	public BigDecimal totalMetaGrupo(Integer ano) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		BigDecimal totalMetaGrupo = BigDecimal.ZERO;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SUM(grupo.meta) AS totalMeta FROM MetasGrupo grupo WHERE grupo.ano = :ano");
	
			Query consulta = sessao.createQuery(sql.toString());
			
			consulta.setInteger("ano", ano);
			totalMetaGrupo = (BigDecimal) consulta.uniqueResult();

			if (totalMetaGrupo == null) {
				totalMetaGrupo = BigDecimal.ZERO;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return totalMetaGrupo;

		
	}
	
	public BigDecimal totalVendasGrupo(Integer ano) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		BigDecimal totalVendasGrupo = BigDecimal.ZERO;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SUM(grupo.vendas) AS totalVendas FROM MetasGrupo grupo WHERE grupo.ano = :ano");
	
			Query consulta = sessao.createQuery(sql.toString());
			
			consulta.setInteger("ano", ano);
			totalVendasGrupo = (BigDecimal) consulta.uniqueResult();

			if (totalVendasGrupo == null) {
				totalVendasGrupo = BigDecimal.ZERO;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return totalVendasGrupo;
	}
	
	public BigDecimal mediaMetaPercentualGrupo(Integer ano) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		BigDecimal mediaMetaGrupo = BigDecimal.ZERO;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ((SUM(grupo.vendas)/SUM(grupo.meta))*100) AS totalmediaRejeicaoPercentual FROM MetasGrupo grupo WHERE grupo.ano = :ano");
	
			Query consulta = sessao.createQuery(sql.toString());
			
			consulta.setInteger("ano", ano);
			mediaMetaGrupo = (BigDecimal) consulta.uniqueResult();

			if (mediaMetaGrupo == null) {
				mediaMetaGrupo = BigDecimal.ZERO;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return mediaMetaGrupo;
	}
}
