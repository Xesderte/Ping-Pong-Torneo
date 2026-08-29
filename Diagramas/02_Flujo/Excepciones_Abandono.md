```mermaid
flowchart TD
    %% Inicio del Método
    Start([Inicio: Algoritmo de Abandono Total]) --> RecibirID[Recibir ID del Equipo/Jugador que abandona]
    
    %% Actualización de Estado Base
    RecibirID --> MarcarRetirado[Actualizar estado del ente a 'Retirado']
    
    %% Operación de Base de Datos - Extracción
    MarcarRetirado --> BuscarPartidos[(Consultar DB: Obtener TODOS los partidos pasados y futuros del ente)]
    
    %% Bucle Transaccional
    BuscarPartidos --> IniciarTransaccion[(BEGIN TRANSACTION: Bloquear DB para operación segura)]
    IniciarTransaccion --> BuclePartidos{¿Quedan partidos en la lista por procesar?}
    
    %% Dentro del Bucle
    BuclePartidos -->|SÍ| SeleccionarPartido[Seleccionar el siguiente partido de la lista]
    SeleccionarPartido --> AsignarDerrota[Asignar automáticamente Derrota y 0 puntos al ente retirado]
    AsignarDerrota --> AsignarVictoria[Sumar automáticamente los puntos de victoria correspondientes al Rival]
    AsignarVictoria --> ActualizarMetricas[Recalcular estadísticas del partido: PJ, PG, PP, Puntos]
    ActualizarMetricas --> GuardarPartido[(Actualizar fila del Partido en SQLite)]
    GuardarPartido --> BuclePartidos
    
    %% Cierre de Transacción
    BuclePartidos -->|NO| ConsolidarTransaccion[(COMMIT: Confirmar y guardar todos los cambios en cascada)]
    
    %% Refresco de UI
    ConsolidarTransaccion --> RecalcularTabla[Ejecutar motor para recalcular Tabla de Posiciones Global]
    RecalcularTabla --> RetornarJSON[Devolver JSON con Tabla limpia y matemáticamente intacta]
    RetornarJSON --> Fin([Fin del Método])

    %% Estilos
    classDef logica fill:#2b3a42,stroke:#4a6fa5,stroke-width:2px,color:#fff;
    classDef decision fill:#6d3b47,stroke:#a55c6e,stroke-width:2px,color:#fff;
    classDef bd fill:#3b592d,stroke:#5c8a46,stroke-width:2px,color:#fff;
    
    class BuclePartidos decision;
    class RecibirID,MarcarRetirado,SeleccionarPartido,AsignarDerrota,AsignarVictoria,ActualizarMetricas,RecalcularTabla logica;
    class BuscarPartidos,IniciarTransaccion,GuardarPartido,ConsolidarTransaccion,RetornarJSON bd;