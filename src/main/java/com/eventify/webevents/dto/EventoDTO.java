package com.eventify.webevents.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoDTO {
	
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;

	@NotNull(message = "el nombre del evento no puede ser nulo")
	@NotEmpty(message = "el nombre del evento no puede estar vacío")
	private String nombre;   

	
	private String descripcion;
	
	@NotNull(message = "la fecha no puede ser nula")
	@NotEmpty(message = "la fecha no puede estar vacía")
	private String fecha;  //not null
	
	@NotNull(message = "debe indicar el lugar del evento")
	@NotEmpty(message = "debe indicar el lugar del evento")
	private String lugar;

	public EventoDTO(
			@NotNull(message = "el nombre del evento no puede ser nulo") @NotEmpty(message = "el nombre del evento no puede estar vacío") String nombre,
			String descripcion,
			@NotNull(message = "la fecha no puede ser nula") @NotEmpty(message = "la fecha no puede estar vacía") String fecha,
			@NotNull(message = "debe indicar el lugar del evento") @NotEmpty(message = "debe indicar el lugar del evento") String lugar) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.lugar = lugar;
	}
	
	
	
}
