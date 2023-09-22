package com.eventify.webevents.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.eventify.webevents.ValidationAdvice.MensajeErrores;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class ValidacionServiceImpl implements ValidacionService{

	@Override
	public String formatarMensaje(BindingResult resultado) {
		List<Map<String, String>> errores=resultado.getFieldErrors()
				.stream().map(err->{
					Map<String, String> error=new HashMap<>();
					error.put(err.getField(), err.getDefaultMessage());
					return error;
				}).collect(Collectors.toList());
		MensajeErrores mensajeError=MensajeErrores.builder()
				.code("01")
				.mensajes(errores).build();
		ObjectMapper mapper = new ObjectMapper();
		String jsonString="";
		try {
			jsonString=mapper.writeValueAsString(mensajeError.getMensajes().get(0));
		}catch(JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonString;
	}

}
