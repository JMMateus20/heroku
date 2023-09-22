package com.eventify.webevents.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.eventify.webevents.dto.UsuarioDTO;
import com.eventify.webevents.service.UsuarioService;
import com.eventify.webevents.service.ValidacionService;

import jakarta.validation.Valid;

@RestController
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ValidacionService validacionService;

	@RequestMapping(value = "usuarios", method = RequestMethod.POST)
	public ResponseEntity<?> insertar(@Valid @RequestBody UsuarioDTO usuarioDTO, BindingResult resultado){
		if (resultado.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, validacionService.formatarMensaje(resultado));
		}
		return usuarioService.insertar(usuarioDTO);
	}
	
	@RequestMapping(value = "usuarios/eventos/{id}", method = RequestMethod.GET)
	ResponseEntity<?> consultarEventos(@PathVariable Long id){
		return usuarioService.consultarEventos(id);
	}
}
