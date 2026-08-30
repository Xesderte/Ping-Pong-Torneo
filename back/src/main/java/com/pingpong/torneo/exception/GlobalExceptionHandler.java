package com.pingpong.torneo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Manejo de Empates Absolutos u otros conflictos lógicos (409 Conflict)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleIllegalStateException(IllegalStateException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(com.pingpong.torneo.exception.EmpateAbsolutoException.class)
    public ResponseEntity<Map<String, Object>> handleEmpateAbsolutoException(com.pingpong.torneo.exception.EmpateAbsolutoException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "EMPATE_ABSOLUTO");
        error.put("mensaje", ex.getMessage());
        error.put("idEquipo1", ex.getIdEquipo1());
        error.put("idEquipo2", ex.getIdEquipo2());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    // Manejo de validaciones fallidas (400 Bad Request)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Petición Inválida");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Manejo de datos JSON inválidos (Jakarta Validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
