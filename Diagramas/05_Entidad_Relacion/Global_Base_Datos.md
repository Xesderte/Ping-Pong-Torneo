```mermaid
erDiagram
    %% Relaciones Principales
    TORNEO ||--|{ FASE_GRUPO : "contiene"
    TORNEO ||--|{ EQUIPO : "inscribe (sin límite)"
    FASE_GRUPO ||--|{ EQUIPO : "agrupa (simétrico/asimétrico)"
    FASE_GRUPO ||--o{ PARTIDO : "organiza fixture"
    EQUIPO ||--o{ PARTIDO : "juega como local"
    EQUIPO ||--o{ PARTIDO : "juega como visitante"
    PARTIDO ||--|{ SET : "desglosa puntaje"

    %% Definición de Tablas y Atributos

    TORNEO {
        int id_torneo PK
        string nombre
        string formato "Liga o Fase de Grupos"
        string estado "Ej: CONFIGURACION, EN_CURSO"
    }

    FASE_GRUPO {
        int id_fase PK
        int id_torneo FK
        string nombre "Ej: Grupo A, Liga Única, Llave Final"
        string tipo "Determina si es tabla regular o eliminación"
    }

    EQUIPO {
        int id_equipo PK
        int id_torneo FK
        int id_fase FK
        string nombre "Nombre del dúo o jugador individual"
        string estado "ACTIVO o RETIRADO"
    }

    PARTIDO {
        int id_partido PK
        int id_fase FK
        int id_equipo_local FK
        int id_equipo_visitante FK
        string estado "PENDIENTE, JUGADO, WALKOVER, CANCELADO"
    }

    SET {
        int id_set PK
        int id_partido FK
        int numero_set "Ej: 1, 2, 3..."
        int puntos_local "Puntaje exacto anotado"
        int puntos_visitante "Puntaje exacto anotado"
    }