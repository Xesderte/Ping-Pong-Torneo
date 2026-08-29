```mermaid
sequenceDiagram
    autonumber
    
    %% Actores y Componentes
    actor Org as 👤 Organizador
    participant API as ⚙️ Backend (Java)
    participant DB as 🗄️ Entidad Partido (SQLite)

    %% Fase 1: Nacimiento del Partido
    Org->>API: Configura y crea el torneo
    API->>DB: INSERT: Nace el registro (Estado: CREADO)
    API->>DB: UPDATE: Fixture confirmado (Estado: PENDIENTE)
    
    %% Fase 2: Ramificaciones de Vida (Alt / Else)
    alt Flujo Ideal (Partido Normal)
        Org->>API: Carga el 1er set (Guardado Parcial opcional)
        API->>DB: UPDATE: (Estado: EN_CURSO)
        
        Org->>API: Carga todos los sets terminados
        API->>DB: UPDATE: (Estado: JUGADO) - Dispara motor de posiciones
        
    else Flujo Alternativo 1 (Ausencia / Abandono)
        Org->>API: Registra baja de un jugador (W.O.)
        API->>DB: UPDATE: (Estado: WALKOVER) - Asigna puntos al rival
        
    else Flujo Alternativo 2 (Fuerza Mayor)
        Org->>API: Suspende el partido por error/clima
        API->>DB: UPDATE: (Estado: CANCELADO) - Partido anulado
    end