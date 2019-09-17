package br.com.jdrmservices.repository.helper.usuario;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.jdrmservices.model.Usuario;
import br.com.jdrmservices.repository.filter.UsuarioFilter;

public interface UsuariosQueries {
	public List<String> permissoes(Usuario usuario);
	public Page<Usuario> filtrar(UsuarioFilter filtro, Pageable pageable);
}