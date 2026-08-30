package com.pingpong.torneo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Table(name = "fases_grupos")
public class FaseGrupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFase;

    @ManyToOne
    @JoinColumn(name = "id_torneo")
    @JsonIgnore
    private Torneo torneo;

    private String nombre; // Ej: "Grupo A", "Liga Única"
    private String tipo; // Determina si es tabla regular o eliminación

    @OneToMany(mappedBy = "fase", cascade = CascadeType.ALL)
    private List<Partido> partidos;

    public FaseGrupo() {}

    // Getters y Setters
    public Long getIdFase() { return idFase; }
    public void setIdFase(Long idFase) { this.idFase = idFase; }
    public Torneo getTorneo() { return torneo; }
    public void setTorneo(Torneo torneo) { this.torneo = torneo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public List<Partido> getPartidos() { return partidos; }
    public void setPartidos(List<Partido> partidos) { this.partidos = partidos; }
}
