package com.pingpong.torneo.repository;

import com.pingpong.torneo.model.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, Long> {
    List<Partido> findByFaseIdFase(Long idFase);
    List<Partido> findByEquipoLocalIdEquipoOrEquipoVisitanteIdEquipo(Long idEquipoLocal, Long idEquipoVisitante);
}
