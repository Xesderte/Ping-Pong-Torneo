package com.pingpong.torneo.repository;

import com.pingpong.torneo.model.SetPartido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SetPartidoRepository extends JpaRepository<SetPartido, Long> {
    List<SetPartido> findByPartidoIdPartido(Long idPartido);
}
