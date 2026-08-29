```mermaid
flowchart TD
    %% Inicio del Método
    Start([Inicio: método registrarResultadoActualizarTabla]) --> RecibirPayload[Recibir JSON con ID de Partido y Resultados por Set]
    
    %% Validación de Datos
    RecibirPayload --> ValidarPartido{¿El partido existe y está PENDIENTE?}
    ValidarPartido -->|NO| Error1[Retornar Error: Partido inválido o ya jugado] --> Fin([Fin del Método])
    ValidarPartido -->|SÍ| ValidarLogica{¿Los sets cargados son lógicos según las reglas?}
    
    %% Control de Errores de Lógica
    ValidarLogica -->|NO| Error2[Retornar Error: Sets o puntos inválidos] --> Fin
    ValidarLogica -->|SÍ| CambiarEstado[Actualizar Estado del Partido a JUGADO y guardar sets]
    
    %% Cálculos del Backend
    CambiarEstado --> CalcularPuntos[Calcular ganador y perdedor del partido]
    CalcularPuntos --> ActualizarStats[Recalcular Estadisticas: Puntos, Sets y Tantos - Favor y Contra]
    
    %% Transacción con la Base de Datos
    ActualizarStats --> TransaccionDB[(Guardar Partido y actualizar Tabla de Posiciones en SQLite)]
    
    %% Respuesta al Frontend
    TransaccionDB --> RetornarJSON[Devolver JSON con la Tabla de Posiciones actualizada y el Partido marcado como JUGADO]
    RetornarJSON --> Fin

    %% Estilos
    classDef logica fill:#2b3a42,stroke:#4a6fa5,stroke-width:2px,color:#fff;
    classDef decision fill:#6d3b47,stroke:#a55c6e,stroke-width:2px,color:#fff;
    classDef bd fill:#3b592d,stroke:#5c8a46,stroke-width:2px,color:#fff;
    classDef error fill:#5c2d2d,stroke:#8a4646,stroke-width:2px,color:#fff;
    
    class ValidarPartido,ValidarLogica decision;
    class CambiarEstado,CalcularPuntos,ActualizarStats logica;
    class TransaccionDB bd;
    class Error1,Error2 error;