package com.pingpong.torneo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "torneos")
public class Torneo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTorneo;

    private String nombre;

    private String formato; // "Liga" o "Fase de Grupos"

    private String estado; // "CONFIGURACION", "EN_CURSO", "TRANSICION_ELIMINATORIAS", "ELIMINATORIAS", "FINALIZADO"

    @OneToMany(mappedBy = "torneo", cascade = CascadeType.ALL)
    private List<FaseGrupo> fases;

    @OneToMany(mappedBy = "torneo", cascade = CascadeType.ALL)
    private List<Equipo> equipos;

    public Torneo() {}

    // Getters y Setters
    public Long getIdTorneo() { return idTorneo; }
    public void setIdTorneo(Long idTorneo) { this.idTorneo = idTorneo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getFormato() { return formato; }
    public void setFormato(String formato) { this.formato = formato; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public List<FaseGrupo> getFases() { return fases; }
    public void setFases(List<FaseGrupo> fases) { this.fases = fases; }
    public List<Equipo> getEquipos() { return equipos; }
    public void setEquipos(List<Equipo> equipos) { this.equipos = equipos; }
}
