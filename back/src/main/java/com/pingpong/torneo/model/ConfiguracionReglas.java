package com.pingpong.torneo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "configuracion_reglas")
public class ConfiguracionReglas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConfiguracion;

    @OneToOne
    @JoinColumn(name = "id_torneo")
    @JsonIgnore
    private Torneo torneo; // Si aplica a todo el torneo

    @OneToOne
    @JoinColumn(name = "id_nodo")
    @JsonIgnore
    private NodoBracket nodoBracket; // Si aplica solo a un nodo eliminatorio (ej. Final a 5 sets)

    private Integer cantidadSets; // 1, 3, 5, 7
    private Integer limitePuntos; // 5, 7, 11
    private Boolean requiereVentaja; // Ventaja obligatoria de 2 pts

    // Puntos para tabla
    private Integer puntosVictoria = 3;
    private Integer puntosEmpate = 1; // Aunque es raro en ping-pong
    private Integer puntosDerrota = 0;
    private Integer puntosNoPresentacion = 0;

    public ConfiguracionReglas() {}

    // Getters y Setters
    public Long getIdConfiguracion() { return idConfiguracion; }
    public void setIdConfiguracion(Long idConfiguracion) { this.idConfiguracion = idConfiguracion; }
    public Torneo getTorneo() { return torneo; }
    public void setTorneo(Torneo torneo) { this.torneo = torneo; }
    public NodoBracket getNodoBracket() { return nodoBracket; }
    public void setNodoBracket(NodoBracket nodoBracket) { this.nodoBracket = nodoBracket; }
    public Integer getCantidadSets() { return cantidadSets; }
    public void setCantidadSets(Integer cantidadSets) { this.cantidadSets = cantidadSets; }
    public Integer getLimitePuntos() { return limitePuntos; }
    public void setLimitePuntos(Integer limitePuntos) { this.limitePuntos = limitePuntos; }
    public Boolean getRequiereVentaja() { return requiereVentaja; }
    public void setRequiereVentaja(Boolean requiereVentaja) { this.requiereVentaja = requiereVentaja; }
    public Integer getPuntosVictoria() { return puntosVictoria; }
    public void setPuntosVictoria(Integer puntosVictoria) { this.puntosVictoria = puntosVictoria; }
    public Integer getPuntosEmpate() { return puntosEmpate; }
    public void setPuntosEmpate(Integer puntosEmpate) { this.puntosEmpate = puntosEmpate; }
    public Integer getPuntosDerrota() { return puntosDerrota; }
    public void setPuntosDerrota(Integer puntosDerrota) { this.puntosDerrota = puntosDerrota; }
    public Integer getPuntosNoPresentacion() { return puntosNoPresentacion; }
    public void setPuntosNoPresentacion(Integer puntosNoPresentacion) { this.puntosNoPresentacion = puntosNoPresentacion; }
}
