package com.eventify.webevents.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eventify.webevents.repositorio.UsuarioRepository;
import com.eventify.webevents.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{
	
	@Autowired
	private TokenService ts;
	
	@Autowired
	private UsuarioRepository usuarioRep;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var token=request.getHeader("Authorization");
		if (token!=null) {
			token=token.replace("Bearer ", "");
			var subject=ts.getSubject(token);
			if (subject!=null) {
				var usuario=usuarioRep.buscarPorEmail(subject);
				var authentication=new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}	
		
		filterChain.doFilter(request, response);
		
	}

}
