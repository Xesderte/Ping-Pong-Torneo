```mermaid
sequenceDiagram
    autonumber
    
    %% Definición de Actores y Componentes
    actor Org as 👤 Organizador
    participant UI as 💻 Frontend (Tauri/Electron)
    participant API as ⚙️ Backend (Java)
    participant DB as 🗄️ Base de Datos (SQLite)

    %% Interacción del Usuario
    Org->>UI: Registra baja (W.O.) o reemplazo de un jugador
    activate UI
    
    %% Envío de la Excepción al Backend
    UI->>API: PUT /api/torneo/excepcion (Envía JSON con el evento)
    activate API
    
    %% Procesamiento de la Excepción en Java
    API->>API: Identifica partidos pendientes del jugador afectado
    API->>API: Reestructura cruces futuros o asigna victoria por Walkover
    
    %% Transacción de Reestructuración en DB
    API->>DB: BEGIN TRANSACTION: Actualiza partidos y recalcula tabla si aplica
    activate DB
    DB-->>API: COMMIT (Confirmación de reestructuración exitosa)
    deactivate DB
    
    %% Respuesta al Frontend
    API-->>UI: HTTP 200 OK (JSON con Fixture y Tabla reorganizados)
    deactivate API
    
    %% Actualización Visual
    UI->>UI: Recarga el estado global del torneo
    UI-->>Org: Muestra alerta de éxito y redibuja la interfaz limpia
    deactivate UI