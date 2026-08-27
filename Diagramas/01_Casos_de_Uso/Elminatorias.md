```mermaid
flowchart TD
    %% Actores
    Org(["👤 Organizador (Tú)"])
    Sys(["⚙️ Motor Lógico (Java)"])

    subgraph Pantalla_Bracket ["Pantalla 3: Árbol de Eliminatorias (Bracket)"]
        direction TB
        
        F1(Consultar Ranking de Rendimiento Final)
        F2(Armar Llaves Manualmente - Drag and Drop)
        F3(Reconfigurar Reglas de Partido: Sets y Puntos)
        F4(Registrar Resultados de Eliminatorias)
        F5(((🏆 Declarar Campeón)))
    end

    %% Acciones del Organizador
    Org --> F1
    Org --> F2
    Org --> F3
    Org --> F4

    %% Flujo lógico de la interfaz
    F1 -.->|Sirve de guía para| F2
    F3 -.->|Nuevas reglas se aplican a| F4
    F4 -->|Gana la final| F5

    %% Interacción con el Backend (Java)
    F2 -.->|Guarda el mapeo de los cruces| Sys
    F4 -.->|Envia puntos del partido| Sys
    Sys -.->|Calcula el ganador y lo avanza de ronda| F4