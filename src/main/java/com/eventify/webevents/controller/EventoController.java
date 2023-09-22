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

import com.eventify.webevents.dto.EventoDTO;
import com.eventify.webevents.dto.FiltroEventoDTO;
import com.eventify.webevents.dto.FiltroPorRangoDTO;
import com.eventify.webevents.service.EventoService;
import com.eventify.webevents.service.ValidacionService;

import jakarta.validation.Valid;

@RestController
public class EventoController {

	@Autowired
	private EventoService eventoService;
	
	@Autowired
	private ValidacionService validacionService;
	
	
	@RequestMapping(value = "eventos", method = RequestMethod.POST)
	public ResponseEntity<?> insertar(@Valid @RequestBody EventoDTO eventoDTO, BindingResult resultado){
		if (resultado.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, validacionService.formatarMensaje(resultado));
		}
		return eventoService.insertar(eventoDTO);
		
	}
	
	@RequestMapping(value = "eventos/asistentes", method = RequestMethod.PUT)
	public ResponseEntity<?> agregarAsistentes(@RequestParam(name = "eventoID", required = true) Long eventoID, @RequestParam(name = "asistenteID", required = true) Long asistenteID){
		return eventoService.agregarAsistente(eventoID, asistenteID);
	}
	
	@RequestMapping(value = "eventos/asistentes/{eventoID}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarAsistentes(@PathVariable Long eventoID){
		return eventoService.consultarAsistentes(eventoID);
	}
	
	@RequestMapping(value = "eventos/asistentes", method = RequestMethod.DELETE)
	public ResponseEntity<?> eliminarAsistente(@RequestParam(name = "eventoID", required = true) Long eventoID, @RequestParam(name = "asistenteID", required = true) Long asistenteID){
		return eventoService.eliminarAsistente(eventoID, asistenteID);
	}
	
	@RequestMapping(value = "eventos/filtro", method = RequestMethod.GET)
	public ResponseEntity<?> filtrarPorFechaOrNombre(@RequestBody FiltroEventoDTO filtro){
		return eventoService.filtrarPorFechaONombre(filtro);
	}
	
	@RequestMapping(value = "eventos/filtro/rango", method = RequestMethod.GET)
	public ResponseEntity<?> filtrarPorRangoDeFecha(@RequestBody FiltroPorRangoDTO filtro){
		return eventoService.filtrarPorRangoFecha(filtro);
	}
}
