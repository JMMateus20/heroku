package com.eventify.webevents.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eventify.webevents.dto.EventoDTOResponse;
import com.eventify.webevents.dto.UsuarioDTO;
import com.eventify.webevents.modelo.Evento;
import com.eventify.webevents.modelo.Usuario;
import com.eventify.webevents.repositorio.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	
	@Autowired
	private UsuarioRepository usuarioRep;
	
	@Autowired
	private PasswordEncoder pe;

	@Override
	public ResponseEntity<?> insertar(UsuarioDTO usuarioDTO) {
		Optional<Usuario> usuarioYaExistente=usuarioRep.findByEmail(usuarioDTO.getEmail());
		if (usuarioYaExistente.isPresent()) {
			Usuario usuarioBD=usuarioYaExistente.get();
			UsuarioDTO usuarioExistente=new UsuarioDTO(usuarioBD.getId(), usuarioBD.getNombre(), usuarioBD.getEmail());
			return ResponseEntity.badRequest().body(usuarioExistente);
		}
		String passwordEncriptado=pe.encode(usuarioDTO.getContrasena());
		Usuario usuario=new Usuario(usuarioDTO.getNombre(), usuarioDTO.getEmail(), passwordEncriptado);
		usuarioRep.save(usuario);
		usuarioDTO.setId(usuario.getId());
		return ResponseEntity.ok(usuarioDTO);
	}

	@Override
	public ResponseEntity<?> buscarPorID(Long id) {
		Optional<Usuario> usuarioFound=usuarioRep.findById(id);
		if (!usuarioFound.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("usuario no encontrado");
		}
		Usuario usuarioBD=usuarioFound.get();
		UsuarioDTO usuarioResponse=new UsuarioDTO(usuarioBD.getId(), usuarioBD.getNombre(), usuarioBD.getEmail());
		return ResponseEntity.ok(usuarioResponse);
	}

	@Override
	public ResponseEntity<?> buscarPorCorreo(String correo) {
		Optional<Usuario> usuarioFound=usuarioRep.findByEmail(correo);
		if (!usuarioFound.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("usuario no encontrado");
		}
		Usuario usuarioBD=usuarioFound.get();
		UsuarioDTO usuarioResponse=new UsuarioDTO(usuarioBD.getId(), usuarioBD.getNombre(), usuarioBD.getEmail());
		return ResponseEntity.ok(usuarioResponse);
	}

	@Override
	public ResponseEntity<?> consultarEventos(Long id) {
		Optional<Usuario> usuarioFound=usuarioRep.findById(id);
		if (!usuarioFound.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("usuario no encontrado");
		}
		Usuario usuarioBD=usuarioFound.get();
		List<Evento> eventos=usuarioBD.getEventos();
		if (eventos.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("el usuario aún no tiene eventos para asistir");
		}
		List<EventoDTOResponse> respuesta=new ArrayList<>();
		for (Evento evento:eventos) {
			EventoDTOResponse eventoResponse=new EventoDTOResponse(evento.getId(), evento.getNombre(), evento.getDescripcion(), evento.getFecha(), evento.getLugar());
			respuesta.add(eventoResponse);
		}
		return ResponseEntity.ok(respuesta);
	}

}
