package com.eventify.webevents.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.eventify.webevents.dto.AutenticacionDTO;
import com.eventify.webevents.dto.JWTTokenDTO;
import com.eventify.webevents.modelo.Usuario;
import com.eventify.webevents.service.TokenService;
import com.eventify.webevents.service.ValidacionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacionController {
	
	@Autowired
	private AuthenticationManager am;
	
	@Autowired
	private TokenService ts;
	
	@Autowired
	private ValidacionService validacionService;
	
	@PostMapping
	public ResponseEntity<?> autenticarUsuario(@Valid @RequestBody AutenticacionDTO datos, BindingResult resultado){
		if (resultado.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, validacionService.formatarMensaje(resultado));
		}
		try {
			Authentication token=new UsernamePasswordAuthenticationToken(datos.getEmail(), datos.getContrasena());
			var usuarioAutenticado=am.authenticate(token);
			var JWTToken=ts.generarToken((Usuario) usuarioAutenticado.getPrincipal());
			return ResponseEntity.ok(new JWTTokenDTO(JWTToken));
		}catch(RuntimeException e) {
			return ResponseEntity.badRequest().body("credenciales incorrectas");
		}
		
	}

}
