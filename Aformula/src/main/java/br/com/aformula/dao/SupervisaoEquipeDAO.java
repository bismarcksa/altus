package br.com.aformula.dao;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.aformula.domain.Funcionario;
import br.com.aformula.domain.SupervisaoEquipe;
import br.com.aformula.util.HibernateUtil;

public class SupervisaoEquipeDAO {
	public void salvar(SupervisaoEquipe supervisaoEquipe){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(supervisaoEquipe);
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
	public List<SupervisaoEquipe> listar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<SupervisaoEquipe> supervisoesEquipe = new ArrayList<SupervisaoEquipe>();
		
		try{
			Query consulta = sessao.getNamedQuery("SupervisaoEquipe.listar");
			supervisoesEquipe = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return supervisoesEquipe; 
	
	}
	

	
	public void excluir(SupervisaoEquipe supervisaoEquipe){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(supervisaoEquipe);
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
	
	public void editar(SupervisaoEquipe supervisaoEquipe){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(supervisaoEquipe);
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
	
	//LISTA A EQUIPE DE UM SUPERVISOR
	@SuppressWarnings("unchecked")
	public List<SupervisaoEquipe> buscarPorCodigo(Long codigoSupervisor){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<SupervisaoEquipe> supervisaoEquipes = null;
					
		try{
			Query consulta = sessao.getNamedQuery("SupervisaoEquipe.buscarPorCodigo");
			consulta.setLong("codigoSupervisor", codigoSupervisor);
			supervisaoEquipes = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return supervisaoEquipes;
	}
	
	//VALIDA FUNCIONARIO PARA NÃO ESTAR EM DUAS EQUIPES
	public boolean buscarPorFuncionario(Long codigoFuncionario){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Funcionario funcionario = null;
		boolean validacao = false;				
		try{
			Query consulta = sessao.getNamedQuery("SupervisaoEquipe.buscarPorFuncionario");
			consulta.setLong("codigoFuncionario", codigoFuncionario);
			
			funcionario = (Funcionario) consulta.uniqueResult();
			
			//funcionario != null QUE DIZER QUE O FUNCIONARIO JÁ TEM UM SUPERVISOR
			if(funcionario != null) {
				validacao = true; 
			}else {
				validacao = false;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return validacao;
	}
}
