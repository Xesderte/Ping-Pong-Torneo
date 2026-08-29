```mermaid
flowchart TD
    %% Inicio del Método
    Start([Inicio: Algoritmo de Clasificación de Grupo]) --> RecibirTabla[(Obtener DB: Tabla Final de Posiciones del Grupo)]
    
    %% Conteo y Evaluación Matemática
    RecibirTabla --> ContarEquipos[Contar cantidad total de equipos en el grupo 'N']
    ContarEquipos --> EvaluarParidad{¿'N' es un número Par o Impar?}
    
    %% Rama 1: Grupos Pares
    EvaluarParidad -->|Par| CalcPar[Calcular: N / 2]
    CalcPar --> AsignarPar[Asignar exactamente la primera mitad superior a 'Clasificados']
    AsignarPar --> AsignarRestoPar[Asignar la mitad inferior a 'No Clasificados']
    AsignarRestoPar --> Consolidar
    
    %% Rama 2: Grupos Impares
    EvaluarParidad -->|Impar| CalcImpar[Calcular: N / 2 y redondear hacia abajo - Math.floor]
    CalcImpar --> AsignarImpar[Asignar esa cantidad superior a 'Clasificados Directos']
    AsignarImpar --> AsignarRepechaje[Tomar el equipo con el porcentaje sobrante / siguiente lugar en tabla]
    AsignarRepechaje --> MarcarRepechaje[Asignar a ese equipo específico a 'Zona de Repechaje']
    MarcarRepechaje --> AsignarRestoImpar[Asignar a los restantes inferiores a 'No Clasificados']
    AsignarRestoImpar --> Consolidar
    
    %% Persistencia y Salida
    Consolidar[(Actualizar estado de rendimiento de cada equipo en SQLite)] --> ConsolidarListas[Consolidar Lista de Rendimiento General de todos los grupos]
    ConsolidarListas --> RetornarJSON[Devolver JSON con la lista ordenada al Frontend]
    RetornarJSON --> Fin([Fin del Método])

    %% Estilos
    classDef logica fill:#2b3a42,stroke:#4a6fa5,stroke-width:2px,color:#fff;
    classDef decision fill:#6d3b47,stroke:#a55c6e,stroke-width:2px,color:#fff;
    classDef bd fill:#3b592d,stroke:#5c8a46,stroke-width:2px,color:#fff;
    
    class EvaluarParidad decision;
    class ContarEquipos,CalcPar,AsignarPar,AsignarRestoPar,CalcImpar,AsignarImpar,AsignarRepechaje,MarcarRepechaje,AsignarRestoImpar,ConsolidarListas logica;
    class RecibirTabla,Consolidar,RetornarJSON bd;