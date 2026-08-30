```mermaid
flowchart TD
    %% Inicio del Método
    Start([Inicio: Reemplazo de Jugador (Abandono Parcial)]) --> RecibirPayload[Recibir JSON con ID de Equipo y Nuevo Nombre de Jugador]
    
    %% Validaciones Básicas
    RecibirPayload --> ValidarEquipo{¿El equipo existe y está ACTIVO?}
    ValidarEquipo -->|NO| Error1[Retornar Error: Equipo inválido o retirado] --> Fin([Fin del Método])
    
    %% Edición Logística
    ValidarEquipo -->|SÍ| ValidarModalidad{¿El equipo es de formato Dúo?}
    ValidarModalidad -->|NO| Error2[Retornar Error: Solo válido para formatos en pareja] --> Fin
    
    ValidarModalidad -->|SÍ| ActualizarRegistro[Actualizar campo 'nombre' del jugador en la BD]
    
    %% Persistencia
    ActualizarRegistro --> TransaccionDB[(Guardar cambios en SQLite)]
    
    %% Finalización (Sin impacto matemático)
    TransaccionDB --> RetornarJSON[Devolver HTTP 200 OK - Equipo actualizado]
    RetornarJSON --> NoMatematica[Nota: NO se recalculan tablas ni se asignan puntos]
    NoMatematica --> Fin
    
    %% Estilos
    classDef logica fill:#2b3a42,stroke:#4a6fa5,stroke-width:2px,color:#fff;
    classDef decision fill:#6d3b47,stroke:#a55c6e,stroke-width:2px,color:#fff;
    classDef bd fill:#3b592d,stroke:#5c8a46,stroke-width:2px,color:#fff;
    classDef error fill:#5c2d2d,stroke:#8a4646,stroke-width:2px,color:#fff;
    
    class ValidarEquipo,ValidarModalidad decision;
    class ActualizarRegistro,NoMatematica logica;
    class TransaccionDB bd;
    class Error1,Error2 error;
```
