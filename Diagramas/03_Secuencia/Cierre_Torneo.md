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
    DB-->>API: Retorna count = 0 (Todo el Fixture inicial está completo)
    deactivate DB
    
    API->>API: Detecta fin de la fase inicial
    
    %% Bifurcación arquitectónica según el formato del torneo
    alt Formato == Liga (Todos contra todos)
        %% Lógica de Coronación Directa
        API->>API: Ejecuta Motor de Desempate final
        API->>API: Consolida la tabla y determina al Campeón / Podio
        
        API->>DB: UPDATE torneos SET estado = 'FINALIZADO' (Bloquea ediciones)
        activate DB
        DB-->>API: COMMIT
        deactivate DB
        
        API-->>UI: HTTP 200 OK (JSON con estado FINALIZADO y Campeón)
        
        UI->>UI: Actualiza estado global y desactiva inputs de edición
        UI-->>Org: Redirige a pantalla de Coronación mostrando al Ganador
        
    else Formato == Fase de Grupos
        %% Transición a Eliminatorias (Armado Libre)
        API->>API: Ejecuta lógica de Clasificación y Repechaje (Pares/Impares)
        API->>API: Consolida la "Lista de Rendimiento General"
        
        API->>DB: UPDATE torneos SET estado = 'TRANSICION_ELIMINATORIAS'
        activate DB
        DB-->>API: COMMIT
        deactivate DB
        
        API-->>UI: HTTP 200 OK (JSON con Lista de Rendimiento estructurada)
        
        UI->>UI: Habilita el módulo de Drag and Drop para llaves
        UI-->>Org: Redirige a Pantalla 3 (Árbol de Eliminatorias) para mapeo manual
    end
    deactivate API
    deactivate UI