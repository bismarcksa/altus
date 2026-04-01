package com.algaworks.brewer.services;

import com.algaworks.brewer.repository.UsuarioRepository;

public enum StatusUsuario {

    ATIVAR{
        @Override
        public void executar(Long codigos[], UsuarioRepository usuarios){
            usuarios.findByCodigoIn(codigos).forEach(u-> u.setAtivo(true));
        }
    },
    DESATIVAR{
        @Override
        public void  executar(Long codigos[], UsuarioRepository usuarios){
            usuarios.findByCodigoIn(codigos).forEach(u->u.setAtivo(false));
        }
    };

    public abstract void executar(Long codigos[], UsuarioRepository usuarios);
}
