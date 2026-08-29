```mermaid
flowchart TD
    %% Actores
    Org(["👤 Organizador (Tú)"])
    Sys(["⚙️ Motor Lógico (Java)"])

    subgraph Fase_Transicion ["Transición: Fase de Grupos a Eliminatorias"]
        direction TB
        
        T1(Fin de Fase de Grupos: Consolidar Puntuaciones) --> T2{¿El grupo es de cantidad Par o Impar?}
        
        %% Ramificación Par
        T2 -->|Par| T3(Clasifica exactamente la mitad de los equipos de forma directa)
        
        %% Ramificación Impar
        T2 -->|Impar| T4(Calcula la mitad redondeada hacia abajo para clasificación directa)
        T4 --> T5(Envía automáticamente al equipo sobrante / 3er lugar a Zona de Repechaje)
        
        %% Consolidación de la Lista
        T3 --> T6(Genera la Lista de Rendimiento General Completa)
        T5 --> T6
    end

    %% Interacción con el Sistema y Organizador
    Sys -.->|Ejecuta algoritmo de clasificación y repechaje invisible| T2
    T6 -.->|Entrega ranking estructurado| Org
    Org --> T7(Consulta la lista de rendimiento y arma llaves manualmente)