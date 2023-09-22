package com.eventify.webevents.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eventify.webevents.modelo.Asistente;

public interface AsistenteRepository extends JpaRepository<Asistente, Long>{
	
	@Query("SELECT A FROM Asistente AS A WHERE A.evento.id=:eventoID AND A.usuario.id=:usuarioID")
	Optional<Asistente> buscarYaExistente(@Param(value = "eventoID") Long eventoID, @Param(value = "usuarioID") Long usuarioID);

}
