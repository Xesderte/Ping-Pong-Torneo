package com.pingpong.torneo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "nodos_bracket")
public class NodoBracket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNodo;

    @ManyToOne
    @JoinColumn(name = "id_torneo")
    @JsonIgnore
    private Torneo torneo;

    @ManyToOne
    @JoinColumn(name = "id_nodo_siguiente")
    @JsonIgnore
    private NodoBracket nodoSiguiente; // Hacia donde avanza el ganador (NULL si es la Final)

    private String instancia; // "Octavos", "Cuartos", "Semifinal", "Final"
    
    @OneToOne(mappedBy = "nodoBracket", cascade = CascadeType.ALL)
    private ConfiguracionReglas reglasLocales; // Si es distinto a las reglas del torneo

    public NodoBracket() {}

    // Getters y Setters
    public Long getIdNodo() { return idNodo; }
    public void setIdNodo(Long idNodo) { this.idNodo = idNodo; }
    public Torneo getTorneo() { return torneo; }
    public void setTorneo(Torneo torneo) { this.torneo = torneo; }
    public NodoBracket getNodoSiguiente() { return nodoSiguiente; }
    public void setNodoSiguiente(NodoBracket nodoSiguiente) { this.nodoSiguiente = nodoSiguiente; }
    public String getInstancia() { return instancia; }
    public void setInstancia(String instancia) { this.instancia = instancia; }
    public ConfiguracionReglas getReglasLocales() { return reglasLocales; }
    public void setReglasLocales(ConfiguracionReglas reglasLocales) { this.reglasLocales = reglasLocales; }
}
