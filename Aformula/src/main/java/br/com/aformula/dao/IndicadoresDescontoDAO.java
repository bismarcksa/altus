package br.com.aformula.dao;

import java.util.Collections;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.aformula.domain.IndicadoresDesconto;
import br.com.aformula.util.HibernateUtil;

public class IndicadoresDescontoDAO {
	
	public float mediaLoja67 = 0, mediaLoja68  = 0, mediaLoja70  = 0, mediaGrupo  = 0;
	
	public void salvar(IndicadoresDesconto indicadoresDesconto){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(indicadoresDesconto);
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
	public List<IndicadoresDesconto> listar12Ultimos(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<IndicadoresDesconto> indicadoresDescontos = null;
		
		try{
			Query consulta = sessao.getNamedQuery("IndicadoresDesconto.listar");
			//RETORNA 12 PRIMEIROS REGISTROS
			consulta.setMaxResults(12);
			indicadoresDescontos = consulta.list();
			//INVERT A LISTA
			Collections.reverse(indicadoresDescontos);
			
			Integer i;
			
			for (i = 0; i < indicadoresDescontos.size(); i++) {
				mediaLoja67 += indicadoresDescontos.get(i).getFilial01().floatValue();
				mediaLoja68 += indicadoresDescontos.get(i).getFilial02().floatValue();
				mediaLoja70 += indicadoresDescontos.get(i).getFilial03().floatValue();
				mediaGrupo += indicadoresDescontos.get(i).getGrupo().floatValue();
				
			}
			
			mediaLoja67 = mediaLoja67/indicadoresDescontos.size();
			mediaLoja68 = mediaLoja68/indicadoresDescontos.size();
			mediaLoja70 = mediaLoja70/indicadoresDescontos.size();
			mediaGrupo = mediaGrupo/indicadoresDescontos.size();
			
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return indicadoresDescontos;
	}
	
	@SuppressWarnings("unchecked")
	public List<IndicadoresDesconto> listar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<IndicadoresDesconto> indicadoresDescontos = null;
		
		try{
			Query consulta = sessao.getNamedQuery("IndicadoresDesconto.listarDesc");
			indicadoresDescontos = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return indicadoresDescontos;
	}
	
	public IndicadoresDesconto buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		IndicadoresDesconto indicadoresDesconto = null;
		
		try{
			Query consulta = sessao.getNamedQuery("IndicadoresDesconto.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			indicadoresDesconto = (IndicadoresDesconto) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		
		return indicadoresDesconto;
	}
	
	public void excluir(IndicadoresDesconto indicadoresDesconto){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(indicadoresDesconto);
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
	
	public void editar(IndicadoresDesconto indicadoresDesconto){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(indicadoresDesconto);
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
	
	//VERIFICA SE EXISTE DESCONTO CADASTRADO
	public IndicadoresDesconto descontoExiste(Integer ano, String mes){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		IndicadoresDesconto indicadoresDesconto = null;
				
		try{
			Query consulta = sessao.getNamedQuery("IndicadoresDesconto.descontoExiste");
			consulta.setInteger("ano", ano);
			consulta.setText("mes", mes);
			indicadoresDesconto = (IndicadoresDesconto) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
				
		return indicadoresDesconto;
	}
}
