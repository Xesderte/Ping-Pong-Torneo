package com.pingpong.torneo.controller;

import com.pingpong.torneo.service.GestorExcepcionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {
    
    @Autowired
    private GestorExcepcionesService excepcionesService;

    @PostMapping("/{id}/abandono-total")
    public ResponseEntity<?> registrarAbandonoTotal(@PathVariable Long id) {
        excepcionesService.registrarAbandonoTotal(id);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{id}/jugador")
    public ResponseEntity<?> editarJugador(@PathVariable Long id, @Valid @RequestBody com.pingpong.torneo.dto.JugadorDTO dto) {
        excepcionesService.editarJugador(id, dto);
        return ResponseEntity.ok().build();
    }
}
