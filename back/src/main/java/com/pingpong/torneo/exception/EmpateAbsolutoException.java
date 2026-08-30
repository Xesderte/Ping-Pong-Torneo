package com.pingpong.torneo.exception;

public class EmpateAbsolutoException extends RuntimeException {
    
    private final Long idEquipo1;
    private final Long idEquipo2;

    public EmpateAbsolutoException(String message, Long idEquipo1, Long idEquipo2) {
        super(message);
        this.idEquipo1 = idEquipo1;
        this.idEquipo2 = idEquipo2;
    }

    public Long getIdEquipo1() { return idEquipo1; }
    public Long getIdEquipo2() { return idEquipo2; }
}
