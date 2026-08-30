package com.pingpong.torneo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class SetDTO {
    
    @NotNull
    @Min(1)
    private Integer numeroSet;
    
    @NotNull
    @Min(value = 0, message = "Los puntos no pueden ser negativos")
    private Integer puntosLocal;
    
    @NotNull
    @Min(value = 0, message = "Los puntos no pueden ser negativos")
    private Integer puntosVisitante;

    public Integer getNumeroSet() { return numeroSet; }
    public void setNumeroSet(Integer numeroSet) { this.numeroSet = numeroSet; }
    public Integer getPuntosLocal() { return puntosLocal; }
    public void setPuntosLocal(Integer puntosLocal) { this.puntosLocal = puntosLocal; }
    public Integer getPuntosVisitante() { return puntosVisitante; }
    public void setPuntosVisitante(Integer puntosVisitante) { this.puntosVisitante = puntosVisitante; }
}
