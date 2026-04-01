package br.com.aformula.dao;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.aformula.domain.GrupoContato;
import br.com.aformula.util.HibernateUtil;

public class GrupoContatoDAO {
	public void salvar(GrupoContato grupoContato){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(grupoContato);
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
	public List<GrupoContato> listar(Integer codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<GrupoContato> grupoContatos = null;
		
		try{
			Query consulta = sessao.getNamedQuery("GrupoContato.listar");
			consulta.setInteger("codigo", codigo);
			grupoContatos = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return grupoContatos;
	}
	
/*	public GrupoContato buscarPorCodigo(Integer codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		GrupoContato grupoContato = null;
		try{
			Query consulta = sessao.getNamedQuery("GrupoContato.buscarPorCodigo");
			consulta.setInteger("codigo", codigo);
			grupoContato = (GrupoContato) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}		
		return grupoContato;
	}*/
	
	public void excluir(GrupoContato grupoContato){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(grupoContato);
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
	
	public void editar(GrupoContato grupoContato){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(grupoContato);
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
	
	//BUSCAR OS GRUPOS DE UM FUNCIOARIO PARA LISTAR NA TELA
	@SuppressWarnings("unchecked")
	public List<GrupoContato> listarGruposFunc(Integer fun_codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<GrupoContato> grupoContatos = null;
		try{
			Query consulta = sessao.getNamedQuery("GrupoContato.buscarGrupoFunc");
			consulta.setInteger("fun_codigo", fun_codigo);
			grupoContatos = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}		
		return grupoContatos;
	}
	
	//BUSCAR TODOS OS FUNCIONÁRIO POR GRUPO PASSADO O GRUPO COM OPARÂMETRO
	@SuppressWarnings("unchecked")
	public List<GrupoContato> buscarGrupoPorCodigo(Integer codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<GrupoContato> grupoContato = null;
				
		try{
			Query consulta = sessao.getNamedQuery("GrupoContato.buscarPorCodigo");
			consulta.setInteger("codigo", codigo);
			grupoContato = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
				
		return grupoContato;
	}
	
	
	//SOMA A QUANTIDADE DE MENSAGENS NÃO LIDAS DOS GRUPOS
	public Long somaMensagensNaoLida(Long codigoFuncionario) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Long quantidade = 0L;
			
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SUM(grupoContato.mensagemNaoLida) AS quantidade FROM GrupoContato grupoContato WHERE grupoContato.usuario.codigo = :codigoFuncionario");
				
			Query consulta = sessao.createQuery(sql.toString());
				
			consulta.setLong("codigoFuncionario", codigoFuncionario);
			quantidade = (Long) consulta.uniqueResult();
				
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return quantidade;
	}
	
	
	//BUSCA O CONTATO POR CODIGO DELE E PELO GRUPO DO CONTATO
	public GrupoContato buscarContatoPorCodigoGrupo(Long codigoContato, Integer codigoGrupo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		GrupoContato grupoContato = null;
//		System.out.println("RESULTADO ENTRADA ");	
		try{
			Query consulta = sessao.getNamedQuery("GrupoContato.buscarContatoPorCodigoGrupo");
			consulta.setLong("codigoContato", codigoContato);
			consulta.setLong("codigoGrupo", codigoGrupo);
			grupoContato = (GrupoContato) consulta.uniqueResult();
			
//			System.out.println("RESULTADO OK: " + grupoContato);
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return grupoContato;
	}
}
