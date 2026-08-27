```mermaid
flowchart TD
    %% Actores
    Org(["👤 Organizador (Tú)"])
    Sys(["⚙️ Motor Lógico (Java)"])

    subgraph Pantalla_Dashboard ["Panel Principal (Dashboard)"]
        direction TB
        
        subgraph Zona_Juego ["Fase 3: Desarrollo y Tablas"]
            direction TB
            D1(Registrar Resultados por Set)
            D2(Visualizar Tabla Global de Posiciones)
            D3(Ver Historial Detallado - Criterio 1)
        end

        subgraph Zona_Emergencia ["Fase 4: Manejo de Excepciones"]
            direction TB
            E1(Reemplazar Jugador de un Dúo)
            E2(Registrar Abandono Total de Equipo)
        end
    end

    %% Acciones principales del Organizador
    Org --> D1
    Org --> D2
    Org --> E1
    Org --> E2

    %% Lógica de Interfaz (Navegación)
    D2 -.->|Hacer clic en la fila de un equipo| D3

    %% Conexiones con el Motor Lógico (Backend Java)
    D1 -.->|Envia puntajes del partido| Sys
    E2 -.->|Notifica el retiro| Sys

    Sys -.->|Procesa desempates y actualiza posiciones| D2
    Sys -.->|Asigna derrotas con 0 pts a futuros cruces| D2