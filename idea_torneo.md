# Gestor Adaptativo de Torneos

## 1. Concepto General

* **Disciplina/Juego:** Un aplicativo de escritorio diseñado para registrar, gestionar y hacer seguimiento de torneos de cualquier tipo, tomando como base dinámicas como el Ping Pong.
* **Resumen de la idea:** Crear una plataforma que permita registrar equipos, guardar la información del torneo, gestionar los encuentros y automatizar las tablas de posiciones, con flexibilidad para elegir diferentes formatos de competición.

### Formatos Disponibles
* **Liga (Todos contra todos):** Opción de configurar a un solo encuentro (solo ida) o dos encuentros (ida y vuelta).
* **Fase de Grupos + Eliminatorias:** Grupos de todos contra todos (solo ida).

### Lógica de Clasificación en Grupos
* **Grupos Pares:** Clasifica exactamente la mitad (Ej: 4 equipos = 2 clasificados).
* **Grupos Impares:** Clasifica la mitad redondeada hacia abajo (Ej: 5 equipos / 2 = 2.5 = 2 clasificados directos). El equipo que queda con el porcentaje sobrante (el 3er lugar) va a una zona de "Repechaje".

### Sistema de Puntuación, Configuración y Desempates
* **Puntuación Base:** Se asignan puntos predefinidos por Victoria y Derrota (Ej: 3 puntos por victoria, 1 por derrota, 0 por no presentación) para ordenar la tabla general.
* **Cantidad de sets (Parametrizable):** El organizador puede definir el número de sets por partido (formatos impares: 1, 3, 5 o 7 sets).
* **Puntos por set (Parametrizable):** El puntaje objetivo para ganar un set es configurable (ej. al primero que llegue a 5, 7 u 11 puntos).
* **Regla de ventaja (Parametrizable):** El sistema contempla matemáticamente la diferencia obligatoria de 2 puntos para definir el set en caso de empate en el límite del puntaje.

**Criterios de Desempate (en orden estricto):**
1. **Enfrentamiento directo:** Si dos equipos empatan en puntos, clasifica primero quien haya ganado el partido disputado entre ambos.
2. **Diferencia de sets:** Cantidad de sets ganados menos la cantidad de sets perdidos a lo largo del torneo.
3. **Mayor cantidad de sets ganados:** Quien haya ganado más sets en total.
4. **Menor cantidad de sets perdidos:** Quien haya perdido menos sets en total.
5. **Diferencia de puntos:** Puntos anotados en total menos puntos recibidos a lo largo del torneo.
6. **Mayor cantidad de puntos a favor:** Quien haya anotado más puntos en total.
7. **Menor cantidad de puntos en contra:** Quien haya recibido menos puntos totales por parte de sus rivales.
8. **Calidad del Rival (Sistema Sonneborn-Berger):** Si persiste el empate absoluto, se suman los puntos finales que obtuvieron en la tabla los rivales a los que cada equipo logró ganarle. Quien le haya ganado a oponentes "más fuertes" clasifica primero.

---

## 2. Mecánicas de Aleatoriedad

* **Formación de equipos al azar:** Si el torneo es individual, los participantes se inscriben tal cual. Si el torneo es en parejas, el sistema debe tomar la lista de participantes individuales y agruparlos de a dos personas de forma 100% aleatoria (nombrando a los equipos resultantes como Equipo A, Equipo B, Equipo C, etc.).
* **Sorteo de Grupos/Ligas:** Una vez conformados los equipos (o jugadores individuales), el sistema los asignará de manera aleatoria a las distintas tablas de grupos o a la tabla general de la Liga.

---

## 3. Formato del Torneo (Avance)

* **Participantes/Equipos:** Sin límite, cantidad completamente libre y configurable por el organizador.
* **Sistema de Avance (Sugerencia base):** Los primeros y segundos lugares obtienen estatus de pase directo a eliminatorias.
* **Gestión de sobrantes:** Los lugares de "Repechaje" (en grupos impares) u otros puestos quedan a disposición de las decisiones del organizador.

---

## 4. Logística y Manejo del Caos

* **Plataforma de organización (Eliminatorias Libres):** El sistema no armará las llaves de eliminación de forma automática y rígida. Al terminar la fase de grupos, el sistema mostrará la **lista completa de todos los equipos** ordenados por su rendimiento en la tabla, mostrando tanto a los teóricamente "Clasificados" como a los "No Clasificados".
* **Libertad de emparejamiento:** El organizador tendrá la libertad absoluta para seleccionar a cualquier equipo de esta lista (permitiendo así crear llaves de repechaje para los últimos puestos si lo desea), definir las instancias (cuartos, semis, etc.) y decidir manualmente qué equipo se enfrenta a qué equipo (Ej: cruzar el 1ro del A contra el 2do del B, o hacer cruces asimétricos).

### Regla de Abandono y Edición de Equipos
* **Abandono parcial:** Si una persona de un equipo de dos abandona, el sistema permitirá editar manualmente el nombre del jugador para incluir un reemplazo, sin afectar el torneo.
* **Abandono total (o de un jugador individual):** Si un equipo completo abandona, el sistema declarará automáticamente como "Derrota" todos sus encuentros (pasados y futuros) y se le sumarán los puntos correspondientes de victoria a todos los demás equipos que se enfrentaron o debían enfrentarse a ellos. Así no se altera la matemática de la tabla.
