package com.eventify.webevents.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsistenteDTOResponse {
	private Long id;

	
	private String nombre; 
	

	private String email;
}
