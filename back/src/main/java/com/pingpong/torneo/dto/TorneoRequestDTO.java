package com.pingpong.torneo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TorneoRequestDTO {
    
    @NotBlank(message = "El nombre del torneo es obligatorio")
    private String nombre;
    
    @NotBlank(message = "El formato es obligatorio")
    private String formato; // Liga o Fase de Grupos
    
    // Reglas Base
    @NotNull(message = "La cantidad de sets es obligatoria")
    @Min(value = 1, message = "Debe jugarse al menos 1 set")
    private Integer cantidadSets;
    
    @NotNull(message = "El límite de puntos es obligatorio")
    @Min(value = 5, message = "El límite mínimo de puntos es 5")
    private Integer limitePuntos;
    
    @NotNull(message = "Debe especificar si requiere ventaja")
    private Boolean requiereVentaja;
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getFormato() { return formato; }
    public void setFormato(String formato) { this.formato = formato; }
    public Integer getCantidadSets() { return cantidadSets; }
    public void setCantidadSets(Integer cantidadSets) { this.cantidadSets = cantidadSets; }
    public Integer getLimitePuntos() { return limitePuntos; }
    public void setLimitePuntos(Integer limitePuntos) { this.limitePuntos = limitePuntos; }
    public Boolean getRequiereVentaja() { return requiereVentaja; }
    public void setRequiereVentaja(Boolean requiereVentaja) { this.requiereVentaja = requiereVentaja; }
}
