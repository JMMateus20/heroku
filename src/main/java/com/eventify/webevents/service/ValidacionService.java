package com.eventify.webevents.service;

import org.springframework.validation.BindingResult;

public interface ValidacionService {

	String formatarMensaje(BindingResult resultado);
}
