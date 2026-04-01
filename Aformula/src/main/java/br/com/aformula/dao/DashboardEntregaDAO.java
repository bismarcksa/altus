package br.com.aformula.dao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import br.com.aformula.domain.Entrega;
import br.com.aformula.util.HibernateUtil;

public class DashboardEntregaDAO {

	public Long totalEntregaMes(Date data_inicial_mes, Date data_final_mes) {
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
		Double totalEntregas = 0d;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SUM(entrega.valor_receber) AS valor_receber FROM Entrega entrega WHERE entrega.status = 'REALIZADA' AND (entrega.data_entrega BETWEEN :data_inicial_mes AND :data_final_mes)");
			
			Query consulta = sessao.createQuery(sql.toString());
		
			consulta.setDate("data_inicial_mes", data_inicial_mes);
			consulta.setDate("data_final_mes", data_final_mes);
			totalEntregas = (Double) consulta.uniqueResult();
			
			if (totalEntregas == null) {
				totalEntregas = 0d;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return totalEntregas;
	}
	
	public Double totalDAVMes(Date data_inicial_mes, Date data_final_mes) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Double totalEntregas = 0d;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SUM(entrega.valor_dav) AS valor_dav FROM Entrega entrega WHERE entrega.status = 'REALIZADA' AND (entrega.data_entrega BETWEEN :data_inicial_mes AND :data_final_mes)");
			
			Query consulta = sessao.createQuery(sql.toString());
		
			consulta.setDate("data_inicial_mes", data_inicial_mes);
			consulta.setDate("data_final_mes", data_final_mes);
			totalEntregas = (Double) consulta.uniqueResult();
			
			if (totalEntregas == null) {
				totalEntregas = 0d;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		System.out.println("TOTAL DAV: " + totalEntregas);
		return totalEntregas;
	}
	
	
	public Double totalTaxasMes(Date data_inicial_mes, Date data_final_mes) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Double totalEntregas = 0d;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SUM(entrega.taxa) AS taxa FROM Entrega entrega WHERE entrega.status = 'REALIZADA' AND (entrega.data_entrega BETWEEN :data_inicial_mes AND :data_final_mes)");
			
			Query consulta = sessao.createQuery(sql.toString());
		
			consulta.setDate("data_inicial_mes", data_inicial_mes);
			consulta.setDate("data_final_mes", data_final_mes);
			totalEntregas = (Double) consulta.uniqueResult();

			if (totalEntregas == null) {
				totalEntregas = 0d;
			}
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return totalEntregas;
	}
	
	
	//DADOS DO GRÁFICO DE DONUT DE TOTAL DE ENTREGAS POR ENTREGADOR DO MÊS EM ABERTO
	@SuppressWarnings("unchecked")
	public List<Entrega> listarTotalEntregaEntregador(Date data_inicial_mes, Date data_final_mes) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();

		// Aqui vai ser manipulado o objetos.
		// Cria suaListaModel
		List<Entrega> entregaEntregador = new ArrayList<Entrega>();		
		try{

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT entrega.entregador.codigo, entrega.entregador.nome, COUNT(entrega.codigo) AS total FROM Entrega entrega WHERE entrega.status = 'REALIZADA' AND entrega.data_lancamento BETWEEN :data_inicial_mes AND :data_final_mes GROUP BY entrega.entregador.codigo, entrega.entregador.nome ORDER BY entrega.entregador.codigo");
				
			Query consulta = sessao.createQuery(sql.toString());
			consulta.setDate("data_inicial_mes", data_inicial_mes);
			consulta.setDate("data_final_mes", data_final_mes);				
				
			List<Object[]> objetos = consulta.list(); 
				
			for (Object[] o : objetos) {
	
				Object[] aux = o;
				Entrega r = new Entrega();
				//Objeto que sualistaModel recebe, vamos chamar de x
				r.setCodigoEntregador((Long) aux[0]); // CÓDIGO ENTREGADOR
				r.setNomeEntregador((String) aux[1]); // NOME ENTREGADOR
				r.setTotalEntregaEntregador((Long)aux[2]); // O CAMPO AGREGADO NO SQL
				entregaEntregador.add(r);  	     
			}					
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return entregaEntregador;
	}
	
	//APENAS UMA LISTA COM OS NOMES DO ENTREGADORES ATIVOS
/*	@SuppressWarnings("unchecked")
	public List<String> listarNomeEntregador(Date data_inicial_mes, Date data_final_mes) {
	    Session sessao = HibernateUtil.getSessionFactory().openSession();
	    List<String> nomeEntregador = new ArrayList<>();

	    try {
	        String hql = "SELECT entregador.codigo, entregador.nome " +
	                     "FROM Entrega entrega " +
	                     "JOIN entrega.entregador entregador " +
	                     "WHERE entregador.status = true " +
	                     "AND entrega.data_lancamento BETWEEN :data_inicial_mes AND :data_final_mes";

	        Query consulta = sessao.createQuery(hql);
	        consulta.setParameter("data_inicial_mes", data_inicial_mes);
	        consulta.setParameter("data_final_mes", data_final_mes);

	        // Processar o resultado como uma lista de Object[]
	        List<Object[]> resultados = consulta.list();

	        for (Object[] resultado : resultados) {
	            Entregador entregador = new Entregador();
//	            entregador.setCodigo((Long) resultado[0]); // Campo "codigo"
	            entregador.setNome((String) resultado[1]); // Campo "nome"
	            nomeEntregador.add(entregador);
	        }
	    } catch (RuntimeException ex) {
	        throw ex;
	    } finally {
	        sessao.close();
	    }

	    return nomeEntregador;
	}*/
	
	//DADOS DO GRÁFICO DE ENTREGA AGRUPADO POR DIA
	@SuppressWarnings("unchecked")
	public List<Entrega> totalEntregaDia(Integer entregador, Date data_inicial_mes, Date data_final_mes) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();

		List<Entrega> entregaDia = new ArrayList<Entrega>();		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT entrega.data_lancamento, COUNT(entrega.codigo) AS total FROM Entrega entrega WHERE entrega.entregador.codigo = :entregador AND entrega.status = 'REALIZADA' AND (entrega.data_lancamento BETWEEN :data_inicial_mes AND :data_final_mes) GROUP BY entrega.data_lancamento ORDER BY entrega.data_lancamento");
				
			Query consulta = sessao.createQuery(sql.toString());
			consulta.setInteger("entregador", entregador);
			consulta.setDate("data_inicial_mes", data_inicial_mes);
			consulta.setDate("data_final_mes", data_final_mes);
					
			List<Object[]> objetos = consulta.list(); 
			for (Object[] aux: objetos) {
				Entrega r = new Entrega();
				//Objeto que sualistaModel recebe, vamos chamar de x
				r.setData_lancamento((Date)aux[0]); // DATA DA LANCAMENTO
				r.setTotalEntregaEntregador((Long)aux[1]); // O CAMPO AGREGADO NO SQL
				entregaDia.add(r);     
			}	
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return entregaDia;
	}

}
