package com.eventify.webevents.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eventify.webevents.dto.AsistenteDTOResponse;
import com.eventify.webevents.dto.EventoDTO;
import com.eventify.webevents.dto.EventoDTOResponse;
import com.eventify.webevents.dto.FiltroEventoDTO;
import com.eventify.webevents.dto.FiltroPorRangoDTO;
import com.eventify.webevents.exceptions.ParseExceptionManaged;
import com.eventify.webevents.modelo.Asistente;
import com.eventify.webevents.modelo.Evento;
import com.eventify.webevents.modelo.Usuario;
import com.eventify.webevents.repositorio.AsistenteRepository;
import com.eventify.webevents.repositorio.EventoRepository;
import com.eventify.webevents.repositorio.UsuarioRepository;

@Service
public class EventoServiceImpl implements EventoService{
	
	@Autowired
	private EventoRepository eventoRep;
	
	@Autowired
	private UsuarioRepository usuarioRep;
	
	@Autowired
	private AsistenteRepository asistenteRep;

	@Override
	public ResponseEntity<?> insertar(EventoDTO eventoDTO) {
		Optional<Evento> eventoYaExistente=eventoRep.findByNombre(eventoDTO.getNombre());
		if (eventoYaExistente.isPresent()) {
			Evento eventoBD=eventoYaExistente.get();
			EventoDTOResponse eventoExistente=new EventoDTOResponse(eventoBD.getId(), eventoBD.getNombre(), eventoBD.getDescripcion(), eventoBD.getFecha(), eventoBD.getLugar());
			return ResponseEntity.badRequest().body(eventoExistente);
		}
		java.sql.Date fechaSQL=null;
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		try {
			java.util.Date fechaParse=sdf.parse(eventoDTO.getFecha());
			fechaSQL=new java.sql.Date(fechaParse.getTime());
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		
		Evento evento=new Evento(eventoDTO.getNombre(), eventoDTO.getDescripcion(), fechaSQL, eventoDTO.getLugar());
		eventoRep.save(evento);
		eventoDTO.setId(evento.getId());
		return ResponseEntity.ok(eventoDTO);
	}

	@Override
	public ResponseEntity<String> agregarAsistente(Long eventoID, Long asistenteID) {
		Optional<Asistente> yaExistente=asistenteRep.buscarYaExistente(eventoID, asistenteID);
		if (yaExistente.isPresent()) {
			return ResponseEntity.badRequest().body("este asistente ya está registrado en este evento");
		}
		Optional<Usuario> usuarioFound=usuarioRep.findById(asistenteID);
		if (!usuarioFound.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("usuario no encontrado");
		}
		Optional<Evento> eventoFound=eventoRep.findById(eventoID);
		if (!eventoFound.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("evento no encontrado o no existente");
		}
		Evento eventoBD=eventoFound.get();
		Usuario usuarioBD=usuarioFound.get();
		eventoBD.agregarAsistente(usuarioBD);
		usuarioBD.getEventos().add(eventoBD);
		eventoRep.save(eventoBD);
		
		return ResponseEntity.ok("asistente agregado");
	}

	@Override
	public ResponseEntity<?> consultarAsistentes(Long eventoID) {
		Optional<Evento> eventoFound=eventoRep.findById(eventoID);
		if (!eventoFound.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("evento no encontrado o no existente");
		}
		Evento eventoBD=eventoFound.get();
		List<AsistenteDTOResponse> asistentes=new ArrayList<>();
		for (Usuario asistente:eventoBD.getAsistentes()) {
			AsistenteDTOResponse assistant=new AsistenteDTOResponse(asistente.getId(), asistente.getNombre(), asistente.getEmail());
			asistentes.add(assistant);
		}
		return ResponseEntity.ok(asistentes);
	}

	@Override
	public ResponseEntity<?> eliminarAsistente(Long eventoID, Long asistenteID) {
		if (eventoID==null || asistenteID==null) {
			return ResponseEntity.badRequest().body("debe mencionar cuál asistente se eliminará de cuál evento");
		}
		Optional<Asistente> asistenteFound=asistenteRep.buscarYaExistente(eventoID, asistenteID);
		if (!asistenteFound.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("asistente no encontrado");
		}
		Evento eventoBD=eventoRep.findById(eventoID).get();
		Usuario asistenteAEliminar=usuarioRep.findById(asistenteID).get();
		eventoBD.getAsistentes().remove(asistenteAEliminar);
		eventoRep.save(eventoBD);
		List<AsistenteDTOResponse> respuesta=new ArrayList<>();
		for (Usuario asistente:eventoBD.getAsistentes()) {
			AsistenteDTOResponse asist=new AsistenteDTOResponse(asistente.getId(), asistente.getNombre(), asistente.getEmail());
			respuesta.add(asist);
			
		}
		return ResponseEntity.ok(respuesta);
	}

	@Override
	public ResponseEntity<?> filtrarPorFechaONombre(FiltroEventoDTO filtro) {
		java.sql.Date fechaSQL=null;
		if (filtro.getFecha()!=null) {
			SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
			try {
				java.util.Date fechaParse=sdf.parse(filtro.getFecha());
				fechaSQL=new java.sql.Date(fechaParse.getTime());
			} catch (ParseException e) {
				throw new ParseExceptionManaged("debe digitar la fecha en el formato dd/MM/yyyy");
			}
		}

		List<Evento> eventosFiltrados=eventoRep.findByFechaOrNombre(fechaSQL, filtro.getNombre());
		if (eventosFiltrados.isEmpty()) {
			return ResponseEntity.badRequest().body("ningún evento fue encontrado");
		}
		List<EventoDTOResponse> eventoFilterResult=new ArrayList<>();
		for (Evento e:eventosFiltrados) {
			EventoDTOResponse evento=new EventoDTOResponse(e.getId(), e.getNombre(), e.getDescripcion(), e.getFecha(), e.getLugar());
			eventoFilterResult.add(evento);
		}
		return ResponseEntity.ok(eventoFilterResult);
	}

	@Override
	public ResponseEntity<?> filtrarPorRangoFecha(FiltroPorRangoDTO filtro) {
		java.sql.Date fechaMinima=null;
		java.sql.Date fechaMaxima=null;
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		try {
			java.util.Date fechaMinimaParse=sdf.parse(filtro.getFechaMinima());
			java.util.Date fechaMaximaParse=sdf.parse(filtro.getFechaMaxima());
			fechaMinima=new java.sql.Date(fechaMinimaParse.getTime());
			fechaMaxima=new java.sql.Date(fechaMaximaParse.getTime());
		}catch(ParseException e) {
			throw new ParseExceptionManaged("digite ambas fechas en el formato dd/MM/yyyy");
		}
		if (fechaMinima.compareTo(fechaMaxima)>0) {
			return ResponseEntity.badRequest().body("la fecha Minima debe ser anterior a la fecha maxima");
		}
		List<Evento> eventosFiltrados=eventoRep.findByFechaBetween(fechaMinima, fechaMaxima);
		if (eventosFiltrados.isEmpty()) {
			return ResponseEntity.badRequest().body("no se encontraron resultados para este filtro");
		}
		List<EventoDTOResponse> respuesta=new ArrayList<>();
		for (Evento evento:eventosFiltrados) {
			EventoDTOResponse resp=new EventoDTOResponse(evento.getId(), evento.getNombre(), evento.getDescripcion(), evento.getFecha(), evento.getLugar());
			respuesta.add(resp);
		}
		return ResponseEntity.ok(respuesta);
	}

}
