package br.com.aformula.dao;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.aformula.domain.ControlePonto;
import br.com.aformula.util.HibernateUtil;

public class ControlePontoDAO {
	public void salvar(ControlePonto controlePonto){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(controlePonto);
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
	public List<ControlePonto> listar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<ControlePonto> controlePontos = null;
		
		try{
			Query consulta = sessao.getNamedQuery("ControlePonto.listar");
			controlePontos = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return controlePontos;
	}
	
	@SuppressWarnings("unchecked")
	public List<ControlePonto> listarPorSupervisor(long codigoSupervisor){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<ControlePonto> controlePontos = null;
		
		try{
			Query consulta = sessao.getNamedQuery("ControlePonto.listarPorSupervisor");
			consulta.setParameter("codigoSupervisor", codigoSupervisor);
			controlePontos = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return controlePontos;
	}
	
	//LISTA OS CONTROLES DE PONTOS DE UM DETERMINADO USUÁRIO, NA PÁGINA MEU PONTO, 
	//O USUÁRIO NÃO VAI VER PONTO DE OUTRAS PESSOAS
	@SuppressWarnings("unchecked")
	public List<ControlePonto> listarControlePontoUsuario(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<ControlePonto> controlePontos = null;
			
		try{
			Query consulta = sessao.getNamedQuery("ControlePonto.listarControlePontoUsuario");
			consulta.setLong("codigo", codigo);
			controlePontos = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return controlePontos;
	}
	
	public ControlePonto buscarPorCodigo(String competencia, Long codigoFuncionario){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		ControlePonto controlePonto = null;
		
		try{
			Query consulta = sessao.getNamedQuery("ControlePonto.buscarPorCodigo");
			consulta.setText("competencia", competencia);
			consulta.setLong("codigoFuncionario", codigoFuncionario);
			controlePonto = (ControlePonto) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		
		return controlePonto;
	}
	
	public void excluir(ControlePonto controlePonto){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(controlePonto);
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
	
	public void editar(ControlePonto controlePonto){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(controlePonto);
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
	
	public boolean checaSeExisteControlePonto(String competencia, Long codigoUsuario) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		boolean existePonto = false;
		Long quantidade = 0L;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT COUNT(competencia) As quantidade FROM ControlePonto controlePonto WHERE controlePonto.competencia = :competencia AND controlePonto.funcionario.codigo = :codigoUsuario ");

			Query consulta = sessao.createQuery(sql.toString());
			consulta.setString("competencia", competencia);
			consulta.setLong("codigoUsuario", codigoUsuario);
			quantidade = (Long) consulta.uniqueResult();

			if (quantidade == 0) {
				existePonto = false;
			}else {
				existePonto = true;
			}	
			
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return existePonto;
	}
	
	//RETORNA A SOMA DA HORAS EXTRAS FEITA PELOS USUÁRIOS NOS MESES FECHADOS
	public String verificaFechamentoPontoBancoHora(Long codigoUsuario) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Long quantidade, horas, minutos = 0L;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SUM(bancoHora) As bancoHora FROM ControlePonto controlePonto WHERE controlePonto.status = 'FECHADO' AND controlePonto.funcionario.codigo = :codigoUsuario");

			Query consulta = sessao.createQuery(sql.toString());
			consulta.setLong("codigoUsuario", codigoUsuario);
			quantidade = (Long) consulta.uniqueResult();						
			
			if(quantidade == null) {
				quantidade = 0L;
			}
			
			horas = quantidade / 60;			
		    minutos = quantidade % 60;
		    
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return String.format("%02d horas e %02d minutos", horas, minutos);
	}
	
	
	//RETORNA A QUANTIDADE DE HORAS DA ULTIMA COMPETENCIA PARA GRAVAR NA NOVA COMPETENCIA DO PONTO
	public Integer PegaBancoHoraUltimaCompetencia(Long codigoUsuario) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Integer quantidade = 0;
			
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT controlePonto.bancoHora FROM ControlePonto controlePonto WHERE controlePonto.funcionario.codigo = :codigoUsuario ORDER BY controlePonto.codigo DESC");

			Query consulta = sessao.createQuery(sql.toString());
			consulta.setLong("codigoUsuario", codigoUsuario);
			consulta.setMaxResults(1); // Equivalente ao LIMIT 1 - PEGA O ULTIMO REGISTRO
			quantidade = (Integer) consulta.uniqueResult();
			
			if(quantidade == null) {
				quantidade = 0;
			}	
					
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return quantidade;
	}
	
	
	//LISTA OS CONTROLES DE PONTOS FECHADO, OS ULTIMOS 5
	@SuppressWarnings("unchecked")
	public List<ControlePonto> listarFechado(Long codigoFuncionario){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<ControlePonto> controlePontos = null;
				
		try{
			Query consulta = sessao.getNamedQuery("ControlePonto.listarFechado").setMaxResults(5);
			consulta.setLong("codigoFuncionario", codigoFuncionario);
			controlePontos = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return controlePontos;
	}

}
