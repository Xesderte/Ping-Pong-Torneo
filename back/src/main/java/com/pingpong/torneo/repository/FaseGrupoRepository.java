package com.pingpong.torneo.repository;

import com.pingpong.torneo.model.FaseGrupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FaseGrupoRepository extends JpaRepository<FaseGrupo, Long> {
    List<FaseGrupo> findByTorneoIdTorneo(Long idTorneo);
}
