package com.pingpong.torneo.controller;

import com.pingpong.torneo.dto.TorneoRequestDTO;
import com.pingpong.torneo.model.Equipo;
import com.pingpong.torneo.model.Torneo;
import com.pingpong.torneo.service.MotorDesempateService;
import com.pingpong.torneo.service.SorteoService;
import com.pingpong.torneo.service.TorneoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/torneos")
public class TorneoController {

    @Autowired
    private TorneoService torneoService;
    
    @Autowired
    private SorteoService sorteoService;
    
    @Autowired
    private MotorDesempateService desempateService;

    @PostMapping
    public ResponseEntity<Torneo> crearTorneo(@Valid @RequestBody TorneoRequestDTO dto) {
        Torneo torneo = torneoService.crearTorneo(dto);
        return ResponseEntity.ok(torneo);
    }
    
    @PostMapping("/{id}/sorteo")
    public ResponseEntity<?> generarSorteo(@PathVariable Long id, @RequestBody(required = false) com.pingpong.torneo.dto.SorteoRequestDTO dto) {
        return ResponseEntity.ok(sorteoService.generarSorteo(id, dto));
    }
    
    @GetMapping("/fase/{idFase}/posiciones")
    public ResponseEntity<List<Equipo>> obtenerTablaPosiciones(@PathVariable Long idFase) {
        List<Equipo> tabla = desempateService.calcularTablaPosiciones(idFase);
        return ResponseEntity.ok(tabla);
    }
    
    @PostMapping("/{id}/equipos")
    public ResponseEntity<?> registrarEquipos(@PathVariable Long id, @Valid @RequestBody com.pingpong.torneo.dto.EquipoRegistroDTO dto) {
        return ResponseEntity.ok(torneoService.registrarEquipos(id, dto));
    }
    
    @PostMapping("/{id}/sorteo-parejas")
    public ResponseEntity<?> registrarEquiposAleatorios(@PathVariable Long id, @Valid @RequestBody com.pingpong.torneo.dto.IndividuosRegistroDTO dto) {
        return ResponseEntity.ok(torneoService.registrarEquiposAleatorios(id, dto));
    }
    
    @PostMapping("/{id}/resolucion-empate")
    public ResponseEntity<?> resolverEmpateAbsoluto(@PathVariable Long id, @Valid @RequestBody com.pingpong.torneo.dto.EmpateAbsolutoDTO dto) {
        return ResponseEntity.ok(desempateService.resolverEmpateAbsoluto(id, dto.getIdEquipoGanador()));
    }
    
    @PostMapping("/{id}/transicion")
    public ResponseEntity<?> transicionarAELiminatorias(@PathVariable Long id) {
        return ResponseEntity.ok(torneoService.transicionarAEliminatorias(id));
    }
    
    @PostMapping("/{id}/bracket")
    public ResponseEntity<?> guardarBracket(@PathVariable Long id, @Valid @RequestBody com.pingpong.torneo.dto.TransicionBracketDTO dto) {
        return ResponseEntity.ok(torneoService.guardarBracket(id, dto));
    }
    
    @GetMapping("/{id}/bracket")
    public ResponseEntity<?> obtenerBracket(@PathVariable Long id) {
        return ResponseEntity.ok(torneoService.obtenerBracket(id));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<com.pingpong.torneo.dto.TorneoResponseDTO> obtenerTorneo(@PathVariable Long id) {
        return ResponseEntity.ok(torneoService.obtenerTorneo(id));
    }
}
