```mermaid
flowchart TD
    %% Inicio del Método
    Start([Inicio: método calcularDesempate]) --> RecibirEmpatados[Recibir lista de equipos empatados en puntos]
    
    %% Criterio 1: Diferencia de Sets
    RecibirEmpatados --> EvaluarSets{¿Persiste el empate tras evaluar Diferencia de Sets?}
    EvaluarSets -->|NO| OrdenarSets[Ordenar equipos por Diferencia de Sets] --> RetornarTabla
    EvaluarSets -->|SÍ| EvaluarTantos{¿Persiste el empate tras evaluar Diferencia de Tantos / Puntos?}
    
    %% Criterio 2: Diferencia de Tantos
    EvaluarTantos -->|NO| OrdenarTantos[Ordenar equipos por Diferencia de Tantos] --> RetornarTabla
    EvaluarTantos -->|SÍ| EvaluarDirecto{¿Hay Partido Directo entre los empatados?}
    
    %% Criterio 3: Partido Directo (Head-to-Head)
    EvaluarDirecto -->|SÍ| EvaluarResultado{¿El ganador del enfrentamiento mutuo desempata?}
    EvaluarResultado -->|SÍ| OrdenarDirecto[Ordenar según resultado entre ellos] --> RetornarTabla
    EvaluarResultado -->|NO| EvaluarSorteo
    EvaluarDirecto -->|NO| EvaluarSorteo{¿Aplica Sorteo Aleatorio final?}
    
    %% Criterio 4: Sorteo Aleatorio (Último recurso)
    EvaluarSorteo -->|SÍ| EjecutarSorteo[Ejecutar randomizador Java para definir posición] --> RetornarTabla
    EvaluarSorteo -->|NO| MarcarEmpateTecnico[Mantener orden y registrar empate técnico] --> RetornarTabla
    
    %% Retorno de la Tabla Ordenada
    RetornarTabla[Consolidar nuevo orden de la tabla] --> RetornarJSON[Devolver JSON con posiciones desempate aplicadas]
    RetornarJSON --> Fin([Fin del Método])

    %% Estilos
    classDef logica fill:#2b3a42,stroke:#4a6fa5,stroke-width:2px,color:#fff;
    classDef decision fill:#6d3b47,stroke:#a55c6e,stroke-width:2px,color:#fff;
    classDef bd fill:#3b592d,stroke:#5c8a46,stroke-width:2px,color:#fff;
    
    class EvaluarSets,EvaluarTantos,EvaluarDirecto,EvaluarResultado,EvaluarSorteo decision;
    class OrdenarSets,OrdenarTantos,OrdenarDirecto,EjecutarSorteo,MarcarEmpateTecnico logica;
    class RetornarJSON bd;