package com.eventify.webevents.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoDTOResponse {

	private Long id;

	
	private String nombre;   

	
	private String descripcion;

	private Date fecha;  //not null
	

	private String lugar;
}
