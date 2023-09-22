package com.eventify.webevents.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.eventify.webevents.modelo.Usuario;

@Service
public class TokenService {
	
	@Value("${api.security.secret}")
	private String apiSecret;
	
	
	public String generarToken(Usuario usuario) {
		try {
			Algorithm algorithm=Algorithm.HMAC256(apiSecret);
			return JWT.create().withIssuer("eventify").withSubject(usuario.getEmail())
					.withClaim("id", usuario.getId())
					.withExpiresAt(generarFechaExpedicion())
					.sign(algorithm);
		}catch(JWTCreationException e) {
			throw new RuntimeException();
		}
	}
	
	public Instant generarFechaExpedicion() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
	}
	
	public String getSubject(String token) {
		if (token==null) {
			throw new RuntimeException();
		}
		DecodedJWT verifier=null;
		try {
			Algorithm algorithm=Algorithm.HMAC256(apiSecret);
			verifier=JWT.require(algorithm)
					.withIssuer("eventify")
					.build()
					.verify(token);
			verifier.getSubject();
		}catch(JWTVerificationException e) {
			
		}
		if (verifier.getSubject()==null) {
			throw new RuntimeException("Verifier inválido");
		}
		return verifier.getSubject();
	}

}
