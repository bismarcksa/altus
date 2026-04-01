package br.com.aformula.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.aformula.domain.FechamentoProducao;
import br.com.aformula.util.HibernateUtil;

public class FechamentoProducaoDAO {
	public void salvar(FechamentoProducao fechamentoProducao){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(fechamentoProducao);
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
	public List<FechamentoProducao> listar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<FechamentoProducao> fechamentoProducoes = null;
		
		try{
			Query consulta = sessao.getNamedQuery("FechamentoProducao.listar");
			fechamentoProducoes = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return fechamentoProducoes;
	}
	
	@SuppressWarnings("unchecked")
	public List<FechamentoProducao> listarMesFechado(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<FechamentoProducao> fechamentoProducoes = null;
		
		try{
			Query consulta = sessao.getNamedQuery("FechamentoProducaoMesFechado.listar");
			fechamentoProducoes = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return fechamentoProducoes;
	}
	
	public void editar(FechamentoProducao fechamentoProducao){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(fechamentoProducao);
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
	
	public FechamentoProducao buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		FechamentoProducao fechamentoProducao = null;
		
		try{
			Query consulta = sessao.getNamedQuery("FechamentoProducao.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			fechamentoProducao = (FechamentoProducao) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		
		return fechamentoProducao;
	}
	
	public Long fechamentoAberto() {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Long codigoFechamentoAberto;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT MAX(codigo) AS codigo FROM FechamentoProducao fechamentoProducao WHERE fechamentoProducao.status = 'ABERTO'");
			
			Query consulta = sessao.createQuery(sql.toString());
			codigoFechamentoAberto = (Long) consulta.uniqueResult();

		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return codigoFechamentoAberto;
	}
	
	public Long totalContratoMes(String tipo, Date data_inicial_mes, Date data_final_mes) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Long totalContratos = 0L;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT COUNT(producao.codigo) AS quantidade FROM Producao producao WHERE producao.tipo = :tipo AND producao.status = 'DISPENSADO' AND (producao.data_dispensacao BETWEEN :data_inicial_mes AND :data_final_mes)");
			
			Query consulta = sessao.createQuery(sql.toString());
			consulta.setText("tipo", tipo);
			consulta.setDate("data_inicial_mes", data_inicial_mes);
			consulta.setDate("data_final_mes", data_final_mes);
		
			totalContratos = (Long) consulta.uniqueResult();
			
			if (totalContratos == null) {
				totalContratos = 0L;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return totalContratos;
	}
	
	public Long totalProducaoMes(String tipo, Date data_inicial_mes, Date data_final_mes) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Long totalProducoes = 0L;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SUM(producao.quantidade) AS quantidade FROM Producao producao WHERE producao.tipo = :tipo AND producao.status = 'DISPENSADO' AND (producao.data_dispensacao BETWEEN :data_inicial_mes AND :data_final_mes)");
			
			Query consulta = sessao.createQuery(sql.toString());

			consulta.setText("tipo", tipo);			
			consulta.setDate("data_inicial_mes", data_inicial_mes);
			consulta.setDate("data_final_mes", data_final_mes);
			totalProducoes = (Long) consulta.uniqueResult();

			if (totalProducoes == null) {
				totalProducoes = 0L;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return totalProducoes;
	}
	
	public Double mediaProdContratoMes(String tipo, Date data_inicial_mes, Date data_final_mes) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Double mediaProdContrato = 0d;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT AVG(producao.quantidade) AS media FROM Producao producao WHERE producao.tipo = :tipo AND producao.status = 'DISPENSADO' AND (producao.data_dispensacao BETWEEN :data_inicial_mes AND :data_final_mes)");
			
			Query consulta = sessao.createQuery(sql.toString());

			consulta.setText("tipo", tipo);			
			consulta.setDate("data_inicial_mes", data_inicial_mes);
			consulta.setDate("data_final_mes", data_final_mes);
			mediaProdContrato = (Double) consulta.uniqueResult();
			if (mediaProdContrato == null) {
				mediaProdContrato = 0d;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return mediaProdContrato;
	}
	
}
