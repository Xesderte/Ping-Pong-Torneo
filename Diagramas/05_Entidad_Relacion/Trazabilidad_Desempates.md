```mermaid
classDiagram
    %% Clase Principal
    class Equipo {
        +int id_equipo
        +String nombre
        +obtenerHistorialContra(Equipo rival) List~Partido~
    }

    %% Métricas para Desempates
    class MetricasGlobales {
        +int puntos_tabla
        +int partidos_jugados
        +int partidos_ganados
        +int partidos_perdidos
        +int diferencia_sets
        +int sets_ganados
        +int sets_perdidos
        +int diferencia_puntos
        +int puntos_favor
        +int puntos_contra
        +calcularSonnebornBerger(List~Equipo~ rivales_vencidos) int
    }

    %% Trazabilidad y Auditoría
    class Partido {
        +int id_partido
        +Equipo local
        +Equipo visitante
        +String estado
        +obtenerGanador() Equipo
        +obtenerPerdedor() Equipo
    }

    class Set {
        +int numero_set
        +int puntos_local
        +int puntos_visitante
    }

    %% Motor Estricto de Desempate
    class MotorDesempate {
        +ordenarTabla(List~Equipo~ equipos_empatados) List~Equipo~
        -1_evaluarEnfrentamientoDirecto()
        -2_evaluarDiferenciaSets()
        -3_evaluarMayorSetsGanados()
        -4_evaluarMenorSetsPerdidos()
        -5_evaluarDiferenciaPuntos()
        -6_evaluarMayorPuntosFavor()
        -7_evaluarMenorPuntosContra()
        -8_evaluarCalidadRival_SonnebornBerger()
    }

    %% Relaciones
    Equipo "1" *-- "1" MetricasGlobales : posee >
    Equipo "1" o-- "*" Partido : historial_partidos >
    Partido "1" *-- "1..*" Set : desglose_puntos >
    
    %% Dependencias del Motor Lógico
    MotorDesempate ..> Equipo : evalúa >
    MotorDesempate ..> MetricasGlobales : extrae métricas >