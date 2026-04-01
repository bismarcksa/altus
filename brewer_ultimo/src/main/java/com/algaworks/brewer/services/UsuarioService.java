package com.algaworks.brewer.services;

import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.repository.UsuarioRepository;
import com.algaworks.brewer.services.exception.EmailJaCadastradoException;
import com.algaworks.brewer.services.exception.SenhaUsuarioObrigatoriaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarios;

    @Autowired
    private PasswordEncoder passwordEncoder;

	@SuppressWarnings("deprecation")
	@Transactional
    public void salvar(Usuario usuario) {
        
    	Optional<Usuario> usuarioExistente = usuarios.findByEmail(usuario.getEmail());

        if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
        	throw new EmailJaCadastradoException("Email já cadastrado");
        }
        
        if(usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())) {
        	throw new SenhaUsuarioObrigatoriaException("Senha é obrigatória para novo usuário");
        }
        
        if(usuario.isNovo()) {
        	usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
        	usuario.setConfirmacaoSenha(usuario.getSenha());
        }
        
        verificarUsuarioExistente(usuario, usuarioExistente);
        validarSenhaObrigatoriaUsuarioNovo(usuario);
        validarSenha(usuario, usuarioExistente);
        validarStatus(usuario, usuarioExistente);

        usuario.setConfirmacaoSenha(usuario.getSenha());
        usuarios.save(usuario);
    }

    private void verificarUsuarioExistente(Usuario usuario, Optional<Usuario> usuarioExistente) {
        if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
            throw new EmailJaCadastradoException("Email já cadastrado");
        }
    }

    @SuppressWarnings("deprecation")
	private void validarSenhaObrigatoriaUsuarioNovo(Usuario usuario) {
        if (usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())) {
            throw new SenhaUsuarioObrigatoriaException("Senha é obrigatória");
        }
    }

    @SuppressWarnings("deprecation")
	private void validarSenha(Usuario usuario, Optional<Usuario> usuarioExistente) {
        if (usuario.isNovo() || !StringUtils.isEmpty(usuario.getSenha())) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        } else if(StringUtils.isEmpty(usuario.getSenha())) {
            usuario.setSenha(usuarioExistente.get().getSenha());
        }
    }

    private void validarStatus(Usuario usuario, Optional<Usuario> usuarioExistente) {
        if(usuario.isEdicao() && usuario.getAtivo() == null) {
            usuario.setAtivo(usuarioExistente.get().getAtivo());
        }
    }

   @Transactional
    public void alterarStatus(Long[] codigos, StatusUsuario statusUsuario) {
        statusUsuario.executar(codigos, usuarios);
    }
}
