```mermaid
stateDiagram-v2
    %% Inicio de la Entidad
    [*] --> INSCRITO : El Organizador carga al participante

    %% Estado 1: Preparación
    state INSCRITO {
        [*] --> Espera
        Espera : Fase 1 y 2
        Espera : - Participante en lista general
        Espera : - Sujeto a mini-sorteo de parejas
        Espera : - Sujeto a sorteo de grupos
    }

    INSCRITO --> ACTIVO : El Sistema genera el fixture y arranca el torneo

    %% Estado 2: Ecosistema Vivo
    state ACTIVO {
        [*] --> Compitiendo
        Compitiendo : Fase 3 y 5
        Compitiendo : - Suma puntos en la tabla
        Compitiendo : - Elegible para llaves de eliminatorias
        
        %% Manejo de Excepciones Menores (Transición interna)
        Compitiendo --> Compitiendo : Abandono Parcial (Reemplazo)
        note right of Compitiendo
            El Organizador edita el nombre.
            Mantiene el estado ACTIVO.
            No altera las matemáticas.
        end note
    }

    %% El evento catastrófico (Fase 4)
    ACTIVO --> RETIRADO : Registrar Abandono Total (Equipo completo o Individual)

    %% Estado 3: Irrevocable y Transaccional
    state RETIRADO {
        [*] --> Bucle_Destructivo
        Bucle_Destructivo : Estado Final e Inmutable
        Bucle_Destructivo : --- TRIGGER DEL SISTEMA ---
        Bucle_Destructivo : 1. Se asignan derrotas (0 puntos) futuros y pasados
        Bucle_Destructivo : 2. Se suman los puntos de victoria a todos sus rivales
        Bucle_Destructivo : 3. Se recalcula la matemática general de la tabla
    }

    RETIRADO --> [*]