```mermaid
flowchart TD
    %% Inicio del Método
    Start([Inicio: método registrarResultadoActualizarTabla]) --> RecibirPayload[Recibir JSON con ID de Partido y Resultados por Set]
    
    %% Validación de Datos
    RecibirPayload --> ValidarPartido{¿El partido existe y está PENDIENTE?}
    ValidarPartido -->|NO| Error1[Retornar Error: Partido inválido o ya jugado] --> Fin([Fin del Método])
    ValidarPartido -->|SÍ| ValidarLogica{¿Los sets cargados son lógicos según las reglas?}
    
    %% Control de Límite de Puntos
    ValidarLogica -->|NO| Error2[Retornar Error: Sets o puntos inválidos] --> Fin
    ValidarLogica -->|SÍ| ValidarLimitePuntos{¿Algún equipo alcanzó el límite objetivo (5, 7 u 11)?}
    ValidarLimitePuntos -->|NO| Error4[Retornar Error: Ningún equipo alcanzó el límite de puntos del set] --> Fin
    ValidarLimitePuntos -->|SÍ| ValidarVentaja{¿Regla de Ventaja de 2 puntos activada?}
    
    %% Control de Regla de Ventaja
    ValidarVentaja -->|SÍ| VerificarDiferencia{¿Diferencia mínima de 2 pts entre ganador y perdedor?}
    VerificarDiferencia -->|NO| Error3[Retornar Error: Se requiere ventaja de 2 puntos] --> Fin
    VerificarDiferencia -->|SÍ| RegistrarSet
    ValidarVentaja -->|NO| RegistrarSet[Guardar resultado parcial del Set]
    
    %% Control de Fin de Partido
    RegistrarSet --> ValidarFinPartido{¿Algún equipo ganó la mayoría de los sets totales (ej. 3 de 5)?}
    ValidarFinPartido -->|NO| CambiarEstadoEnCurso[Actualizar Estado a EN_CURSO]
    ValidarFinPartido -->|SÍ| CambiarEstadoJugado[Actualizar Estado a JUGADO]
    
    %% Cálculos del Backend
    CambiarEstadoEnCurso --> ActualizarStats[Actualizar métricas temporales de Sets y Tantos]
    CambiarEstadoJugado --> CalcularPuntos[Calcular ganador final y asignar puntos base]
    CalcularPuntos --> ActualizarStats
    
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
    
    class ValidarPartido,ValidarLogica,ValidarLimitePuntos,ValidarVentaja,VerificarDiferencia,ValidarFinPartido decision;
    class RegistrarSet,CambiarEstadoEnCurso,CambiarEstadoJugado,CalcularPuntos,ActualizarStats logica;
    class TransaccionDB bd;
    class Error1,Error2,Error3,Error4 error;