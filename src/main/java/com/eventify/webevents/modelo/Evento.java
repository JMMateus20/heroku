package com.eventify.webevents.modelo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="eventos")
public class Evento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID_EVENTO")
	private Long id;
	
	@Column(name="NOMBRE")
	private String nombre;   //not null
	
	@Column(name="DESCRIPCION")
	private String descripcion;
	
	@Column(name="FECHA")
	private Date fecha;  //not null
	
	@Column(name="LUGAR")
	private String lugar;
	

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "asistentes", joinColumns = @JoinColumn(name="ID_EVENTO"), inverseJoinColumns = @JoinColumn(name="ID_USUARIO"))
	private List<Usuario> asistentes=new ArrayList<>();

	public Evento(String nombre, String descripcion, Date fecha, String lugar) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.lugar = lugar;
	}
	
	
	public void agregarAsistente(Usuario usuario) {
		this.asistentes.add(usuario);
	}
	
	

}
