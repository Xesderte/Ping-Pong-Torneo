```mermaid
flowchart TD
    %% Inicio del Método
    Start([Inicio: método generarFixture]) --> RecibirDatos[Recibir Lista de Participantes, Formato y Modalidad: Ida/Vuelta]
    
    %% Decisión Principal
    RecibirDatos --> EvaluarFormato{¿Qué formato eligió el usuario?}
    
    %% Rama 1: Fase de Grupos
    EvaluarFormato -->|Fase de Grupos| DividirGrupos[Dividir lista en N Grupos equilibrados]
    DividirGrupos --> BucleGrupos[Por cada Grupo: Tomar su lista de jugadores]
    BucleGrupos --> EvaluarParidad
    
    %% Rama 2: Liga
    EvaluarFormato -->|Liga| TomarTodos[Tomar la lista completa de jugadores]
    TomarTodos --> EvaluarParidad
    
    %% Bloque de Algoritmo Round-Robin
    EvaluarParidad{¿La cantidad de jugadores es Par?}
    EvaluarParidad -->|NO| AgregarLibre[Agregar jugador ficticio 'LIBRE' a la lista]
    EvaluarParidad -->|SÍ| CheckFormatoIdaVuelta{¿Es torneo de Liga?}
    AgregarLibre --> CheckFormatoIdaVuelta
    
    %% Evaluación de Ida o Ida y Vuelta
    CheckFormatoIdaVuelta -->|NO / Grupos| SoloIda[Calcular Fechas: Solo Ida obligatoriamente]
    CheckFormatoIdaVuelta -->|SÍ / Liga| EvaluarModalidad[Calcular Fechas: Si es Ida y Vuelta, duplicar y alternar localías]
    SoloIda --> BucleFechas
    EvaluarModalidad --> BucleFechas
    
    %% Lógica de Rotación por Fecha
    BucleFechas[Iniciar Bucle: Por cada Fecha del Torneo] --> FijarPivot[Fijar Jugador 1 y rotar posiciones del resto]
    FijarPivot --> Emparejar[Armar cruces de la Fecha actual]
    
    Emparejar --> CheckLibre{¿Un partido incluye a 'LIBRE'?}
    CheckLibre -->|SÍ| Descansa[El jugador real tiene fecha libre - No se crea partido]
    CheckLibre -->|NO| CreaPartido[Crear objeto Partido con estado PENDIENTE]
    
    Descansa --> GuardarFechaDB
    CreaPartido --> GuardarFechaDB[(Persistir partidos en SQLite inmediatamente)]
    
    GuardarFechaDB --> FinBucle{¿Se completaron todas las Fechas del torneo?}
    
    %% Salidas de datos (Parcial y Completa)
    FinBucle -->|NO| RetornarParcial[Devolver JSON Parcial con la fecha recién creada]
    FinBucle -->|SÍ| RetornarCompleto[Devolver JSON Completo con todo el Fixture listo]
    
    RetornarParcial --> BucleFechas
    RetornarCompleto --> Fin([Fin del Método])

    %% Estilos
    classDef logica fill:#2b3a42,stroke:#4a6fa5,stroke-width:2px,color:#fff;
    classDef decision fill:#6d3b47,stroke:#a55c6e,stroke-width:2px,color:#fff;
    classDef bd fill:#3b592d,stroke:#5c8a46,stroke-width:2px,color:#fff;
    
    class EvaluarFormato,EvaluarParidad,CheckFormatoIdaVuelta,CheckLibre,FinBucle decision;
    class AgregarLibre,FijarPivot,Emparejar,EvaluarModalidad,SoloIda logica;
    class GuardarFechaDB bd;