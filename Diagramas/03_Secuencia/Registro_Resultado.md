```mermaid
sequenceDiagram
    autonumber
    
    %% Definición de Actores y Componentes
    actor Org as 👤 Organizador
    participant UI as 💻 Frontend (Tauri/Electron)
    participant API as ⚙️ Backend (Java)
    participant DB as 🗄️ Base de Datos (SQLite)

    %% Interacción del Usuario
    Org->>UI: Ingresa resultado por set y hace clic en "Guardar"
    activate UI
    
    %% Envío de datos al Backend
    UI->>API: POST /api/partidos/{id_partido}/resultado (Envía JSON)
    activate API
    
    %% Validaciones y Lógica en Java
    API->>API: Valida consistencia de los datos (Reglas de Sets)
    API->>API: Calcula nuevos puntos y ejecuta Motor de Desempate
    
    %% Transacción con la Base de Datos
    API->>DB: BEGIN TRANSACTION: Actualiza Partido y Posiciones
    activate DB
    DB-->>API: COMMIT (Confirmación de guardado exitoso)
    deactivate DB
    
    %% Respuesta al Frontend
    API-->>UI: HTTP 200 OK (Devuelve JSON con Tabla Recalculada)
    deactivate API
    
    %% Actualización Visual en Tiempo Real
    UI->>UI: Actualiza el estado global (State/Store)
    UI-->>Org: Redibuja el Fixture y la Tabla de Posiciones al instante
    deactivate UI