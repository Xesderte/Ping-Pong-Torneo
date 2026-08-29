```mermaid
sequenceDiagram
    autonumber
    
    %% Definición de Actores y Componentes
    actor Org as 👤 Organizador
    participant UI as 💻 Frontend (Tauri/Electron)
    participant API as ⚙️ Backend (Java)
    participant DB as 🗄️ Base de Datos (SQLite)

    %% Registro del Último Partido
    Org->>UI: Guarda el resultado del último partido del Fixture
    activate UI
    
    %% Petición HTTP habitual
    UI->>API: POST /api/partidos/{id_partido}/resultado
    activate API
    
    %% Guardado y Verificación de Estado Global
    API->>DB: Actualiza partido a JUGADO y consulta: ¿Quedan partidos PENDIENTES?
    activate DB
    DB-->>API: Retorna count = 0 (Todo el Fixture está completo)
    deactivate DB
    
    %% Lógica de Coronación en Java
    API->>API: Detecta fin del torneo y ejecuta Motor de Desempate final
    API->>API: Consolida la tabla y determina al Campeón / Podio
    
    %% Bloqueo de Seguridad en Base de Datos
    API->>DB: UPDATE torneos SET estado = 'FINALIZADO' (Bloquea ediciones futuras)
    activate DB
    DB-->>API: COMMIT
    deactivate DB
    
    %% Generación de Archivos (Opcional)
    API->>API: (Opcional) Genera archivo de reporte (PDF/Excel)
    
    %% Respuesta definitiva al Frontend
    API-->>UI: HTTP 200 OK (JSON con estado FINALIZADO, Tabla Final y Campeón)
    deactivate API
    
    %% Transición a Pantalla de Celebración
    UI->>UI: Actualiza estado global y desactiva inputs de edición
    UI-->>Org: Redirige a pantalla de Coronación mostrando al Ganador y el reporte
    deactivate UI