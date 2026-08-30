```mermaid
flowchart LR
    %% Actores
    Org(["👤 Organizador (Tú)"])
    Sys(["⚙️ Motor Lógico (Java)"])

    %% Módulos Principales (Fases)
    subgraph M1 ["1. Preparación Estructural y Formato"]
        direction TB
        A1(Parametrizar Reglas y Puntuación)
    end

    subgraph M2 ["2. Inscripción y Sorteos"]
        direction TB
        B1(Inscribir, Conformar y Sortear)
    end

    subgraph M3 ["3. Desarrollo y Tablas"]
        direction TB
        C1(Registrar Resultados y Consultar Posiciones)
    end

    subgraph M4 ["4. Manejo de Excepciones"]
        direction TB
        D1(Gestionar Reemplazos y Abandonos Totales)
    end

    subgraph M5 ["5. Transición y Eliminatorias"]
        direction TB
        E1(Mapeo Libre de Llaves y Play-offs)
    end

    %% Interacciones del Organizador (UI)
    Org --> M1
    Org --> M2
    Org --> M3
    Org --> M4
    Org --> M5

    %% Interacciones del Motor Lógico (Backend)
    M1 -.->|Configura constraints lógicos| Sys
    M2 -.->|Ejecuta algoritmos de aleatoriedad y fixture| Sys
    M3 -.->|Aplica motor estricto de desempate (8 niveles)| Sys
    M4 -.->|Recalcula matemáticas en cascada (Sonneborn-Berger)| Sys
    M5 -.->|Valida integridad de llaves y determina campeón| Sys

```
