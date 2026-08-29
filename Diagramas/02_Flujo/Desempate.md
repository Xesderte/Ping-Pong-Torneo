```mermaid
flowchart TD
    %% Inicio del Método
    Start([Inicio: método calcularDesempate]) --> RecibirEmpatados[Recibir lista de equipos empatados en puntos base]
    
    %% Criterio 1: Enfrentamiento directo
    RecibirEmpatados --> C1{1. ¿Desempata por Enfrentamiento Directo?}
    C1 -->|SÍ| Ordenar1[Ordenar por ganador del cruce mutuo] --> RetornarTabla
    C1 -->|NO / Empate| C2{2. ¿Desempata por Diferencia de Sets?}
    
    %% Criterio 2: Diferencia de sets
    C2 -->|SÍ| Ordenar2[Ordenar por Diferencia de Sets] --> RetornarTabla
    C2 -->|NO / Empate| C3{3. ¿Desempata por Mayor cant. de Sets Ganados?}
    
    %% Criterio 3: Mayor cantidad de sets ganados
    C3 -->|SÍ| Ordenar3[Ordenar por Mayor Cantidad de Sets Ganados] --> RetornarTabla
    C3 -->|NO / Empate| C4{4. ¿Desempata por Menor cant. de Sets Perdidos?}
    
    %% Criterio 4: Menor cantidad de sets perdidos
    C4 -->|SÍ| Ordenar4[Ordenar por Menor Cantidad de Sets Perdidos] --> RetornarTabla
    C4 -->|NO / Empate| C5{5. ¿Desempata por Diferencia de Puntos?}
    
    %% Criterio 5: Diferencia de puntos
    C5 -->|SÍ| Ordenar5[Ordenar por Diferencia de Puntos] --> RetornarTabla
    C5 -->|NO / Empate| C6{6. ¿Desempata por Mayor cant. de Puntos a Favor?}
    
    %% Criterio 6: Mayor cantidad de puntos a favor
    C6 -->|SÍ| Ordenar6[Ordenar por Mayor Cantidad de Puntos a Favor] --> RetornarTabla
    C6 -->|NO / Empate| C7{7. ¿Desempata por Menor cant. de Puntos en Contra?}
    
    %% Criterio 7: Menor cantidad de puntos en contra
    C7 -->|SÍ| Ordenar7[Ordenar por Menor Cantidad de Puntos en Contra] --> RetornarTabla
    C7 -->|NO / Empate| C8{8. ¿Desempata por Sistema Sonneborn-Berger?}
    
    %% Criterio 8: Calidad del Rival (Sonneborn-Berger)
    C8 -->|SÍ| Ordenar8[Ordenar sumando puntos en tabla de rivales vencidos] --> RetornarTabla
    C8 -->|NO / Empate Absoluto| Sorteo[Empate Técnico / Definir por Sorteo Aleatorio] --> RetornarTabla
    
    %% Retorno de la Tabla Ordenada
    RetornarTabla[Consolidar nuevo orden de la tabla] --> RetornarJSON[Devolver JSON con posiciones de desempate aplicadas]
    RetornarJSON --> Fin([Fin del Método])

    %% Estilos
    classDef logica fill:#2b3a42,stroke:#4a6fa5,stroke-width:2px,color:#fff;
    classDef decision fill:#6d3b47,stroke:#a55c6e,stroke-width:2px,color:#fff;
    classDef bd fill:#3b592d,stroke:#5c8a46,stroke-width:2px,color:#fff;
    
    class C1,C2,C3,C4,C5,C6,C7,C8 decision;
    class Ordenar1,Ordenar2,Ordenar3,Ordenar4,Ordenar5,Ordenar6,Ordenar7,Ordenar8,Sorteo logica;
    class RetornarJSON bd;