package com.pingpong.torneo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Table(name = "partidos")
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPartido;

    @ManyToOne
    @JoinColumn(name = "id_fase")
    @JsonIgnore
    private FaseGrupo fase; // Si pertenece a la fase de grupos o liga

    @ManyToOne
    @JoinColumn(name = "id_nodo")
    @JsonIgnore
    private NodoBracket nodoBracket; // Si pertenece a las eliminatorias

    @ManyToOne
    @JoinColumn(name = "id_equipo_local")
    private Equipo equipoLocal;

    @ManyToOne
    @JoinColumn(name = "id_equipo_visitante")
    private Equipo equipoVisitante;

    private String estado; // "PENDIENTE", "EN_CURSO", "JUGADO", "WALKOVER", "CANCELADO"
    
    // Puntos otorgados a la tabla general (para aislar resultados de abandonos)
    private Integer puntosTablaLocal = 0;
    private Integer puntosTablaVisitante = 0;
    
    private Integer fecha; // Jornada del fixture (1, 2, 3...)

    @OneToMany(mappedBy = "partido", cascade = CascadeType.ALL)
    private List<SetPartido> sets;

    public Partido() {}

    // Getters y Setters
    public Long getIdPartido() { return idPartido; }
    public void setIdPartido(Long idPartido) { this.idPartido = idPartido; }
    public FaseGrupo getFase() { return fase; }
    public void setFase(FaseGrupo fase) { this.fase = fase; }
    public NodoBracket getNodoBracket() { return nodoBracket; }
    public void setNodoBracket(NodoBracket nodoBracket) { this.nodoBracket = nodoBracket; }
    public Equipo getEquipoLocal() { return equipoLocal; }
    public void setEquipoLocal(Equipo equipoLocal) { this.equipoLocal = equipoLocal; }
    public Equipo getEquipoVisitante() { return equipoVisitante; }
    public void setEquipoVisitante(Equipo equipoVisitante) { this.equipoVisitante = equipoVisitante; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Integer getPuntosTablaLocal() { return puntosTablaLocal; }
    public void setPuntosTablaLocal(Integer puntosTablaLocal) { this.puntosTablaLocal = puntosTablaLocal; }
    public Integer getPuntosTablaVisitante() { return puntosTablaVisitante; }
    public void setPuntosTablaVisitante(Integer puntosTablaVisitante) { this.puntosTablaVisitante = puntosTablaVisitante; }
    public Integer getFecha() { return fecha; }
    public void setFecha(Integer fecha) { this.fecha = fecha; }
    public List<SetPartido> getSets() { return sets; }
    public void setSets(List<SetPartido> sets) { this.sets = sets; }
}
