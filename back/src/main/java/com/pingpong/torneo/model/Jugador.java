package com.pingpong.torneo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "jugadores")
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJugador;

    @ManyToOne
    @JoinColumn(name = "id_equipo")
    @JsonIgnore
    private Equipo equipo;

    private String nombre; // Nombre real de la persona

    public Jugador() {}

    // Getters y Setters
    public Long getIdJugador() { return idJugador; }
    public void setIdJugador(Long idJugador) { this.idJugador = idJugador; }
    public Equipo getEquipo() { return equipo; }
    public void setEquipo(Equipo equipo) { this.equipo = equipo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
