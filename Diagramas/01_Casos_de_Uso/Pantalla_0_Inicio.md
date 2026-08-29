```mermaid
flowchart TD
    %% Actores
    Org(["👤 Organizador (Tú)"])
    Sys(["⚙️ Motor Lógico (Java)"])

    subgraph Pantalla_Inicio ["Pantalla 0: Inicio y Gestión Raíz"]
        direction TB
        
        A1(1. Crear Nuevo Torneo)
        A2(2. Cargar Torneo Guardado)
        
        A3(3. Definir Nombre del Torneo)
    end

    %% Interacción Inicial del Organizador
    Org --> A1
    Org --> A2

    %% Flujo para un Torneo Nuevo
    A1 --> A3
    A3 -.->|Transición de interfaz| Pantalla1([Ir a Pantalla 1: Participantes y Estructura])

    %% Flujo para un Torneo Existente
    A2 -.->|Solicita datos históricos| Sys
    Sys -.->|Devuelve estado y fixture| Dashboard([Ir a Panel Principal / Dashboard])