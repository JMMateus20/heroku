package com.eventify.webevents.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutenticacionDTO {

	@NotBlank(message = "debe llenar el campo email")
	private String email;
	
	@NotBlank(message = "debe llenar el campo contraseña")
	private String contrasena;
}
