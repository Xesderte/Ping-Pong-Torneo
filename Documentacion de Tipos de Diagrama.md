
2. Diagramas de Flujo / Actividad (El "Cuándo")
Análisis: Muestra el ciclo de vida del torneo y las decisiones lógicas. Es el camino que recorren los datos desde que creas el torneo hasta que hay un campeón.

Estrategia: Aquí es donde graficaremos el comportamiento de tu motor lógico (Java). Por ejemplo: ilustrar cómo el sistema detecta un empate, entra al Criterio 1, si persiste pasa al Criterio 2, y así sucesivamente. Es vital para no dejar "callejones sin salida" en la programación.

3. Diagramas de Secuencia (El "Cómo se comunican")
Análisis: Este es el diagrama más importante para tu arquitectura elegida. Mostrará la línea de tiempo del "ping-pong" de información entre tus tecnologías.

Estrategia: Dibujaremos qué pasa exactamente cuando el organizador hace clic en algo. Por ejemplo: El usuario registra un resultado en el Frontend (Tauri/Electron) ➡️ El frontend envía un JSON por un endpoint REST ➡️ El Backend (Java) recibe el dato, calcula los nuevos puntajes ➡️ Java guarda todo en SQLite ➡️ Java devuelve la tabla actualizada al Frontend ➡️ El Frontend redibuja la pantalla.

4. Diagrama Entidad-Relación - DER (El "Dónde se guarda")
Análisis: Es el mapa arquitectónico de tu base de datos SQLite. Definirá las tablas, sus columnas y cómo se conectan entre sí para que no haya datos duplicados ni pérdida de información.

Estrategia: Analizaremos las entidades clave (Torneos, Equipos, Partidos, Sets) y definiremos las relaciones (Ej: "Un partido tiene muchos sets", "Un equipo pertenece a un grupo"). Esto será la base exacta para que escribas tus sentencias SQL en Java.