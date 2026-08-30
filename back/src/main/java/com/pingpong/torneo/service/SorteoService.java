package com.pingpong.torneo.service;

import com.pingpong.torneo.model.Equipo;
import com.pingpong.torneo.model.FaseGrupo;
import com.pingpong.torneo.model.Torneo;
import com.pingpong.torneo.repository.EquipoRepository;
import com.pingpong.torneo.repository.FaseGrupoRepository;
import com.pingpong.torneo.repository.TorneoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class SorteoService {

    @Autowired
    private TorneoRepository torneoRepository;
    
    @Autowired
    private EquipoRepository equipoRepository;
    
    @Autowired
    private FaseGrupoRepository faseGrupoRepository;

    @Transactional
    public java.util.List<com.pingpong.torneo.model.Partido> generarSorteo(Long idTorneo, com.pingpong.torneo.dto.SorteoRequestDTO dto) {
        Torneo torneo = torneoRepository.findById(idTorneo)
            .orElseThrow(() -> new IllegalArgumentException("Torneo no encontrado"));
            
        if (!"CONFIGURACION".equals(torneo.getEstado())) {
            throw new IllegalStateException("El torneo ya no está en fase de configuración.");
        }

        List<Equipo> equipos = equipoRepository.findByTorneoIdTorneo(idTorneo);
        if (equipos.isEmpty()) {
            throw new IllegalStateException("No hay equipos inscritos para el sorteo.");
        }

        Collections.shuffle(equipos);
        java.util.List<FaseGrupo> fasesGuardadas = new java.util.ArrayList<>();

        if ("Liga".equals(torneo.getFormato())) {
            FaseGrupo liga = new FaseGrupo();
            liga.setTorneo(torneo);
            liga.setNombre("Liga Única");
            liga = faseGrupoRepository.save(liga);
            
            for (Equipo eq : equipos) {
                eq.setFase(liga);
                equipoRepository.save(eq);
            }
            fasesGuardadas.add(liga);
        } else {
            int maxEquipos = (dto != null && dto.getMaxEquiposPorGrupo() != null && dto.getMaxEquiposPorGrupo() > 1) 
                            ? dto.getMaxEquiposPorGrupo() : 4;
            int numGrupos = (int) Math.ceil((double) equipos.size() / maxEquipos);
            if (numGrupos == 0) numGrupos = 1;

            for (int i = 0; i < numGrupos; i++) {
                FaseGrupo grupo = new FaseGrupo();
                grupo.setTorneo(torneo);
                grupo.setNombre("Grupo " + (char)('A' + i));
                grupo = faseGrupoRepository.save(grupo);
                fasesGuardadas.add(grupo);
            }
            
            for (int i = 0; i < equipos.size(); i++) {
                Equipo eq = equipos.get(i);
                eq.setFase(fasesGuardadas.get(i % numGrupos));
                equipoRepository.save(eq);
            }
        }

        torneo.setEstado("EN_CURSO");
        torneoRepository.save(torneo);
        
        for (FaseGrupo fase : fasesGuardadas) {
            List<Equipo> equiposGrupo = equipoRepository.findByFaseIdFase(fase.getIdFase());
            generarFixtureRoundRobin(equiposGrupo, fase);
        }
        
        return partidoRepository.findByFaseIdFase(fasesGuardadas.get(0).getIdFase());
    }

    @Autowired
    private com.pingpong.torneo.repository.PartidoRepository partidoRepository;
    
    @Autowired
    private com.pingpong.torneo.repository.EquipoRepository equipoRepo;

    private void generarFixtureRoundRobin(List<Equipo> grupo, FaseGrupo fase) {
        if (grupo.size() < 2) return;
        
        List<Equipo> equiposRotacion = new java.util.ArrayList<>(grupo);
        if (equiposRotacion.size() % 2 != 0) {
            equiposRotacion.add(null); // Dummy ("Bye") para equipo que descansa
        }
        
        int numFechas = equiposRotacion.size() - 1;
        int numPartidosPorFecha = equiposRotacion.size() / 2;
        
        for (int fecha = 1; fecha <= numFechas; fecha++) {
            for (int p = 0; p < numPartidosPorFecha; p++) {
                Equipo local = equiposRotacion.get(p);
                Equipo visitante = equiposRotacion.get(equiposRotacion.size() - 1 - p);
                
                if (local != null && visitante != null) {
                    com.pingpong.torneo.model.Partido partido = new com.pingpong.torneo.model.Partido();
                    partido.setFase(fase);
                    partido.setEquipoLocal(local);
                    partido.setEquipoVisitante(visitante);
                    partido.setEstado("PENDIENTE");
                    partido.setFecha(fecha);
                    partidoRepository.save(partido);
                }
            }
            // Rotar elementos (menos el primero)
            Equipo ultimo = equiposRotacion.remove(equiposRotacion.size() - 1);
            equiposRotacion.add(1, ultimo);
        }
    }
}
