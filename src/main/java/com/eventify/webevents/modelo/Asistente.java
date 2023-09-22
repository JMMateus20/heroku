package com.eventify.webevents.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="asistentes")
public class Asistente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID_ASISTENTE")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="ID_EVENTO")
	private Evento evento;
	
	@ManyToOne
	@JoinColumn(name="ID_USUARIO")
	private Usuario usuario;

	public Asistente(Evento evento, Usuario usuario) {
		this.evento = evento;
		this.usuario = usuario;
	}
	
	

}
