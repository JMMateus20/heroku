package com.eventify.webevents.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eventify.webevents.modelo.Usuario;
import com.eventify.webevents.repositorio.UsuarioRepository;

@Service
public class AutenticacionService implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository usuarioRep;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return usuarioRep.buscarPorEmail(username);
	}

	

}
