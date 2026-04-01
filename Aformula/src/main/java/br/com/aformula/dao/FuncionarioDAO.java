package br.com.aformula.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.aformula.domain.Funcionario;
import br.com.aformula.util.HibernateUtil;

public class FuncionarioDAO {
	public void salvar(Funcionario funcionario){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(funcionario);
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
	public List<Funcionario> listar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Funcionario> funcionarios = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Funcionario.listar");
			funcionarios = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return funcionarios;
	}
	
	public Funcionario buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Funcionario funcionario = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Funcionario.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			funcionario = (Funcionario) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		
		return funcionario;
	}
	
	public void excluir(Funcionario funcionario){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(funcionario);
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
	
	public void editar(Funcionario funcionario){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(funcionario);
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
	
	public Funcionario autenticar(String login, String senha) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Funcionario funcionario = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Funcionario.autenticar");
			consulta.setString("login", login);
			consulta.setString("senha", senha);
			
			funcionario = (Funcionario) consulta.uniqueResult();

		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}

		return funcionario;
	}
	
	public Funcionario buscarPorCPFNascimento(String cpf, Date nascimento){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Funcionario funcionario = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Funcionario.buscarPorCPFNascimento");
			consulta.setString("cpf", cpf);
			consulta.setDate("nascimento", nascimento);
			funcionario = (Funcionario) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		
		return funcionario;
	}
	
	@SuppressWarnings("unchecked")
	public List<Funcionario> listarAtivo(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Funcionario> funcionarios = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Funcionario.listarAtivo");
			funcionarios = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return funcionarios;
	}
	
	@SuppressWarnings("unchecked")
	public List<Funcionario> listarTodosAtivo(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Funcionario> funcionarios = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Funcionario.listarTodosAtivo");
			funcionarios = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return funcionarios;
	}
	
	@SuppressWarnings("unchecked")
	public List<Funcionario> listarFuncFilial(Long codigoFilial){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Funcionario> funcionarios = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Funcionario.listarFuncFilial");
			consulta.setLong("codigoFilial", codigoFilial);
			funcionarios = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return funcionarios;
	}
	
	//GERA LISTA DE FUNCIONÁRIOS ATIVOS DE UMA DETERMINADA FILIAL, PARA GERAR A FOLHA DE PONTO DE TODOS DE UMA VEZ SÓ
	@SuppressWarnings("unchecked")
	public List<Funcionario> listarFuncAtivoFilial(Long codigoFilial){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Funcionario> funcionarios = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Funcionario.listarFuncAtivoFilial");
			consulta.setLong("codigoFilial", codigoFilial);
			funcionarios = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return funcionarios;
	}
	
	//GERA LISTA DE FUNCIONÁRIOS ATIVOS E FUNÇÃO ENTEGADOR
	@SuppressWarnings("unchecked")
	public List<Funcionario> listarEntregadores(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Funcionario> funcionarios = null;
			
		try{
			Query consulta = sessao.getNamedQuery("Funcionario.listarEntregadores");
			funcionarios = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return funcionarios;
	}
	
	//GERA LISTA DE FUNCIONÁRIOS ATIVOS E FUNÇÃO ATENDENTE
	@SuppressWarnings("unchecked")
	public List<Funcionario> listarAtendentes(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Funcionario> funcionarios = null;
				
		try{
			Query consulta = sessao.getNamedQuery("Funcionario.listarAtendentes");
			funcionarios = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return funcionarios;
	}
	
	@SuppressWarnings("unchecked")
	public List<Funcionario> listarTodosAtivoNaoLiderancao(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Funcionario> funcionarios = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Funcionario.listarTodosAtivoNaoLideranca");
			funcionarios = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return funcionarios;
	}
}
