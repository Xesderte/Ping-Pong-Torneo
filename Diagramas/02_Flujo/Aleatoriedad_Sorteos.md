```mermaid
flowchart TD
    %% Inicio del Método
    Start([Inicio: Algoritmo de Aleatoriedad y Sorteos]) --> RecibirLista[Recibir Array de Participantes Inscritos]
    
    %% Fase A: Mini-Sorteo de Equipos
    RecibirLista --> EvaluarModalidad{¿El torneo es en Parejas?}
    
    EvaluarModalidad -->|SÍ| ShuffleJugadores[Mezclar aleatoriamente el Array de Jugadores]
    ShuffleJugadores --> BucleParejas[Bucle: Extraer 2 jugadores a la vez del Array]
    BucleParejas --> CrearEquipo[Crear entidad 'Equipo' y asignar la pareja]
    CrearEquipo --> FinParejas{¿Quedan jugadores por asignar?}
    FinParejas -->|SÍ| BucleParejas
    FinParejas -->|NO| ListaEquipos[Consolidar Array final de Equipos formados]
    
    EvaluarModalidad -->|NO / Individual| AsignarDirecto[Cada jugador se envuelve en su propia entidad 'Equipo']
    AsignarDirecto --> ListaEquipos
    
    %% Fase B: Sorteo de Grupos
    ListaEquipos --> EvaluarFormato{¿El formato es Fase de Grupos?}
    
    EvaluarFormato -->|NO / Liga| FinLiga([Fin: Pasar Array consolidado al método generarFixture])
    
    EvaluarFormato -->|SÍ| RecibirCupos[Recibir config: Cantidad de Grupos y sus respectivos cupos]
    RecibirCupos --> ShuffleEquipos[Mezclar aleatoriamente el Array final de Equipos]
    ShuffleEquipos --> BucleGrupos[Bucle: Iterar Array de Equipos mezclado]
    BucleGrupos --> AsignarGrupo[Asignar Equipo al Grupo correspondiente hasta llenar su cupo]
    AsignarGrupo --> FinGrupos{¿Faltan Equipos por ubicar?}
    FinGrupos -->|SÍ| BucleGrupos
    
    %% Persistencia y Cierre
    FinGrupos -->|NO| GuardarGrupos[(Guardar estructura de Grupos en SQLite)]
    GuardarGrupos --> FinGruposAsignados([Fin: Grupos listos para generar cruces])

    %% Estilos
    classDef logica fill:#2b3a42,stroke:#4a6fa5,stroke-width:2px,color:#fff;
    classDef decision fill:#6d3b47,stroke:#a55c6e,stroke-width:2px,color:#fff;
    classDef bd fill:#3b592d,stroke:#5c8a46,stroke-width:2px,color:#fff;
    
    class EvaluarModalidad,FinParejas,EvaluarFormato,FinGrupos decision;
    class ShuffleJugadores,BucleParejas,CrearEquipo,AsignarDirecto,ListaEquipos,RecibirCupos,ShuffleEquipos,BucleGrupos,AsignarGrupo logica;
    class GuardarGrupos bd;