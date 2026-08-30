```mermaid
stateDiagram-v2
    %% Nacimiento de la Entidad
    [*] --> CREADO : El Sistema empareja a los equipos

    %% Estado 1: Inicialización
    state CREADO {
        [*] --> Generacion
        Generacion : - Producto del Sorteo de Grupos o Liga
        Generacion : - Sin validez matemática aún
    }

    CREADO --> PENDIENTE : Fixture confirmado y publicado en la interfaz

    %% Estado 2: Ecosistema Activo
    state PENDIENTE {
        [*] --> Espera
        Espera : Fase 3 o Fase 5
        Espera : - A la espera de que se dispute el encuentro
        Espera : - No contabiliza ni afecta a la tabla general de posiciones
    }

    PENDIENTE --> EN_CURSO : El Organizador guarda el primer set (Guardado Parcial)
    
    %% Estado Intermedio: Actualización Progresiva
    state EN_CURSO {
        [*] --> Actualizacion_Parcial
        Actualizacion_Parcial : - Se registran sets de forma progresiva
        Actualizacion_Parcial : - No detona recálculo de tabla general
    }
    
    %% Bifurcaciones Transaccionales (Acciones que cierran el partido)
    EN_CURSO --> JUGADO : Se completan todos los sets y se declara ganador
    PENDIENTE --> JUGADO : El Organizador carga todos los sets de una vez al finalizar
    PENDIENTE --> WALKOVER : Registra Abandono Total (Equipo completo o Jugador Individual)
    EN_CURSO --> WALKOVER : Registra Abandono Total (Equipo completo o Jugador Individual)
    PENDIENTE --> CANCELADO : Suspensión manual por fuerza mayor
    EN_CURSO --> CANCELADO : Suspensión manual por fuerza mayor

    %% Estado 3: Flujo Ideal
    state JUGADO {
        [*] --> Transaccion_Regular
        Transaccion_Regular : --- TRIGGER DEL SISTEMA ---
        Transaccion_Regular : 1. Analiza los sets y define al Ganador / Perdedor
        Transaccion_Regular : 2. Asigna los Puntos Base predefinidos
        Transaccion_Regular : 3. Actualiza métricas globales (Sets y Puntos a Favor/Contra)
    }

    %% Estado 4: Flujo por Excepción
    state WALKOVER {
        [*] --> Transaccion_Excepcion
        Transaccion_Excepcion : --- TRIGGER DEL SISTEMA ---
        Transaccion_Excepcion : 1. Cierra el partido sin registro de sets jugados
        Transaccion_Excepcion : 2. Asigna los puntos de Victoria al equipo que quedó en pie
        Transaccion_Excepcion : 3. Asigna Derrota (0 puntos por No Presentación) al retirado
    }

    %% Estado 5: Anulación
    state CANCELADO {
        [*] --> Anulado
        Anulado : Partido anulado sin validez
        Anulado : No suma puntos a ningún equipo
    }

    %% Fin del ciclo de vida (Estados inmutables)
    JUGADO --> [*]
    WALKOVER --> [*]
    CANCELADO --> [*]