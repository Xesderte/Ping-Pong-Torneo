```mermaid
flowchart TD
    %% Actores
    Org(["👤 Organizador (Tú)"])
    Sys(["⚙️ Motor Lógico (Java)"])

    subgraph Pantalla_2 ["Pantalla 2: Ajuste de Reglas y Puntuación"]
        direction TB
        
        R1(Configurar Modalidad de Cruces: Ida / Ida y Vuelta)
        R2(Configurar Reglas del Partido: Sets, Puntos, Ventaja de 2)
        R3(Configurar Sistema de Puntuación Base: Puntos por Victoria, Derrota y No Presentación)
    end

    %% Acciones del Organizador
    Org --> R1
    Org --> R2
    Org --> R3

    %% Conexión con el Sistema
    R3 -.->|Envía parámetros de puntuación base| Sys