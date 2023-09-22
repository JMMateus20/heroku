package com.eventify.webevents.service;

import org.springframework.http.ResponseEntity;

import com.eventify.webevents.dto.EventoDTO;
import com.eventify.webevents.dto.FiltroEventoDTO;
import com.eventify.webevents.dto.FiltroPorRangoDTO;

public interface EventoService {

	ResponseEntity<?> insertar(EventoDTO eventoDTO);
	
	
	ResponseEntity<String> agregarAsistente(Long eventoID, Long asistenteID);
	
	
	ResponseEntity<?> consultarAsistentes(Long eventoID);
	
	
	ResponseEntity<?> eliminarAsistente(Long eventoID, Long asistenteID);
	
	
	ResponseEntity<?> filtrarPorFechaONombre(FiltroEventoDTO filtro);
	
	
	ResponseEntity<?> filtrarPorRangoFecha(FiltroPorRangoDTO filtro);
}
