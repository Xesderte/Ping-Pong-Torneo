package com.pingpong.torneo.dto;

import java.util.List;

public class TransicionBracketDTO {
    private List<AsignacionNodoDTO> asignaciones;

    public List<AsignacionNodoDTO> getAsignaciones() { return asignaciones; }
    public void setAsignaciones(List<AsignacionNodoDTO> asignaciones) { this.asignaciones = asignaciones; }

    public static class AsignacionNodoDTO {
        private Long idEquipo;
        private Long idNodoTarget;
        private Boolean esLocal;

        public Long getIdEquipo() { return idEquipo; }
        public void setIdEquipo(Long idEquipo) { this.idEquipo = idEquipo; }
        public void setIdNodoTarget(Long idNodoTarget) { this.idNodoTarget = idNodoTarget; }
        public Boolean getEsLocal() { return esLocal; }
        public void setEsLocal(Boolean esLocal) { this.esLocal = esLocal; }
    }
}
