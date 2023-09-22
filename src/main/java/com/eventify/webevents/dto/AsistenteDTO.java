package com.eventify.webevents.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsistenteDTO {


	@NotNull(message = "indicar a cuál evento asistirá")
	private Long eventoID;
	
	@NotNull(message = "indicar cuál usuario asistirá")
	private Long usuarioID;
}
