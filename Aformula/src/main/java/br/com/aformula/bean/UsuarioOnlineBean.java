package br.com.aformula.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@RequestScoped
@ManagedBean
public class UsuarioOnlineBean {
    
    public List<String> getUsuariosOnline() {
        List<String> lista = new ArrayList<>(UsuariosOnlineManager.getUsuariosOnline());
//        System.out.println("Lista usuários online: " + lista);
        return lista;
    }
}