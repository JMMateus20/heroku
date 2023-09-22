package com.eventify.webevents.repositorio;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eventify.webevents.modelo.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long>{

	Optional<Evento> findByNombre(String nombre);
	
	@Query("SELECT E FROM Evento AS E WHERE E.fecha=:fecha OR E.nombre=:nombre")
	List<Evento> findByFechaOrNombre(@Param(value = "fecha") Date fecha, @Param(value = "nombre") String nombre);


	List<Evento> findByFechaBetween(Date fechaMinima, Date fechaMaxima);
}
