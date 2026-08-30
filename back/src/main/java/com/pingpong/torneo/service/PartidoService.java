package com.pingpong.torneo.service;

import com.pingpong.torneo.dto.ResultadoPartidoDTO;
import com.pingpong.torneo.dto.SetDTO;
import com.pingpong.torneo.model.ConfiguracionReglas;
import com.pingpong.torneo.model.Partido;
import com.pingpong.torneo.repository.ConfiguracionReglasRepository;
import com.pingpong.torneo.repository.PartidoRepository;
import com.pingpong.torneo.repository.SetPartidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PartidoService {

    @Autowired
    private PartidoRepository partidoRepository;

    @Autowired
    private ConfiguracionReglasRepository reglasRepository;

    @Autowired
    private MotorDesempateService desempateService;
    
    @Autowired
    private SetPartidoRepository setPartidoRepository;

    @Transactional
    public java.util.List<com.pingpong.torneo.model.Equipo> registrarResultado(Long idPartido, ResultadoPartidoDTO dto) {
        Partido partido = partidoRepository.findById(idPartido)
            .orElseThrow(() -> new IllegalArgumentException("Partido no encontrado"));

        if (!"PENDIENTE".equals(partido.getEstado()) && !"EN_CURSO".equals(partido.getEstado())) {
            throw new IllegalStateException("El partido ya fue finalizado o cancelado.");
        }

        ConfiguracionReglas reglas = reglasRepository.findByTorneoIdTorneo(partido.getFase().getTorneo().getIdTorneo());

        int setsGanadosLocal = 0;
        int setsGanadosVisitante = 0;
        int puntosTotalesLocal = 0;
        int puntosTotalesVisitante = 0;

        for (SetDTO setDTO : dto.getSets()) {
            validarReglaVentaja(setDTO.getPuntosLocal(), setDTO.getPuntosVisitante(), reglas);
            
            com.pingpong.torneo.model.SetPartido setPartido = new com.pingpong.torneo.model.SetPartido();
            setPartido.setPartido(partido);
            setPartido.setPuntosLocal(setDTO.getPuntosLocal());
            setPartido.setPuntosVisitante(setDTO.getPuntosVisitante());
            setPartidoRepository.save(setPartido);
            
            if (setDTO.getPuntosLocal() > setDTO.getPuntosVisitante()) {
                setsGanadosLocal++;
            } else if (setDTO.getPuntosVisitante() > setDTO.getPuntosLocal()) {
                setsGanadosVisitante++;
            }
            
            puntosTotalesLocal += setDTO.getPuntosLocal();
            puntosTotalesVisitante += setDTO.getPuntosVisitante();
        }

        if (dto.getPartidoFinalizado()) {
            partido.setEstado("JUGADO");
            
            // Asignar puntos para la tabla general (Victoria = 3, Derrota = 0, no hay empates en Ping Pong)
            if (setsGanadosLocal > setsGanadosVisitante) {
                partido.setPuntosTablaLocal(3);
                partido.setPuntosTablaVisitante(0);
            } else {
                partido.setPuntosTablaLocal(0);
                partido.setPuntosTablaVisitante(3);
            }
        } else {
            partido.setEstado("EN_CURSO");
        }

        partidoRepository.save(partido);
        
        return desempateService.calcularTablaPosiciones(partido.getFase().getIdFase());
    }

    private void validarReglaVentaja(int ptsLocal, int ptsVisitante, ConfiguracionReglas reglas) {
        int limite = reglas.getLimitePuntos();
        
        // Ninguno llegó al límite
        if (ptsLocal < limite && ptsVisitante < limite) {
            throw new IllegalArgumentException("Ningún equipo alcanzó el límite de puntos (" + limite + ")");
        }
        
        if (reglas.getRequiereVentaja()) {
            int diferencia = Math.abs(ptsLocal - ptsVisitante);
            if (diferencia < 2) {
                throw new IllegalArgumentException("La regla de ventaja de 2 puntos está activa. El resultado " + ptsLocal + "-" + ptsVisitante + " es inválido.");
            }
        }
    }

    public java.util.List<Partido> obtenerFixture(Long idTorneo) {
        // En una app real, buscarías por idFase o idTorneo filtrando
        return partidoRepository.findAll();
    }

    @Transactional
    public Partido suspenderPartido(Long idPartido) {
        Partido partido = partidoRepository.findById(idPartido).orElseThrow();
        partido.setEstado("CANCELADO");
        return partidoRepository.save(partido);
    }

    @Autowired
    private com.pingpong.torneo.repository.NodoBracketRepository nodoBracketRepository;

    @Transactional
    public java.util.List<com.pingpong.torneo.model.NodoBracket> registrarResultadoEliminatoria(Long idPartido, ResultadoPartidoDTO dto) {
        registrarResultado(idPartido, dto);
        Partido partido = partidoRepository.findById(idPartido).orElseThrow();
        if ("JUGADO".equals(partido.getEstado())) {
            com.pingpong.torneo.model.Equipo ganador = null;
            if (partido.getPuntosTablaLocal() > partido.getPuntosTablaVisitante()) {
                ganador = partido.getEquipoLocal();
            } else {
                ganador = partido.getEquipoVisitante();
            }
            
            // Avanzar ganador
            com.pingpong.torneo.model.NodoBracket nodoActual = nodoBracketRepository.findByTorneoIdTorneo(partido.getFase().getTorneo().getIdTorneo())
                .stream().filter(n -> n.getEquipoLocal().equals(partido.getEquipoLocal()) || n.getEquipoLocal().equals(partido.getEquipoVisitante()))
                .findFirst().orElse(null);
                
            if (nodoActual != null && nodoActual.getSiguienteNodo() != null) {
                com.pingpong.torneo.model.NodoBracket sig = nodoActual.getSiguienteNodo();
                if (sig.getEquipoLocal() == null) sig.setEquipoLocal(ganador);
                else sig.setEquipoVisitante(ganador);
                nodoBracketRepository.save(sig);
            }
        }
        return nodoBracketRepository.findByTorneoIdTorneo(partido.getFase().getTorneo().getIdTorneo());
    }
}
