package com.pingpong.torneo.repository;

import com.pingpong.torneo.model.NodoBracket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NodoBracketRepository extends JpaRepository<NodoBracket, Long> {
    List<NodoBracket> findByTorneoIdTorneo(Long idTorneo);
}
