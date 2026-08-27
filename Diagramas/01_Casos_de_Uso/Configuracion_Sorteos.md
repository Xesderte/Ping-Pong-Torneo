```mermaid
flowchart TD
    %% Actores
    Org(["👤 Organizador (Tú)"])
    Sys(["⚙️ Motor Lógico (Java)"])

    subgraph Pantalla_1 ["Pantalla 1: Participantes y Estructura"]
        direction TB
        
        P1(1. Inscribir Participantes)
        P2(2. Definir Modalidad: Individual o Equipos)
        P3(3. Mini-Sorteo de Parejas)
        P4(4. Seleccionar Formato del Torneo)
        
        P5(5. Definir Cantidad de Grupos y Cupos)
        P6(6. Sortear Participantes en Grupos)
        
        P7(Generar Fixture Automático)
    end

    %% Flujo Cronológico del Organizador
    Org --> P1
    P1 --> P2
    
    %% Bifurcación: Individual vs Equipos
    P2 -.->|Individual| P4
    P2 -.->|Equipos| P3
    P3 -->|Competidores listos| P4
    
    %% Bifurcación: Grupos vs Liga
    P4 -.->|Fase de Grupos| P5
    P5 --> P6
    P4 -.->|Liga| P7

    %% Conexiones con el Sistema (Backend Java)
    P3 -.->|Ejecuta aleatoriedad| Sys
    P6 -.->|Distribuye según cupos| Sys
    P7 -.->|Crea todos los cruces| Sys
```



```mermaid
flowchart TD
    %% Actor
    Org(["👤 Organizador (Tú)"])

    subgraph Pantalla_2 ["Pantalla 2: Ajuste de Reglas"]
        direction TB
        
        R1(Configurar Modalidad de Cruces: Ida / Ida y Vuelta)
        R2(Configurar Reglas del Partido: Sets, Puntos, Ventaja de 2)
    end

    %% Acciones Independientes
    Org --> R1
    Org --> R2