package br.com.aformula.bean;

import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class UsuariosOnlineManager {

    private static final Map<String, Instant> usuariosOnline = new ConcurrentHashMap<>();

    public static void adicionarOuAtualizarUsuario(String nome) {
        usuariosOnline.put(nome, Instant.now());
    }

    public static void removerUsuario(String nome) {
        usuariosOnline.remove(nome);
    }

    public static Set<String> getUsuariosOnline() {
        // Filtra somente usuários que deram heartbeat nos últimos 1 minutos
        Instant limite = Instant.now().minusSeconds(60);
        return usuariosOnline.entrySet().stream().filter(e -> e.getValue().isAfter(limite)).map(Map.Entry::getKey).collect(Collectors.toSet());
    }
}
