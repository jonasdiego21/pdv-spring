package br.com.jdrmservices.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.jdrmservices.exception.GlobalException;
import br.com.jdrmservices.model.Usuario;
import br.com.jdrmservices.repository.Usuarios;
import br.com.jdrmservices.util.Constants;

@Service
public class UsuarioService {

	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public void cadastrar(Usuario usuario) {
		
		Optional<Usuario> emailOptional = usuarios.findByEmail(usuario.getEmail());
		
		if (usuario.isNovo() && emailOptional.isPresent()) {
			throw new GlobalException(Constants.INFORMACOES_JA_CADASTRADAS);
		}
		
		usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
		
		usuarios.saveAndFlush(usuario);	
	}
	
	@Transactional
	public void excluir(Usuario usuario) {
		usuarios.delete(usuario);
	}
}