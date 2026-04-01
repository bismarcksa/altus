package br.com.aformula.dao;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.aformula.domain.Mensagem;
import br.com.aformula.util.HibernateUtil;

public class MensagemDAO {
	public void salvar(Mensagem mensagem){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(mensagem);
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
	public List<String> listarContato(long codigoUsuario) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<String> funcMensagens = null;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT f.nome FROM Mensagem m JOIN m.destinatario f WHERE m.remetente.codigo = :codigoUsuario");
	
			Query consulta = sessao.createQuery(sql.toString());

			consulta.setLong("codigoUsuario", codigoUsuario);			
			funcMensagens = consulta.list();

			System.out.println("CONTATO REMETENTE: " + funcMensagens);
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return funcMensagens;
	}
	
	
	public Mensagem buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Mensagem mensagem = null;
		
		try{
			Query consulta = sessao.getNamedQuery("Mensagem.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			mensagem = (Mensagem) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return mensagem;
	}
	
	public void excluir(Mensagem mensagem){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(mensagem);
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
	
	public void editar(Mensagem mensagem){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(mensagem);
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
	
	//LISTAR AS MENSAGENS QUE SERÃO EXIBIDAS ENTRE REMETENTE E DESTINATÁRIO AO CLICAR NA CONVERSA
	@SuppressWarnings("unchecked")
	public List<Mensagem> listarMensagensFiltradas(long remetente, long destinatarioCodigo){
			
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Mensagem> mensagens = null;
		long destinatario;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT f.codigo FROM Funcionario f WHERE f.codigo = :destinatarioCodigo");

		Query consultaFunc = sessao.createQuery(sql.toString());		
		consultaFunc.setLong("destinatarioCodigo", destinatarioCodigo);					
		destinatario =  (long) consultaFunc.uniqueResult();
		
		try{
			Query consulta = sessao.getNamedQuery("Mensagem.listarMensagensFiltradas");
			consulta.setLong("remetente", remetente);
			consulta.setLong("destinatario", destinatario);
			mensagens = consulta.list();
			
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		
		return mensagens;
	}
	
	//LISTAR AS MENSAGENS QUE SERÃO EXIBIDAS ENTRE REMETENTE E O GRUPO
	@SuppressWarnings("unchecked")
	public List<Mensagem> listarMensagensFiltradasGrupo(int grupoCodigo){
				
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Mensagem> mensagens = null;
//		int grupo;
			
//		StringBuilder sql = new StringBuilder();
//		sql.append("SELECT g.codigo FROM Grupo g WHERE g.codigo = :grupoCodigo");

//		Query consultaFunc = sessao.createQuery(sql.toString());		
//		consultaFunc.setInteger("grupoCodigo", grupoCodigo);					
//		grupo = (int) consultaFunc.uniqueResult();
			
		try{
			Query consulta = sessao.getNamedQuery("Mensagem.listarMensagensFiltradasGrupo");
			consulta.setInteger("grupo", grupoCodigo);
			mensagens = consulta.list();
				
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
			
		return mensagens;
	}
}
