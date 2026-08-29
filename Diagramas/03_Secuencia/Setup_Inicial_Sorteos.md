```mermaid
sequenceDiagram
    autonumber
    
    %% Definición de Actores y Componentes
    actor Org as 👤 Organizador
    participant UI as 💻 Frontend (Tauri/Electron)
    participant API as ⚙️ Backend (Java)
    participant DB as 🗄️ Base de Datos (SQLite)

    %% Interacción del Usuario (Fase 1 y 2)
    Org->>UI: Configura formato, reglas (sets, ventaja, puntuación) e inscribe jugadores
    activate UI
    
    %% Envío de configuración al Backend
    UI->>API: POST /api/torneos/setup (Envía JSON con configuración y lista de jugadores)
    activate API
    
    %% Motor Lógico: Aleatoriedad y Equipos
    alt Modalidad == En Parejas
        API->>API: Ejecuta "mini-sorteo 100% aleatorio" para agrupar individuos en parejas
    end
    
    %% Transacción de Equipos
    API->>DB: BEGIN TRANSACTION
    activate DB
    API->>DB: INSERT MASIVO (Bulk Insert): Guarda todos los Equipos generados
    DB-->>API: Confirmación de inserción de equipos
    
    %% Motor Lógico: Generación de Cruces
    API->>API: Ejecuta sorteo principal de grupos o genera fixture automático de Liga
    
    %% Transacción de Partidos
    API->>DB: INSERT MASIVO (Bulk Insert): Guarda los cruces como Partidos (Estado: PENDIENTE)
    DB-->>API: COMMIT (Confirmación de base de datos lista)
    deactivate DB
    
    %% Respuesta al Frontend
    API-->>UI: HTTP 200 OK (JSON indicando que el torneo está listo para arrancar)
    deactivate API
    
    %% Actualización Visual
    UI->>UI: Inicializa el estado (Store) con los datos del torneo
    UI-->>Org: Redirige al Dashboard principal mostrando el fixture y las tablas
    deactivate UI