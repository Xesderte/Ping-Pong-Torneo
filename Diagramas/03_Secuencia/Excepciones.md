```mermaid
sequenceDiagram
    autonumber
    
    %% Definición de Actores y Componentes
    actor Org as 👤 Organizador
    participant UI as 💻 Frontend (Tauri/Electron)
    participant API as ⚙️ Backend (Java)
    participant DB as 🗄️ Base de Datos (SQLite)

    %% Interacción Inicial
    Org->>UI: Inicia gestión de excepción en un equipo (Fase 4)
    activate UI
    
    alt Reemplazo de Jugador (Abandono Parcial)
        %% Flujo Ligero: Solo actualización de texto sin alterar la matemática
        Org->>UI: Edita el nombre del participante que abandona
        UI->>API: PUT /api/jugadores/{id} (Payload con nombre del reemplazo)
        activate API
        
        API->>DB: UPDATE: Sobreescribe el nombre del jugador en el registro
        activate DB
        DB-->>API: Confirmación de actualización
        deactivate DB
        
        API-->>UI: HTTP 200 OK (Datos del jugador actualizados)
        deactivate API
        UI-->>Org: Refresca la vista visualizando al nuevo jugador
        
    else Abandono Total (Retiro de Equipo/Jugador individual)
        %% Flujo Pesado: Recálculo matemático transaccional en cascada
        Org->>UI: Marca al equipo completo como "Retirado"
        UI->>API: POST /api/equipos/{id}/retiro (Notifica abandono)
        activate API
        
        %% Inicio de Transacción Crítica
        API->>DB: BEGIN TRANSACTION: Bloquea la base de datos
        activate DB
        
        %% Alteración de Partidos Pasados y Futuros
        API->>DB: UPDATE: Asigna automáticamente derrotas (0 pts) al equipo retirado
        API->>DB: UPDATE: Suma puntos de victoria correspondientes a los rivales afectados
        
        %% Recálculo del Motor Lógico
        API->>API: Ejecuta motor para recalcular métricas de la Tabla Global (PJ, PG, Pts, etc.)
        
        DB-->>API: COMMIT (Confirma y guarda los cambios en cascada)
        deactivate DB
        
        %% Respuesta final
        API-->>UI: HTTP 200 OK (Devuelve JSON con la Tabla Global reestructurada)
        deactivate API
        
        %% Actualización Visual en Tiempo Real
        UI->>UI: Recarga el estado (State) global del torneo en memoria
        UI-->>Org: Redibuja la Tabla y el Fixture manteniendo la matemática intacta
    end
    deactivate UI