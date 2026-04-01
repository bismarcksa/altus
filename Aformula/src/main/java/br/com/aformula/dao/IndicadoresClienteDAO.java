package br.com.aformula.dao;

import java.util.Collections;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.aformula.domain.IndicadoresCliente;
import br.com.aformula.util.HibernateUtil;

public class IndicadoresClienteDAO {
	
	public float mediaLoja67 = 0, mediaLoja68  = 0, mediaLoja70  = 0, mediaGrupo  = 0;
	
	public void salvar(IndicadoresCliente indicadoresCliente){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(indicadoresCliente);
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
	public List<IndicadoresCliente> listar12Ultimos(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<IndicadoresCliente> indicadoresClientes = null;
		
		try{
			Query consulta = sessao.getNamedQuery("IndicadoresCliente.listar");
			//RETORNA 12 PRIMEIROS REGISTROS
			consulta.setMaxResults(12);
			indicadoresClientes = consulta.list();
			//INVERT A LISTA
			Collections.reverse(indicadoresClientes);
			
			Integer i;
			
			for (i = 0; i < indicadoresClientes.size(); i++) {
				mediaLoja67 += indicadoresClientes.get(i).getFilial01().floatValue();
				mediaLoja68 += indicadoresClientes.get(i).getFilial02().floatValue();
				mediaLoja70 += indicadoresClientes.get(i).getFilial03().floatValue();
				mediaGrupo += indicadoresClientes.get(i).getGrupo().floatValue();
				
			}
			
			mediaLoja67 = Math.round(mediaLoja67/indicadoresClientes.size());
			mediaLoja68 = Math.round(mediaLoja68/indicadoresClientes.size());
			mediaLoja70 = Math.round(mediaLoja70/indicadoresClientes.size());
			mediaGrupo = Math.round(mediaGrupo/indicadoresClientes.size());
			
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return indicadoresClientes;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<IndicadoresCliente> listar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<IndicadoresCliente> indicadoresClientes = null;
		
		try{
			Query consulta = sessao.getNamedQuery("IndicadoresCliente.listarDesc");
			indicadoresClientes = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return indicadoresClientes;
	}
	
	public IndicadoresCliente buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		IndicadoresCliente indicadoresCliente = null;
		
		try{
			Query consulta = sessao.getNamedQuery("IndicadoresCliente.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			indicadoresCliente = (IndicadoresCliente) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		
		return indicadoresCliente;
	}
	
	public void excluir(IndicadoresCliente indicadoresCliente){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(indicadoresCliente);
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
	
	public void editar(IndicadoresCliente indicadoresCliente){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(indicadoresCliente);
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
	
	//VERIFICA SE EXISTE CLIENTE CADASTRADA
	public IndicadoresCliente clienteExiste(Integer ano, String mes){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		IndicadoresCliente indicadoresCliente = null;
				
		try{
			Query consulta = sessao.getNamedQuery("IndicadoresCliente.clienteExiste");
			consulta.setInteger("ano", ano);
			consulta.setText("mes", mes);
			indicadoresCliente = (IndicadoresCliente) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
				
		return indicadoresCliente;
	}
}
