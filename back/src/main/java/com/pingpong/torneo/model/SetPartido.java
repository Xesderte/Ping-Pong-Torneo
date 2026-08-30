package com.pingpong.torneo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "sets_partido")
public class SetPartido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSet;

    @ManyToOne
    @JoinColumn(name = "id_partido")
    @JsonIgnore
    private Partido partido;

    private Integer numeroSet; // 1, 2, 3...

    private Integer puntosLocal; // Puntaje exacto anotado
    private Integer puntosVisitante; // Puntaje exacto anotado

    public SetPartido() {}

    // Getters y Setters
    public Long getIdSet() { return idSet; }
    public void setIdSet(Long idSet) { this.idSet = idSet; }
    public Partido getPartido() { return partido; }
    public void setPartido(Partido partido) { this.partido = partido; }
    public Integer getNumeroSet() { return numeroSet; }
    public void setNumeroSet(Integer numeroSet) { this.numeroSet = numeroSet; }
    public Integer getPuntosLocal() { return puntosLocal; }
    public void setPuntosLocal(Integer puntosLocal) { this.puntosLocal = puntosLocal; }
    public Integer getPuntosVisitante() { return puntosVisitante; }
    public void setPuntosVisitante(Integer puntosVisitante) { this.puntosVisitante = puntosVisitante; }
}
