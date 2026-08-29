```mermaid
sequenceDiagram
    autonumber
    
    %% Definición de Actores y Componentes
    actor Org as 👤 Organizador
    participant UI as 💻 Frontend (Tauri/Electron)
    participant API as ⚙️ Backend (Java)
    participant DB as 🗄️ Base de Datos (SQLite)

    %% Inicio del Flujo
    Org->>UI: Abre la app / Selecciona un Torneo guardado
    activate UI
    
    %% Petición HTTP / Comunicación IPC
    UI->>API: GET /api/torneo/{id_torneo} (Solicita datos)
    activate API
    
    %% Consulta a la Base de Datos
    API->>DB: Ejecuta consulta SQL (SELECT * FROM partidos, equipos...)
    activate DB
    DB-->>API: Retorna los registros (ResultSet) crudos
    deactivate DB
    
    %% Procesamiento en el Servidor
    API->>API: Procesa los datos y arma el objeto JSON completo
    
    %% Respuesta al Frontend
    API-->>UI: Responde HTTP 200 OK (JSON con Fixture y Configuraciones)
    deactivate API
    
    %% Renderizado de la Pantalla
    UI->>UI: Actualiza el estado (State) de la interfaz
    UI-->>Org: Dibuja y muestra el Panel Principal (Dashboard)
    deactivate UI