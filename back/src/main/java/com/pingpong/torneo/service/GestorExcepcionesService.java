package com.pingpong.torneo.service;

import com.pingpong.torneo.model.Equipo;
import com.pingpong.torneo.model.Partido;
import com.pingpong.torneo.repository.EquipoRepository;
import com.pingpong.torneo.repository.PartidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GestorExcepcionesService {

    @Autowired
    private EquipoRepository equipoRepository;
    
    @Autowired
    private PartidoRepository partidoRepository;
    
    @Autowired
    private MotorDesempateService desempateService;

    @Transactional
    public java.util.List<Equipo> registrarAbandonoTotal(Long idEquipo) {
        Equipo equipo = equipoRepository.findById(idEquipo)
            .orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado"));

        equipo.setEstado("RETIRADO"); // Cambiar el estado según diagrama
        equipoRepository.save(equipo);

        // Buscar todos los partidos del equipo
        List<Partido> partidos = partidoRepository.findByEquipoLocalIdEquipoOrEquipoVisitanteIdEquipo(idEquipo, idEquipo);

        for (Partido p : partidos) {
            if ("PENDIENTE".equals(p.getEstado()) || "EN_CURSO".equals(p.getEstado())) {
                p.setEstado("WALKOVER");
                
                // Lógica: 3 pts al rival, 0 al retirado
                if (p.getEquipoLocal().getIdEquipo().equals(idEquipo)) {
                    p.setPuntosTablaVisitante(3);
                    p.setPuntosTablaLocal(0);
                } else {
                    p.setPuntosTablaLocal(3);
                    p.setPuntosTablaVisitante(0);
                }
                partidoRepository.save(p);
            }
        }
        
        return desempateService.calcularTablaPosiciones(equipo.getTorneo().getFases().get(0).getIdFase()); // Asumiendo fase unica de grupos
    }
    @Transactional
    public Equipo editarJugador(Long idEquipo, com.pingpong.torneo.dto.JugadorDTO dto) {
        Equipo equipo = equipoRepository.findById(idEquipo)
            .orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado"));
        if (!equipo.getJugadores().isEmpty()) {
            equipo.getJugadores().get(0).setNombre(dto.getNombre());
            equipo = equipoRepository.save(equipo);
        }
        return equipo;
    }
}
