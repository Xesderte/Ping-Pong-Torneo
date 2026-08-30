package com.pingpong.torneo.repository;

import com.pingpong.torneo.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    List<Equipo> findByTorneoIdTorneo(Long idTorneo);
    List<Equipo> findByFaseIdFase(Long idFase);
}
