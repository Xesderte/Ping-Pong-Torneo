package com.pingpong.torneo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Table(name = "equipos")
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEquipo;

    @ManyToOne
    @JoinColumn(name = "id_torneo")
    @JsonIgnore
    private Torneo torneo;

    @ManyToOne
    @JoinColumn(name = "id_fase")
    @JsonIgnore
    private FaseGrupo fase; // Si el equipo está en un grupo en particulares del sorteo

    private String nombre; // Nombre del dúo o jugador individual
    
    private String estado; // "ACTIVO" o "RETIRADO"
    
    private String estadoClasificacion; // "Clasificado Directo", "Repechaje", "Eliminado"

    @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL)
    private List<Jugador> jugadores; // 1 o 2 jugadores

    public Equipo() {}

    // Getters y Setters
    public Long getIdEquipo() { return idEquipo; }
    public void setIdEquipo(Long idEquipo) { this.idEquipo = idEquipo; }
    public Torneo getTorneo() { return torneo; }
    public void setTorneo(Torneo torneo) { this.torneo = torneo; }
    public FaseGrupo getFase() { return fase; }
    public void setFase(FaseGrupo fase) { this.fase = fase; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getEstadoClasificacion() { return estadoClasificacion; }
    public void setEstadoClasificacion(String estadoClasificacion) { this.estadoClasificacion = estadoClasificacion; }
    public List<Jugador> getJugadores() { return jugadores; }
    public void setJugadores(List<Jugador> jugadores) { this.jugadores = jugadores; }
}
