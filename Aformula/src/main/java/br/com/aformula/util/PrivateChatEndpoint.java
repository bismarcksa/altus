package br.com.aformula.util;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ServerEndpoint("/privateChat/{username}")
public class PrivateChatEndpoint {

    private static Set<Session> sessions = new HashSet<>();
    private static Map<String, Session> users = new HashMap<>();
    
    private String usuariosLogados;

    public String getUsuariosLogados() {
		return usuariosLogados;
	}

	public void setUsuariosLogados(String usuariosLogados) {
		this.usuariosLogados = usuariosLogados;
	}

	@OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        sessions.add(session);
        
//        System.out.println("SESSION: " + session);
//		System.out.println("USERNAME: " + username);
        
		 users.put(username.toString().replace("username=", ""), session);      
	     sendUserListUpdate();
	        
//	     System.out.println("SESSAO: " + users);
    }

    @OnClose
    public void onClose(Session session) { 
        try {
            synchronized (sessions) {
                sessions.remove(session);sessions.remove(session);
                users.entrySet().removeIf(entry -> entry.getValue().equals(session));
                sendUserListUpdate();
            }
//            System.out.println("Sessão removida: " + session.getId());
        } catch (Exception e) {
            System.err.println("Erro ao processar onClose: " + e.getMessage());
            e.printStackTrace();
        }
        
    }

    private void sendUserListUpdate() {
        // Enviar a lista de usuários conectados para todos os clientes       
    	String userList = String.join(", ", users.keySet());    	
        for (Session session : sessions) {
            try {
                session.getBasicRemote().sendText("USERS:" + userList); // Alterado de "Users:" para "USERS:"                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }  
        
        //REMOVENDO O username=
        usuariosLogados = users.keySet().toString().replaceAll("username=", "");  
//        System.out.println("LOGADOS: " + usuariosLogados);
    }
    
    public boolean estaOnline(String nomeContato) {
//		System.out.println("OUTRO TESTE: " + usuariosLogados);
		return usuariosLogados.contains(nomeContato.trim());
	}
    
    public static void enviarMensagemPrivada(String destinatario, String remetente, String mensagem) {
	    
//		System.out.println("USERS AGORA: " + users);
//		System.out.println("DEST: " + destinatario);
//		System.out.println("REMETENTE: " + remetente);
//		System.out.println("MENSAGEM: " + mensagem);
					
		Session sessionDestino = users.get(destinatario);

//	    System.out.println("OLHA: " + sessionDestino);
	    
	    if (sessionDestino != null && sessionDestino.isOpen()) {
	    	
//	    	System.out.println("ENTREI");
	        try {
	            sessionDestino.getBasicRemote().sendText("MSG:" + remetente + ":" + mensagem);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
//	    System.out.println("CHEGOU AQUI");
	}
}

