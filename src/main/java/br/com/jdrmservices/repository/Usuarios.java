package br.com.jdrmservices.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jdrmservices.model.Usuario;
import br.com.jdrmservices.repository.helper.usuario.UsuariosQueries;

@Repository
public interface Usuarios extends JpaRepository<Usuario, Long>, UsuariosQueries {
	Optional<Usuario> findByEmail(String email);
	Optional<Usuario> findByEmailIgnoreCaseAndAtivoTrue(String email);
	List<String> permissoes(Usuario usuario);
	List<Usuario> findAllByOrderByNomeAsc();
}