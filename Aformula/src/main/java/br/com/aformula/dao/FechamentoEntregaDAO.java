package br.com.aformula.dao;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.aformula.domain.FechamentoEntrega;
import br.com.aformula.util.HibernateUtil;

public class FechamentoEntregaDAO {
	public void salvar(FechamentoEntrega fechamentoEntrega){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(fechamentoEntrega);
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
	public List<FechamentoEntrega> listar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<FechamentoEntrega> fechamentoEntregas = null;
		
		try{
			Query consulta = sessao.getNamedQuery("FechamentoEntrega.listar");
			fechamentoEntregas = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return fechamentoEntregas;
	}
	
	@SuppressWarnings("unchecked")
	public List<FechamentoEntrega> listarMesFechado(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<FechamentoEntrega> fechamentoEntregas = null;
		
		try{
			Query consulta = sessao.getNamedQuery("FechamentoEntregaMesFechado.listar");
			fechamentoEntregas = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return fechamentoEntregas;
	}
	
	public void editar(FechamentoEntrega fechamentoEntrega){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(fechamentoEntrega);
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
	
	public FechamentoEntrega buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		FechamentoEntrega fechamentoEntrega = null;
		
		try{
			Query consulta = sessao.getNamedQuery("FechamentoEntrega.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			fechamentoEntrega = (FechamentoEntrega) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		
		return fechamentoEntrega;
	}
	
	public Long fechamentoAberto() {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Long codigoFechamentoAberto;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT MAX(codigo) AS codigo FROM FechamentoEntrega fechamentoEntrega WHERE fechamentoEntrega.status = 'ABERTO'");
			
			Query consulta = sessao.createQuery(sql.toString());
			codigoFechamentoAberto = (Long) consulta.uniqueResult();

		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return codigoFechamentoAberto;
	}
	
	public Long totalEntregasMes(Date data_inicial_mes, Date data_final_mes) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Long totalEntregas = 0L;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT COUNT(entrega.codigo) AS quantidade FROM Entrega entrega WHERE entrega.status = 'REALIZADA' AND (entrega.data_entrega BETWEEN :data_inicial_mes AND :data_final_mes)");
			
			Query consulta = sessao.createQuery(sql.toString());
			consulta.setDate("data_inicial_mes", data_inicial_mes);
			consulta.setDate("data_final_mes", data_final_mes);
		
			totalEntregas = (Long) consulta.uniqueResult();
			
			if (totalEntregas == null) {
				totalEntregas = 0L;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return totalEntregas;
	}
	
	public Double totalRecebidosMes(Date data_inicial_mes, Date data_final_mes) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Double totalRecebidos = 0d;
			
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SUM(entrega.valor_receber) AS totalRecebidos FROM Entrega entrega WHERE entrega.status = 'REALIZADA' AND (entrega.data_entrega BETWEEN :data_inicial_mes AND :data_final_mes)");
			
			Query consulta = sessao.createQuery(sql.toString());

			consulta.setDate("data_inicial_mes", data_inicial_mes);
			consulta.setDate("data_final_mes", data_final_mes);
			totalRecebidos = (Double) consulta.uniqueResult();

			if (totalRecebidos == null) {
				totalRecebidos = 0d;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return totalRecebidos;
	}

	public Double totalTaxasMes(Date data_inicial_mes, Date data_final_mes) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Double totalTaxas = 0d;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SUM(entrega.taxa) AS quantidade FROM Entrega entrega WHERE entrega.status = 'REALIZADA' AND (entrega.data_entrega BETWEEN :data_inicial_mes AND :data_final_mes)");
			
			Query consulta = sessao.createQuery(sql.toString());
		
			consulta.setDate("data_inicial_mes", data_inicial_mes);
			consulta.setDate("data_final_mes", data_final_mes);
			totalTaxas = (Double) consulta.uniqueResult();

			if (totalTaxas == null) {
				totalTaxas = 0d;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return totalTaxas;
	}
	
	public Double totalDAVMes(Date data_inicial_mes, Date data_final_mes) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Double totalDAV = 0d;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SUM(entrega.valor_dav) AS quantidade FROM Entrega entrega WHERE entrega.status = 'REALIZADA' AND (entrega.data_entrega BETWEEN :data_inicial_mes AND :data_final_mes)");
			
			Query consulta = sessao.createQuery(sql.toString());
		
			consulta.setDate("data_inicial_mes", data_inicial_mes);
			consulta.setDate("data_final_mes", data_final_mes);
			totalDAV = (Double) consulta.uniqueResult();

			if (totalDAV == null) {
				totalDAV = 0d;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return totalDAV;
	}
	
	//VERIFICA QUAL MÊS ESTÁ EM ABERTO PARA NOTIFICAR AO USUÁRIO
	public String verificaFechamentoAberto() {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		String anoMes = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT fechamentoEntrega.ano, fechamentoEntrega.mes FROM FechamentoEntrega fechamentoEntrega WHERE fechamentoEntrega.status = 'ABERTO'");

			Query consulta = sessao.createQuery(sql.toString());
		    Object resultado = consulta.uniqueResult(); // Retorna como Object


		    
		    if (resultado != null && resultado instanceof Object[]) {
	            Object[] valores = (Object[]) resultado; // Casting para Object[]
	            Integer ano = (Integer) valores[0]; // Primeiro valor é o ano
	            
	            String mes = null;
	            switch ((String) valores[1]) {
		    	case "JANEIRO":
		    		mes = "01"; // Segundo valor é o mês	 
		    		break;
		    	case "FEVEREIRO":
		    		mes = "02";
			        break;
		    	case "MARÇO":
		    		mes = "03";
			        break;
			    case "ABRIL":
			    	mes = "04";
			        break;
			    case "MAIO":
			    	mes = "05";
		       		break;
			    case "JUNHO":
			    	mes = "06";
			    	break;
			    case "JULHO":
			    	mes = "07";
			    	break;
			    case "AGOSTO":
			    	mes = "09";
			    	break;
			    case "SETEMBRO":
			    	mes = "09";
			    	break;
			    case "OUTUBRO":
			    	mes = "10";
			    	break;
			    case "NOVEMBRO":
			    	mes = "11";
			    	break;
			    case "DEZEMBRO":
			    	mes = "12";
			    	break;
	            }
	            
	         // Converte para String no formato desejado
	         anoMes =  mes + "/" +  ano; // Exemplo: "01/2024"
	         
		    }
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		
		return anoMes;
	}

}
