package br.com.aformula.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.aformula.domain.Producao;
import br.com.aformula.util.HibernateUtil;

public class ProducaoDAO {
	public void salvar(Producao producao){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(producao);
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
	public List<Producao> listar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Producao> producaos = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Producao.listar");
			producaos = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		
		return producaos;
	}
	
	//LISTA SÓ PENDENTES PARA CONFERENCIA
	@SuppressWarnings("unchecked")
	public List<Producao> listarConferencia(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Producao> producaos = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Producao.listarConferencia");
			producaos = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		
		return producaos;
	}
	
	//LISTA AS PRODUÇÕES PENDENTE OU EM PRODUÇÃO PARA OS TÉCNICOS EM LABORATÓRIO, NÃO LISTA DISPENSADO
	@SuppressWarnings("unchecked")
	public List<Producao> listarPendenteProducao(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Producao> producaos = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Producao.listarPendenteProducao");
			producaos = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		
		return producaos;
	}
	
	//LISTA AS PRODUÇÕES DE UM DETERMINADO USUÁRIO, NA PÁGINA DE SOLICITAR PRODUÇÃO, 
	//O USUÁRIO NÃO VAI VER PRODUÇÃO DE OUTRAS PESSOAS
	@SuppressWarnings("unchecked")
	public List<Producao> listarProducaoUsuario(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Producao> producaos = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Producao.listarProducaoUsuario");
			consulta.setLong("codigo", codigo);
			producaos = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return producaos;
	}
	
	public Producao buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Producao producao = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Producao.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			producao = (Producao) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		
		return producao;
	}
	
	public void excluir(Producao producao){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(producao);
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
	
	public void editar(Producao producao){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(producao);
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
	
	public Long checarLimeteDiario(Date data_dispensacao, String tipo) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Long quantidade = 0L;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SUM(producao.quantidade) AS quantidade FROM Producao producao WHERE producao.tipo = :tipo AND producao.data_dispensacao = :data_dispensacao");
			
			Query consulta = sessao.createQuery(sql.toString());
			
			consulta.setDate("data_dispensacao", data_dispensacao);
			consulta.setText("tipo", tipo);
			quantidade = (Long) consulta.uniqueResult();
			
			if (quantidade == null) {
				quantidade = 0L;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return quantidade;
	}
	

	@SuppressWarnings("unchecked")
	public List<Producao> listarPendenteAgregadoData() {
		Session sessao = HibernateUtil.getSessionFactory().openSession();

		List<Producao> producaoPendenteAgregadoData = new ArrayList<Producao>();		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT producao.data_dispensacao, producao.tipo, SUM(producao.quantidade), producao.status FROM Producao producao WHERE producao.status <> 'DISPENSADO' GROUP BY producao.data_dispensacao, producao.tipo, producao.status ORDER BY producao.data_dispensacao ASC");
				
			Query consulta = sessao.createQuery(sql.toString());
					
			List<Object[]> objetos = consulta.list(); 
				
			for (Object[] o : objetos) {
				Object[] aux = o;
				Producao r = new Producao();
				
				r.setData_dispensacao((Date)aux[0]); // DATA DA DISPENSAÇÃO
				r.setTipo((String)aux[1]); // TIPO
				
				// O CAMPO AGREGADO NO SQL - era pra ter usado o setQuantidade, mas ele é integer aí armenguei com essa varivel do Producao
				r.setTotalProducaoDia((Long)aux[2]); 
				
				r.setStatus((String)aux[3]); // STATUS
				producaoPendenteAgregadoData.add(r);     
			}	
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return producaoPendenteAgregadoData;
	}
	
	public Long checarDav(Integer dav, String identificador) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Long totalDav = 0L;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT COUNT(producao.dav) AS quantidade FROM Producao producao WHERE producao.dav = :dav AND producao.identificador = :identificador");
			
			Query consulta = sessao.createQuery(sql.toString());
			consulta.setInteger("dav", dav);
			consulta.setString("identificador", identificador);
			totalDav = (Long) consulta.uniqueResult();
			
			if (totalDav == null) {
				totalDav = 0L;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return totalDav;
	}
}
