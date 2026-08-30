package com.pingpong.torneo.dto;

import java.util.List;

public class TorneoResponseDTO {
    private Long idTorneo;
    private String nombre;
    private String formato;
    private String estado;
    private List<FaseResponseDTO> fases;

    // Getters y Setters
    public Long getIdTorneo() { return idTorneo; }
    public void setIdTorneo(Long idTorneo) { this.idTorneo = idTorneo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getFormato() { return formato; }
    public void setFormato(String formato) { this.formato = formato; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public List<FaseResponseDTO> getFases() { return fases; }
    public void setFases(List<FaseResponseDTO> fases) { this.fases = fases; }

    public static class FaseResponseDTO {
        private Long idFase;
        private String nombre;
        private String tipo;

        public FaseResponseDTO(Long idFase, String nombre, String tipo) {
            this.idFase = idFase;
            this.nombre = nombre;
            this.tipo = tipo;
        }

        public Long getIdFase() { return idFase; }
        public void setIdFase(Long idFase) { this.idFase = idFase; }
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getTipo() { return tipo; }
        public void setTipo(String tipo) { this.tipo = tipo; }
    }
}
