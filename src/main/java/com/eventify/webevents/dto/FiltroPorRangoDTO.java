package com.eventify.webevents.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FiltroPorRangoDTO {
	
	
	private String fechaMinima;
	private String fechaMaxima;

}
