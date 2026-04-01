package br.com.aformula.dao;
import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.aformula.domain.ConfiguracoesLog;
import br.com.aformula.util.HibernateUtil;

public class ConfiguracoesLogDAO {
	
	public void salvar(ConfiguracoesLog configuracoesLog){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(configuracoesLog);
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
	
	public void logConfiguracoes(Long usuario, Date data_alteracao, String maquina, String ip, Long limite_diario_sache, Long limite_diario_capsula, Long limite_diario_shake, Long limite_diario_envase, Date bloquear_lancamento_producao, Date bloquear_lancamento_producao2) {
	Session sessao = HibernateUtil.getSessionFactory().openSession();
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO tbl_log_configuracoes(codigo, usuario, data_alteracao, maquina, ip, limite_diario_sache, limite_diario_capsula, limite_diario_shake, limite_diario_envase, bloquear_lancamento_producao, bloquear_lancamento_producao2) SELECT (nextval('sequence_log_configuracoes'), usuario, data_alteracao, maquina, ip, limite_diario_sache, limite_diario_capsula, limite_diario_shake, limite_diario_envase, bloquear_lancamento_producao, bloquear_lancamento_producao2)");
			

			Query consulta = sessao.createQuery(sql.toString());
			
//            int result = consulta.executeUpdate();
			
			consulta.setLong("usuario", usuario);
			consulta.setDate("data_alteracao", data_alteracao);
			consulta.setText("maquina", maquina);
			consulta.setText("ip", ip);
			consulta.setLong("limite_diario_sache", limite_diario_sache);
			consulta.setLong("limite_diario_capsula", limite_diario_capsula);
			consulta.setLong("limite_diario_shake", limite_diario_shake);
			consulta.setLong("limite_diario_envase", limite_diario_envase);
			consulta.setDate("bloquear_lancamento_producao", bloquear_lancamento_producao);
			consulta.setDate("bloquear_lancamento_producao2", bloquear_lancamento_producao2);	

		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
	}

}
