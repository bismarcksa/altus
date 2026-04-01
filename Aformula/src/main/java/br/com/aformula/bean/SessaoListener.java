package br.com.aformula.bean;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessaoListener implements HttpSessionListener {

    private static final Set<String> usuariosOnline = ConcurrentHashMap.newKeySet();

    public static void adicionarUsuario(String nome) {
//    	System.out.println("OLHA O NOME: " + nome);
        usuariosOnline.add(nome);
//        System.out.println("LISTA " + usuariosOnline );
    }

    public static void removerUsuario(String nome) {
        usuariosOnline.remove(nome);
    }

    public static Set<String> getUsuariosOnline() {
        return usuariosOnline;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        String nome = (String) session.getAttribute("usuarioLogado");
        if (nome != null) {
            usuariosOnline.remove(nome);
        }
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // não precisa implementar nada aqui por enquanto
    }
}