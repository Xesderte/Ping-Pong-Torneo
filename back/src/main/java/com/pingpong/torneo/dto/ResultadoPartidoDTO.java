package com.pingpong.torneo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class ResultadoPartidoDTO {
    
    @NotEmpty(message = "Debe enviar al menos un set")
    @Valid
    private List<SetDTO> sets;
    
    @NotNull
    private Boolean partidoFinalizado;

    public List<SetDTO> getSets() { return sets; }
    public void setSets(List<SetDTO> sets) { this.sets = sets; }
    public Boolean getPartidoFinalizado() { return partidoFinalizado; }
    public void setPartidoFinalizado(Boolean partidoFinalizado) { this.partidoFinalizado = partidoFinalizado; }
}
