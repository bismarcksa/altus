package br.com.aformula.dao;

import java.util.Collections;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.aformula.domain.IndicadoresTicketMedio;
import br.com.aformula.util.HibernateUtil;

public class IndicadoresTicketMedioDAO {
	
	public float mediaLoja67 = 0, mediaLoja68  = 0, mediaLoja70  = 0, mediaGrupo  = 0;
	
	public void salvar(IndicadoresTicketMedio indicadoresTicketMedio){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(indicadoresTicketMedio);
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
	public List<IndicadoresTicketMedio> listar12Ultimos(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<IndicadoresTicketMedio> indicadoresTicketMedios = null;
		
		try{
			Query consulta = sessao.getNamedQuery("IndicadoresTicketMedio.listar");
			//RETORNA 12 PRIMEIROS REGISTROS
			consulta.setMaxResults(12);
			indicadoresTicketMedios = consulta.list();
			//INVERT A LISTA
			Collections.reverse(indicadoresTicketMedios);
			
			Integer i;
			
			for (i = 0; i < indicadoresTicketMedios.size(); i++) {
				mediaLoja67 += indicadoresTicketMedios.get(i).getFilial01().floatValue();
				mediaLoja68 += indicadoresTicketMedios.get(i).getFilial02().floatValue();
				mediaLoja70 += indicadoresTicketMedios.get(i).getFilial03().floatValue();
				mediaGrupo += indicadoresTicketMedios.get(i).getGrupo().floatValue();
				
			}
			
			mediaLoja67 = mediaLoja67/indicadoresTicketMedios.size();
			mediaLoja68 = mediaLoja68/indicadoresTicketMedios.size();
			mediaLoja70 = mediaLoja70/indicadoresTicketMedios.size();
			mediaGrupo = mediaGrupo/indicadoresTicketMedios.size();
			
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return indicadoresTicketMedios;
	}
	
	@SuppressWarnings("unchecked")
	public List<IndicadoresTicketMedio> listar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<IndicadoresTicketMedio> indicadoresTicketMedios = null;
		
		try{
			Query consulta = sessao.getNamedQuery("IndicadoresTicketMedio.listarDesc");
			indicadoresTicketMedios = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return indicadoresTicketMedios;
	}
	
	public IndicadoresTicketMedio buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		IndicadoresTicketMedio indicadoresTicketMedio = null;
		
		try{
			Query consulta = sessao.getNamedQuery("IndicadoresTicketMedio.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			indicadoresTicketMedio = (IndicadoresTicketMedio) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		
		return indicadoresTicketMedio;
	}
	
	public void excluir(IndicadoresTicketMedio indicadoresTicketMedio){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(indicadoresTicketMedio);
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
	
	public void editar(IndicadoresTicketMedio indicadoresTicketMedio){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(indicadoresTicketMedio);
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
	
	//VERIFICA SE EXISTE TICKET MÉDIO CADASTRADA
	public IndicadoresTicketMedio ticketMedioExiste(Integer ano, String mes){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		IndicadoresTicketMedio indicadoresTicketMedio = null;
				
		try{
			Query consulta = sessao.getNamedQuery("IndicadoresTicketMedio.ticketMedioExiste");
			consulta.setInteger("ano", ano);
			consulta.setText("mes", mes);
			indicadoresTicketMedio = (IndicadoresTicketMedio) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
				
		return indicadoresTicketMedio;
	}
}
