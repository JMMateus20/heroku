package com.eventify.webevents.dto;

import java.util.ArrayList;
import java.util.List;

import com.eventify.webevents.modelo.Evento;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
	
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;

	@NotNull(message = "el nombre no puede ser nulo")
	@NotEmpty(message = "el nombre no puede estar vacío")
	private String nombre; 
	
	@Email(message = "este campo debe ser un email válido")
	@NotNull(message = "el email no puede ser nulo")
	@NotEmpty(message = "el email no puede estar vacío")
	private String email;   //not null

	@JsonProperty(access = Access.WRITE_ONLY)
	@NotNull(message = "Debe digitar un password")
	@NotEmpty(message = "Debe digitar un password")
	private String contrasena;

	public UsuarioDTO(Long id,
			@NotNull(message = "el nombre no puede ser nulo") @NotEmpty(message = "el nombre no puede estar vacío") String nombre,
			@Email(message = "este campo debe ser un email válido") @NotNull(message = "el email no puede ser nulo") @NotEmpty(message = "el email no puede estar vacío") String email) {
		this.id = id;
		this.nombre = nombre;
		this.email = email;
	}

	public UsuarioDTO(
			@NotNull(message = "el nombre no puede ser nulo") @NotEmpty(message = "el nombre no puede estar vacío") String nombre,
			@Email(message = "este campo debe ser un email válido") @NotNull(message = "el email no puede ser nulo") @NotEmpty(message = "el email no puede estar vacío") String email,
			@NotNull(message = "Debe digitar un password") @NotEmpty(message = "Debe digitar un password") String contrasena) {
		this.nombre = nombre;
		this.email = email;
		this.contrasena = contrasena;
	}
	
	
	
	
}
