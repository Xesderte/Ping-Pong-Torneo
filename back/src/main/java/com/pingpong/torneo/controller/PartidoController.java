package com.pingpong.torneo.controller;

import com.pingpong.torneo.dto.ResultadoPartidoDTO;
import com.pingpong.torneo.service.PartidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/partidos")
public class PartidoController {

    @Autowired
    private PartidoService partidoService;

    @PostMapping("/{id}/resultado")
    public ResponseEntity<?> registrarResultado(@PathVariable Long id, @Valid @RequestBody ResultadoPartidoDTO dto) {
        partidoService.registrarResultado(id, dto);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/torneo/{idTorneo}/fixture")
    public ResponseEntity<?> obtenerFixture(@PathVariable Long idTorneo) {
        return ResponseEntity.ok(partidoService.obtenerFixture(idTorneo));
    }
    
    @PostMapping("/{id}/cancelar")
    public ResponseEntity<?> suspenderPartido(@PathVariable Long id) {
        partidoService.suspenderPartido(id);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/{id}/resultado-eliminatoria")
    public ResponseEntity<?> registrarResultadoEliminatoria(@PathVariable Long id, @Valid @RequestBody ResultadoPartidoDTO dto) {
        partidoService.registrarResultadoEliminatoria(id, dto);
        return ResponseEntity.ok().build();
    }
}
