package br.com.aformula.dao;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.aformula.domain.MensagemContato;
import br.com.aformula.util.HibernateUtil;

public class MensagemContatoDAO {
	public void salvar(MensagemContato mensagemContato){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(mensagemContato);
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
	public List<MensagemContato> listar(Long codigoUsuario){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<MensagemContato> mensagemContatos = null;
		
		try{
			Query consulta = sessao.getNamedQuery("MensagemContato.listar");
			consulta.setLong("codigoUsuario", codigoUsuario);
			mensagemContatos = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return mensagemContatos;
	}
	
	@SuppressWarnings("unchecked")
	public List<MensagemContato> listarAtivo(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<MensagemContato> mensagemContatos = null;
		
		try{
			Query consulta = sessao.getNamedQuery("MensagemContato.listarAtivo");
			mensagemContatos = consulta.list();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return mensagemContatos;
	}
	
	public MensagemContato buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		MensagemContato mensagemContato = null;
		
		try{
			Query consulta = sessao.getNamedQuery("MensagemContato.buscarPorCodigo");
			consulta.setLong("codigo", codigo);
			mensagemContato = (MensagemContato) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return mensagemContato;
	}
	
	public void excluir(MensagemContato mensagemContato){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(mensagemContato);
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
	
	public void editar(MensagemContato mensagemContato){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(mensagemContato);
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
	
	
	public MensagemContato buscarContatoPorCodigo(Long codigoUsuario, Long codigoContato){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		MensagemContato mensagemContato = null;
		
		try{
			Query consulta = sessao.getNamedQuery("MensagemContato.buscarContatoPorCodigo");
			consulta.setLong("codigoUsuario", codigoUsuario);
			consulta.setLong("codigoContato", codigoContato);			
			mensagemContato = (MensagemContato) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return mensagemContato;
	}
	
	//BUSCA O CONTATO POR CODIGO DELE E PELO GRUPO DO CONTATO
	public MensagemContato buscarContatoPorCodigoGrupo(Long codigoUsuario, Long codigoContato, Integer codigoGrupo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		MensagemContato mensagemContato = null;
		
		try{
			Query consulta = sessao.getNamedQuery("MensagemContato.buscarContatoPorCodigoGrupo");
			consulta.setLong("codigoUsuario", codigoUsuario);
			consulta.setLong("codigoContato", codigoContato);
			consulta.setLong("codigoGrupo", codigoGrupo);
			mensagemContato = (MensagemContato) consulta.uniqueResult();
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
		return mensagemContato;
	}
	
	//BUSCA O CONTATO POR CODIGO DELE E PELO GRUPO DO CONTATO
	public void zeraMensamgeVista(Long codigoUsuario, Integer codigoGrupo){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE MensagemContato mensagemContato SET mensagemContato.mensagemNaoLida = 0 WHERE mensagemContato.usuario.codigo = :codigoUsuario AND mensagemContato.grupo.codigo = :codigoGrupo");			
			Query consulta = sessao.createQuery(sql.toString());			
			consulta.setLong("codigoUsuario", codigoUsuario);
			consulta.setInteger("codigoGrupo", codigoGrupo);		
			
			//AQUI EU PODERIA COLOCAR APENAS O consulta.executeUpdate();
			int linhasAfetadas = consulta.executeUpdate();
		    System.out.println("OLHO NO LANCE: " + linhasAfetadas);

		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
	}
	
	//SOMA A QUANTIDADE DE MENSAGENS NÃO LIDAS
	public Long somaMensagensNaoLida(Long codigoFuncionario) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Long quantidade = 0L;
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SUM(mensagemContato.mensagemNaoLida) AS quantidade FROM MensagemContato mensagemContato WHERE mensagemContato.usuario.codigo = :codigoFuncionario");
			
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
	
	//EXCLUIR O MENSAGEM CONTATO DE USUARIOS QUE ESTEJAM EM GRUPO
	public void excluiMensagemContatoGrupo(Long codigoFuncionario, Integer codigoGrupo) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
			
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM MensagemContato mensgemContato WHERE (mensgemContato.usuario.codigo = :codigoFuncionario OR mensgemContato.contato.codigo = :codigoFuncionario) AND mensgemContato.grupo.codigo = :codigoGrupo");
				
			Query consulta = sessao.createQuery(sql.toString());
				
			consulta.setParameter("codigoFuncionario", codigoFuncionario);
			consulta.setParameter("codigoGrupo", codigoGrupo);
			int linhasAfetadas = consulta.executeUpdate();
			System.out.println("VEJA: " + linhasAfetadas);
		
		}catch(RuntimeException ex){
			throw ex;
		}finally{
			sessao.close();
		}
	}
}
