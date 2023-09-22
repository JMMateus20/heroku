package com.eventify.webevents.ValidationAdvice;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class MensajeErrores {

	private String code;
	private List<Map<String, String>> mensajes;
}
