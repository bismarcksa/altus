package br.com.aformula.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import br.com.aformula.domain.Funcionario;
import br.com.aformula.domain.Producao;
import br.com.aformula.util.HibernateUtil;

public class DashboardDAO {

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
	
	
	//DADOS DO GRÁFICO DE PRODUÇÃO (CONTRATOS) AGRUPADO POR DIA
	@SuppressWarnings("unchecked")
	public List<Producao> totalContratoDia(String tipo, Date data_inicial_mes, Date data_final_mes) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();

		// Aqui vai ser manipulado o objetos.
		// Cria suaListaModel
		List<Producao> contratoDia = new ArrayList<Producao>();		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT producao.data_dispensacao, COUNT(producao.codigo) AS total FROM Producao producao WHERE producao.tipo = :tipo AND producao.status = 'DISPENSADO' AND (producao.data_dispensacao BETWEEN :data_inicial_mes AND :data_final_mes) GROUP BY producao.data_dispensacao ORDER BY producao.data_dispensacao");
			
			Query consulta = sessao.createQuery(sql.toString());
			consulta.setText("tipo", tipo);
			consulta.setDate("data_inicial_mes", data_inicial_mes);
			consulta.setDate("data_final_mes", data_final_mes);
				
			List<Object[]> objetos = consulta.list(); 
			
			for (Object[] o : objetos) {

			     Object[] aux = o;
			     Producao r = new Producao();
			     //Objeto que sualistaModel recebe, vamos chamar de x

			     r.setData_dispensacao((Date)aux[0]); // DATA DA DISPENSAÇÃO
			     r.setTotalProducaoDia((Long)aux[1]); // O CAMPO AGREGADO NO SQL
			     contratoDia.add(r);     
			}	
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return contratoDia;
	}
	
	//DADOS DO GRÁFICO DE PRODUÇÃO (SACHÊ E CÁPSULA) AGRUPADO POR DIA
	@SuppressWarnings("unchecked")
	public List<Producao> totalProducaoDia(String tipo, Date data_inicial_mes, Date data_final_mes) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();

		// Aqui vai ser manipulado o objetos.
		// Cria suaListaModel
		List<Producao> producaoDia = new ArrayList<Producao>();		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT producao.data_dispensacao, SUM(producao.quantidade) AS total FROM Producao producao WHERE producao.tipo = :tipo AND producao.status = 'DISPENSADO' AND (producao.data_dispensacao BETWEEN :data_inicial_mes AND :data_final_mes) GROUP BY producao.data_dispensacao ORDER BY producao.data_dispensacao");	
			
			Query consulta = sessao.createQuery(sql.toString());
			consulta.setText("tipo", tipo);
			consulta.setDate("data_inicial_mes", data_inicial_mes);
			consulta.setDate("data_final_mes", data_final_mes);
					
			List<Object[]> objetos = consulta.list(); 
				
			for (Object[] o : objetos) {
				Object[] aux = o;
				Producao r = new Producao();
				//Objeto que sualistaModel recebe, vamos chamar de x

				r.setData_dispensacao((Date)aux[0]); // DATA DA DISPENSA��O
				r.setTotalProducaoDia((Long)aux[1]); // O CAMPO AGREGADO NO SQL
				producaoDia.add(r); 
			}	
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return producaoDia;
	}
	
	//RANKING DE SOLICIAÇÃO DE PRODUÇÃO
	@SuppressWarnings("unchecked")
	public List<Funcionario> rankingMes(Date data_inicial_mes, Date data_final_mes) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();

		List<Funcionario> funcionarioProducao = new ArrayList<Funcionario>();		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT fun.codigo, fun.nome, COUNT(pro.codigo) AS quantidade FROM Producao pro INNER JOIN pro.funcionario fun WHERE pro.status = 'DISPENSADO' AND (pro.data_dispensacao BETWEEN :data_inicial_mes AND :data_final_mes) GROUP BY fun.codigo, fun.nome ORDER BY quantidade DESC");

			Query consulta = sessao.createQuery(sql.toString());
			consulta.setDate("data_inicial_mes", data_inicial_mes);
			consulta.setDate("data_final_mes", data_final_mes);
				
			List<Object[]> objetos = consulta.list(); 
			
			for (Object[] o : objetos) {
			     Object[] aux = o;
			     Funcionario s = new Funcionario();
			     //Objeto que sualistaModel recebe, vamos chamar de x
			     s.setCodigo((Long)aux[0]); // CÓDIGO DO FUNCIONÁRIO
			     s.setNome((String)aux[1]); // NOME DO FUNCIONÁRIO
			     s.setQuantidadeProducao((Long)aux[2]); // O CAMPO AGREGADO NO SQL
			     funcionarioProducao.add(s);     
			}	
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return funcionarioProducao;
	}
	
}
