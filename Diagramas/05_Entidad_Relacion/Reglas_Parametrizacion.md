```mermaid
classDiagram
    %% Clases Centrales del Torneo
    class Torneo {
        +int id_torneo
        +String nombre
        +String formato_principal
    }

    class Fase {
        +int id_fase
        +String nombre_fase
        +String tipo_fase
        +sobrescribirReglas(ConfiguracionReglas nuevasReglas)
    }

    %% Clases de Parametrización y Reglas
    class ConfiguracionReglas {
        +int cantidad_sets
        +int limite_puntos
        +boolean ventaja_dos_puntos
        +String modalidad_cruces
        +validarFormatosPermitidos()
    }
    note for ConfiguracionReglas "Formatos Impares: 1, 3, 5 o 7\nPuntaje Objetivo: 5, 7 u 11"

    class PuntuacionBase {
        +int puntos_victoria
        +int puntos_derrota
        +int puntos_no_presentacion
    }

    %% Relaciones y Multiplicidad
    Torneo "1" *-- "1" PuntuacionBase : define (Tabla General) >
    Torneo "1" *-- "1" ConfiguracionReglas : reglas_globales (Fase 1 a 4) >
    
    Torneo "1" o-- "*" Fase : contiene >
    
    %% Relación clave para la Fase 5
    Fase "1" o-- "0..1" ConfiguracionReglas : reglas_especificas (Sobreescritura Fase 5) >