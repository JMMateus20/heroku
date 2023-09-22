package com.eventify.webevents.service;

import org.springframework.http.ResponseEntity;

import com.eventify.webevents.dto.UsuarioDTO;

public interface UsuarioService {

	
	ResponseEntity<?> insertar(UsuarioDTO usuarioDTO);
	
	
	ResponseEntity<?> buscarPorID(Long id);
	
	
	ResponseEntity<?> buscarPorCorreo(String correo);
	
	
	ResponseEntity<?> consultarEventos(Long id);
}
