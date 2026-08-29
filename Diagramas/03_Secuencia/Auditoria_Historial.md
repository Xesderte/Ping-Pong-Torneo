```mermaid
sequenceDiagram
    autonumber
    
    %% Definición de Actores y Componentes
    actor Org as 👤 Organizador
    participant UI as 💻 Frontend (Tauri/Electron)
    participant API as ⚙️ Backend (Java)
    participant DB as 🗄️ Base de Datos (SQLite)

    %% Interacción del Usuario en el Dashboard
    Org->>UI: Hace clic en la fila de un equipo específico en la tabla
    activate UI
    
    %% Petición HTTP de Solo Lectura
    UI->>API: GET /api/equipos/{id_equipo}/historial
    activate API
    
    %% Consulta específica a la Base de Datos
    API->>DB: SELECT de partidos (Estado = 'JUGADO') filtrando por {id_equipo}
    activate DB
    DB-->>API: Retorna registros crudos de los enfrentamientos
    deactivate DB
    
    %% Estructuración de la Respuesta
    API->>API: Procesa y estructura los resultados detallados por set de cada partido
    
    %% Respuesta Ligera al Frontend
    API-->>UI: HTTP 200 OK (JSON con el historial de enfrentamientos directos)
    deactivate API
    
    %% Renderizado de la Interfaz (Transparencia sin sobrecarga)
    UI->>UI: Procesa el JSON sin recargar la vista global del torneo
    UI-->>Org: Despliega un panel secundario (Modal) con el historial detallado
    deactivate UI