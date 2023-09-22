package com.eventify.webevents.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import com.eventify.webevents.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Optional<Usuario> findByEmail(String email);
	
	@Query("SELECT U FROM Usuario AS U WHERE U.email=:email")
	UserDetails buscarPorEmail(@Param(value = "email") String email);
	
}
