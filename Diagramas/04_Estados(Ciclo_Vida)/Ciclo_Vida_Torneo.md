```mermaid
stateDiagram-v2
    %% Inicio del Ciclo de Vida
    [*] --> CONFIGURACION : Crear Torneo

    %% Estado de Preparación
    state CONFIGURACION {
        [*] --> Setup
        Setup : Fases 1 y 2
        Setup : - Seleccionar formato principal (Liga o Grupos)
        Setup : - Parametrizar reglas base (sets, puntos, ventaja)
        Setup : - Inscripción y sorteos aleatorios
    }
    
    CONFIGURACION --> EN_CURSO : Finaliza sorteo y se genera fixture

    %% Estado de Ejecución Regular
    state EN_CURSO {
        [*] --> Desarrollo
        Desarrollo : Fases 3 y 4
        Desarrollo : - Registro manual de resultados por set
        Desarrollo : - Cálculo automatizado de posiciones
        Desarrollo : - Gestión de excepciones (Abandonos/Reemplazos)
    }

    %% Bifurcación dependiendo del Formato Elegido
    state validacion_formato <<choice>>
    EN_CURSO --> validacion_formato : Se juegan todos los partidos pendientes

    validacion_formato --> FINALIZADO : [Formato Liga]
    validacion_formato --> TRANSICION : [Fase de Grupos]

    %% Estado de Preparación para Playoffs
    state TRANSICION {
        [*] --> Espera
        Espera : Fase 5 (Inicio)
        Espera : - Se bloquea la tabla de posiciones
        Espera : - El sistema genera Lista de Rendimiento General
        Espera : - **Candado Abierto:** Reconfigurar reglas (ej. cambiar a 5/7 sets)
        Espera : - Armado libre y manual de llaves
    }

    TRANSICION --> ELIMINATORIAS : El Organizador confirma las llaves

    %% Estado de Playoffs
    state ELIMINATORIAS {
        [*] --> Playoffs
        Playoffs : Fase 5 (Desarrollo)
        Playoffs : - Se aplican las nuevas reglas reconfiguradas
        Playoffs : - Registro de resultados de eliminación directa
    }

    ELIMINATORIAS --> FINALIZADO : El Sistema declara a un Campeón definitivo

    %% Estado de Cierre
    state FINALIZADO {
        [*] --> Cierre
        Cierre : - Sistema de puntuación bloqueado
        Cierre : - Torneo inmutable (Solo Lectura)
    }
    
    FINALIZADO --> [*]