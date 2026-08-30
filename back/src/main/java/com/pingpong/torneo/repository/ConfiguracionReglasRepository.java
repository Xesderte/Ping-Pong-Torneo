package com.pingpong.torneo.repository;

import com.pingpong.torneo.model.ConfiguracionReglas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracionReglasRepository extends JpaRepository<ConfiguracionReglas, Long> {
    ConfiguracionReglas findByTorneoIdTorneo(Long idTorneo);
}
