```mermaid
sequenceDiagram
    autonumber
    
    %% Definición de Actores y Componentes
    actor Org as 👤 Organizador
    participant UI as 💻 Frontend (Tauri/Electron)
    participant API as ⚙️ Backend (Java)
    participant DB as 🗄️ Base de Datos (SQLite)

    %% Obtención de la Lista de Rendimiento
    Org->>UI: Accede a la etapa de Eliminatorias tras finalizar grupos
    activate UI
    UI->>API: GET /api/torneos/{id_torneo}/rendimiento
    activate API
    API->>DB: Consulta posiciones finales, clasificados y repechajes
    activate DB
    DB-->>API: Retorna historial y métricas de los equipos
    deactivate DB
    API-->>UI: HTTP 200 OK (JSON con la Lista de Rendimiento General)
    deactivate API
    UI-->>Org: Renderiza el ranking y habilita la interfaz Drag and Drop
    
    %% Armado Manual y Reconfiguración
    Org->>UI: Empareja equipos libremente y reconfigura reglas (Sets y Puntos)
    UI->>API: POST /api/torneos/{id_torneo}/eliminatorias (Payload: Cruces + Nuevas Reglas)
    activate API
    
    %% Transacción de Guardado en Base de Datos
    API->>DB: BEGIN TRANSACTION
    activate DB
    API->>DB: UPDATE: Guarda las nuevas reglas (ej. pasa de 3 a 5 sets) para esta fase
    API->>DB: INSERT MASIVO (Bulk Insert): Registra los nuevos partidos del Bracket (Estado: PENDIENTE)
    DB-->>API: COMMIT (Confirmación de almacenamiento exitoso)
    deactivate DB
    
    %% Respuesta al Frontend
    API-->>UI: HTTP 200 OK (Bracket y reglas configuradas correctamente)
    deactivate API
    
    %% Actualización Visual
    UI->>UI: Actualiza el estado de la aplicación a modo Eliminatorias
    UI-->>Org: Despliega el Árbol de Eliminatorias (Bracket) listo para iniciar
    deactivate UI