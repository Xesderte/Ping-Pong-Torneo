package com.pingpong.torneo.service;

import com.pingpong.torneo.model.Equipo;
import com.pingpong.torneo.model.Partido;
import com.pingpong.torneo.repository.EquipoRepository;
import com.pingpong.torneo.repository.PartidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class MotorDesempateService {

    @Autowired
    private PartidoRepository partidoRepository;
    
    @Autowired
    private EquipoRepository equipoRepository;

    public List<Equipo> calcularTablaPosiciones(Long idFase) {
        List<Equipo> equipos = equipoRepository.findByFaseIdFase(idFase);
        List<Partido> partidos = partidoRepository.findByFaseIdFase(idFase);

        // 1. Resetear y recalcular métricas básicas para cada equipo
        for (Equipo eq : equipos) {
            calcularMetricasBasicas(eq, partidos);
        }

        // 2. Calcular Sonneborn-Berger (cruzando puntos de rivales vencidos)
        for (Equipo eq : equipos) {
            calcularSonnebornBerger(eq, partidos, equipos);
        }

        // 3. Ordenar aplicando los 8 criterios de desempate en cascada
        Comparator<Equipo> comparador = new Comparator<Equipo>() {
            @Override
            public int compare(Equipo e1, Equipo e2) {
                if (e1.getPuntos() != e2.getPuntos()) return Integer.compare(e2.getPuntos(), e1.getPuntos());
                
                int ds1 = e1.getSetsGanados() - e1.getSetsPerdidos();
                int ds2 = e2.getSetsGanados() - e2.getSetsPerdidos();
                if (ds1 != ds2) return Integer.compare(ds2, ds1);
                
                if (e1.getSonnebornBerger() != e2.getSonnebornBerger()) 
                    return Double.compare(e2.getSonnebornBerger(), e1.getSonnebornBerger());
                    
                if (e1.getPartidosGanados() != e2.getPartidosGanados()) 
                    return Integer.compare(e2.getPartidosGanados(), e1.getPartidosGanados());
                
                return 0; // Empate absoluto
            }
        };
        
        equipos.sort(comparador);
        
        // 4. Detección de Empate Absoluto post-ordenamiento
        for (int i = 0; i < equipos.size() - 1; i++) {
            Equipo e1 = equipos.get(i);
            Equipo e2 = equipos.get(i+1);
            if (comparador.compare(e1, e2) == 0 && "JUGADO".equals(e1.getEstadoClasificacion())) {
                // Solo si el torneo exige resolución (simplificado: si se detecta un empate a 0)
                throw new com.pingpong.torneo.exception.EmpateAbsolutoException(
                    "Empate absoluto detectado entre " + e1.getNombreEquipo() + " y " + e2.getNombreEquipo(),
                    e1.getIdEquipo(), e2.getIdEquipo()
                );
            }
        }

        return equipos;
    }

    @Autowired
    private com.pingpong.torneo.repository.SetPartidoRepository setPartidoRepository;

    private void calcularMetricasBasicas(Equipo eq, List<Partido> partidos) {
        int pg = 0, pp = 0, pts = 0;
        int sf = 0, sc = 0;
        
        for (Partido p : partidos) {
            if ("JUGADO".equals(p.getEstado()) || "WALKOVER".equals(p.getEstado())) {
                boolean esLocal = p.getEquipoLocal().getIdEquipo().equals(eq.getIdEquipo());
                boolean esVisitante = p.getEquipoVisitante().getIdEquipo().equals(eq.getIdEquipo());
                
                if (esLocal) {
                    if (p.getPuntosTablaLocal() > p.getPuntosTablaVisitante()) { pg++; pts += p.getPuntosTablaLocal(); }
                    else { pp++; pts += p.getPuntosTablaLocal(); }
                } else if (esVisitante) {
                    if (p.getPuntosTablaVisitante() > p.getPuntosTablaLocal()) { pg++; pts += p.getPuntosTablaVisitante(); }
                    else { pp++; pts += p.getPuntosTablaVisitante(); }
                }
                
                if (esLocal || esVisitante) {
                    List<com.pingpong.torneo.model.SetPartido> sets = setPartidoRepository.findByPartidoIdPartido(p.getIdPartido());
                    for (com.pingpong.torneo.model.SetPartido s : sets) {
                        if (esLocal) {
                            if (s.getPuntosLocal() > s.getPuntosVisitante()) sf++; else sc++;
                        } else {
                            if (s.getPuntosVisitante() > s.getPuntosLocal()) sf++; else sc++;
                        }
                    }
                }
            }
        }
        
        eq.setPartidosGanados(pg);
        eq.setPartidosPerdidos(pp);
        eq.setPuntos(pts);
        eq.setSetsGanados(sf);
        eq.setSetsPerdidos(sc);
    }

    private void calcularSonnebornBerger(Equipo eq, List<Partido> partidos, List<Equipo> todosLosEquipos) {
        double sb = 0.0;
        for (Partido p : partidos) {
            if ("JUGADO".equals(p.getEstado()) || "WALKOVER".equals(p.getEstado())) {
                boolean jugoLocal = p.getEquipoLocal().getIdEquipo().equals(eq.getIdEquipo());
                boolean jugoVisitante = p.getEquipoVisitante().getIdEquipo().equals(eq.getIdEquipo());
                
                if (jugoLocal && p.getPuntosTablaLocal() > p.getPuntosTablaVisitante()) {
                    sb += obtenerPuntosDeEquipo(p.getEquipoVisitante().getIdEquipo(), todosLosEquipos);
                } else if (jugoVisitante && p.getPuntosTablaVisitante() > p.getPuntosTablaLocal()) {
                    sb += obtenerPuntosDeEquipo(p.getEquipoLocal().getIdEquipo(), todosLosEquipos);
                }
            }
        }
        eq.setSonnebornBerger(sb);
    }
    
    private int obtenerPuntosDeEquipo(Long idEquipo, List<Equipo> equipos) {
        for (Equipo eq : equipos) {
            if (eq.getIdEquipo().equals(idEquipo)) return eq.getPuntos();
        }
        return 0;
    }
    
    @org.springframework.transaction.annotation.Transactional
    public java.util.List<Equipo> resolverEmpateAbsoluto(Long idTorneo, Long idEquipoGanador) {
        Equipo equipo = equipoRepository.findById(idEquipoGanador).orElseThrow();
        equipo.setSonnebornBerger(equipo.getSonnebornBerger() + 0.001);
        equipoRepository.save(equipo);
        return calcularTablaPosiciones(equipo.getFase().getIdFase());
    }
}
