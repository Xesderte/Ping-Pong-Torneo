package com.pingpong.torneo.service;

import com.pingpong.torneo.model.*;
import com.pingpong.torneo.repository.*;
import com.pingpong.torneo.dto.TorneoRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TorneoService {

    @Autowired
    private TorneoRepository torneoRepository;
    
    @Autowired
    private ConfiguracionReglasRepository reglasRepository;

    @Transactional
    public Torneo crearTorneo(TorneoRequestDTO dto) {
        Torneo torneo = new Torneo();
        torneo.setNombre(dto.getNombre());
        torneo.setFormato(dto.getFormato());
        torneo.setEstado("CONFIGURACION");
        torneo = torneoRepository.save(torneo);

        ConfiguracionReglas reglas = new ConfiguracionReglas();
        reglas.setTorneo(torneo);
        reglas.setCantidadSets(dto.getCantidadSets());
        reglas.setLimitePuntos(dto.getLimitePuntos());
        reglas.setRequiereVentaja(dto.getRequiereVentaja());
        reglasRepository.save(reglas);

        return torneo;
    }
    
    @Autowired
    private com.pingpong.torneo.repository.EquipoRepository equipoRepository;
    @Autowired
    private com.pingpong.torneo.repository.JugadorRepository jugadorRepository;
    @Autowired
    private com.pingpong.torneo.repository.NodoBracketRepository nodoBracketRepository;

    @Transactional
    public com.pingpong.torneo.model.Equipo registrarEquipos(Long idTorneo, com.pingpong.torneo.dto.EquipoRegistroDTO dto) {
        Torneo torneo = torneoRepository.findById(idTorneo).orElseThrow();
        if (!"CONFIGURACION".equals(torneo.getEstado())) throw new IllegalStateException("El torneo ya inició.");
        
        com.pingpong.torneo.model.Equipo equipo = new com.pingpong.torneo.model.Equipo();
        equipo.setTorneo(torneo);
        equipo.setNombreEquipo(dto.getNombreEquipo() != null ? dto.getNombreEquipo() : "Equipo de " + dto.getJugadores().get(0).getNombre());
        equipo.setEstadoClasificacion("Fase de Grupos");
        equipo = equipoRepository.save(equipo);
        
        for (com.pingpong.torneo.dto.JugadorDTO jDTO : dto.getJugadores()) {
            com.pingpong.torneo.model.Jugador jugador = new com.pingpong.torneo.model.Jugador();
            jugador.setNombre(jDTO.getNombre());
            jugador.setEquipo(equipo);
            jugadorRepository.save(jugador);
        }
        return equipo;
    }

    @Transactional
    public java.util.List<com.pingpong.torneo.model.Equipo> registrarEquiposAleatorios(Long idTorneo, com.pingpong.torneo.dto.IndividuosRegistroDTO dto) {
        Torneo torneo = torneoRepository.findById(idTorneo).orElseThrow();
        if (!"CONFIGURACION".equals(torneo.getEstado())) throw new IllegalStateException("El torneo ya inició.");
        
        java.util.List<com.pingpong.torneo.dto.JugadorDTO> individuos = new java.util.ArrayList<>(dto.getJugadores());
        java.util.Collections.shuffle(individuos); // El "mini-sorteo 100% aleatorio"
        
        java.util.List<com.pingpong.torneo.model.Equipo> equiposCreados = new java.util.ArrayList<>();
        
        for (int i = 0; i < individuos.size(); i += 2) {
            com.pingpong.torneo.model.Equipo equipo = new com.pingpong.torneo.model.Equipo();
            equipo.setTorneo(torneo);
            equipo.setNombreEquipo("Pareja " + (i/2 + 1));
            equipo.setEstadoClasificacion("Fase de Grupos");
            equipo = equipoRepository.save(equipo);
            equiposCreados.add(equipo);
            
            com.pingpong.torneo.model.Jugador j1 = new com.pingpong.torneo.model.Jugador();
            j1.setNombre(individuos.get(i).getNombre());
            j1.setEquipo(equipo);
            jugadorRepository.save(j1);
            
            if (i + 1 < individuos.size()) {
                com.pingpong.torneo.model.Jugador j2 = new com.pingpong.torneo.model.Jugador();
                j2.setNombre(individuos.get(i+1).getNombre());
                j2.setEquipo(equipo);
                jugadorRepository.save(j2);
            }
        }
        return equiposCreados;
    }

    @Transactional
    public Torneo transicionarAEliminatorias(Long idTorneo) {
        Torneo torneo = torneoRepository.findById(idTorneo).orElseThrow();
        if (!"EN_CURSO".equals(torneo.getEstado())) throw new IllegalStateException("Torneo no está en curso.");
        torneo.setEstado("TRANSICION_ELIMINATORIAS");
        return torneoRepository.save(torneo);
    }
    
    @Transactional
    public java.util.List<com.pingpong.torneo.model.NodoBracket> guardarBracket(Long idTorneo, com.pingpong.torneo.dto.TransicionBracketDTO dto) {
        Torneo torneo = torneoRepository.findById(idTorneo).orElseThrow();
        torneo.setEstado("ELIMINATORIAS");
        torneoRepository.save(torneo);
        
        for (com.pingpong.torneo.dto.TransicionBracketDTO.AsignacionNodoDTO asignacion : dto.getAsignaciones()) {
            com.pingpong.torneo.model.NodoBracket nodo = nodoBracketRepository.findById(asignacion.getIdNodoTarget()).orElse(new com.pingpong.torneo.model.NodoBracket());
            com.pingpong.torneo.model.Equipo equipo = equipoRepository.findById(asignacion.getIdEquipo()).orElse(null);
            
            if (Boolean.TRUE.equals(asignacion.getEsLocal())) {
                nodo.setEquipoLocal(equipo);
            } else {
                nodo.setEquipoVisitante(equipo);
            }
            
            nodoBracketRepository.save(nodo);
        }
        return nodoBracketRepository.findByTorneoIdTorneo(idTorneo);
    }
    
    public java.util.List<com.pingpong.torneo.model.NodoBracket> obtenerBracket(Long idTorneo) {
        return nodoBracketRepository.findByTorneoIdTorneo(idTorneo);
    }
    
    @Transactional(readOnly = true)
    public com.pingpong.torneo.dto.TorneoResponseDTO obtenerTorneo(Long idTorneo) {
        Torneo torneo = torneoRepository.findById(idTorneo).orElseThrow();
        com.pingpong.torneo.dto.TorneoResponseDTO dto = new com.pingpong.torneo.dto.TorneoResponseDTO();
        dto.setIdTorneo(torneo.getIdTorneo());
        dto.setNombre(torneo.getNombre());
        dto.setFormato(torneo.getFormato());
        dto.setEstado(torneo.getEstado());
        
        if (torneo.getFases() != null) {
            java.util.List<com.pingpong.torneo.dto.TorneoResponseDTO.FaseResponseDTO> fasesDTO = new java.util.ArrayList<>();
            for (FaseGrupo fase : torneo.getFases()) {
                fasesDTO.add(new com.pingpong.torneo.dto.TorneoResponseDTO.FaseResponseDTO(
                    fase.getIdFase(), fase.getNombre(), fase.getTipo()
                ));
            }
            dto.setFases(fasesDTO);
        }
        return dto;
    }
}
