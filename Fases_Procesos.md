# 🏓 Guía de Gestión Técnica: Torneo Adaptativo de Ping Pong

Esta documentación detalla el ciclo de vida completo para la administración de un torneo utilizando el Gestor Adaptativo. La arquitectura de este aplicativo de escritorio se divide entre las decisiones manuales del **Organizador** y el procesamiento matemático del **Sistema**.

---

## ⚙️ Fase 1: Preparación Estructural y Formato

En esta etapa inicial, el Organizador debe establecer las bases del torneo y parametrizar las reglas del juego.

* **Selección del Formato Principal:** El Organizador decide si la competición será una Liga (Todos contra todos) o una Fase de Grupos (con posteriores eliminatorias). 
* **Configuración de Modalidad:** Si se elige Liga, se debe definir si los encuentros serán a un solo partido (solo ida) o a dos encuentros (ida y vuelta). Si se opta por Fase de Grupos, los cruces iniciales serán obligatoriamente de todos contra todos a solo ida.
* **Parametrización de Reglas de Partido:** Se debe configurar la estructura de los encuentros:
  * **Cantidad de sets:** Debe ser un formato impar (1, 3, 5 o 7 sets).
  * **Límite de puntos por set:** El puntaje objetivo para ganar (por ejemplo, 5, 7 u 11 puntos).
  * **Regla de ventaja:** Habilitar si se contempla matemáticamente la diferencia obligatoria de 2 puntos para definir el set en caso de empate.
* **Sistema de Puntuación Base:** Se definen los puntos que el sistema asignará a la tabla general (por ejemplo: 3 puntos por victoria, 1 por derrota, 0 por no presentación).

---

## 📝 Fase 2: Inscripción, Formación y Sorteos

El sistema soporta una cantidad de participantes o equipos completamente libre, sin límite de inscripciones.

* **Carga de Participantes:** El Organizador ingresa la lista con los nombres de todos los jugadores individuales.
* **Formación de Equipos (Mecánica de Aleatoriedad):** 
  * Si el torneo es individual, los participantes se inscriben tal cual en el sistema.
  * Si el torneo es en parejas, el Sistema ejecuta un mini-sorteo 100% aleatorio para agrupar a los individuos de a dos y nombrarlos automáticamente (por ejemplo, Equipo A, Equipo B, Equipo C).
* **Asignación y Generación de Fixture (Según Formato):**
  * **En Fase de Grupos:** El Organizador define manualmente cuántos grupos existirán y los cupos de cada uno, permitiendo que sean simétricos o asimétricos. Luego, el Sistema realiza un sorteo aleatorio principal para distribuir a los equipos en dichos cupos.
  * **En Liga:** No se requiere sorteo de división; el Sistema simplemente genera de forma automática el fixture con todos los cruces necesarios.

---

## 📊 Fase 3: Desarrollo del Torneo y Desempates

Durante la ejecución de los partidos, el Sistema automatiza el cálculo de las posiciones en tiempo real.

* **Registro de Resultados:** Al finalizar cada encuentro, el Organizador carga los puntos exactos anotados por cada equipo por cada set. El Sistema calcula automáticamente quién ganó el encuentro y suma los puntos a la tabla general.
* **Métricas Globales (Visor de Tablas):** El Sistema calcula y muestra las siguientes estadísticas por equipo para garantizar la transparencia:
  * **PJ, PG, PP:** Partidos Jugados, Ganados y Perdidos.
  * **Pts:** Puntos base del torneo.
  * **DS, SG, SP:** Diferencia de Sets, Sets Ganados y Sets Perdidos.
  * **DP, PF, PC:** Diferencia de Puntos, Puntos a Favor y Puntos en Contra.
  * **SB:** Puntuación Sonneborn-Berger.

> **Motor Estricto de Desempate**
> Si el Sistema detecta un empate en los puntos base (Pts), aplicará el siguiente orden algorítmico:
> 1. **Enfrentamiento directo:** Clasifica quien haya ganado el partido disputado entre ambos.
> 2. **Diferencia de sets:** Sets ganados menos sets perdidos totales.
> 3. **Mayor cantidad de sets ganados**.
> 4. **Menor cantidad de sets perdidos**.
> 5. **Diferencia de puntos:** Puntos anotados menos puntos recibidos totales.
> 6. **Mayor cantidad de puntos a favor**.
> 7. **Menor cantidad de puntos en contra**.
> 8. **Calidad del Rival (Sonneborn-Berger):** Suma de los puntos finales obtenidos en la tabla por los rivales a los que el equipo logró ganarle.

* **Auditoría de Historial:** Para evaluar visualmente el Criterio 1, la fila de cada equipo en la tabla es interactiva (cliqueable). El Organizador puede seleccionarla para desplegar un panel con el historial exacto de los partidos jugados y los resultados de cada set por ese equipo.

---

## ⚠️ Fase 4: Manejo de Excepciones (Logística y Caos)

El aplicativo permite gestionar imprevistos sin corromper la estructura matemática del torneo.

* **Abandono Parcial (Reemplazo):** Si una persona de un dúo abandona la competición, el Organizador simplemente edita de forma manual el nombre del participante para incluir al reemplazo, sin afectar el torneo.
* **Abandono Total:** Si un equipo completo (o jugador en modalidad individual) abandona, el Organizador marca al ente como "retirado". El Sistema automatizará asignándole derrotas (0 puntos) en sus encuentros futuros y pasados, sumando los puntos de victoria correspondientes a todos sus rivales para no alterar la matemática de la tabla.

---

## 🏆 Fase 5: Transición y Eliminatorias (Armado Libre)

La fase final está diseñada para evitar llaves rígidas y otorgar control total al Organizador.

* **Lista de Rendimiento General:** Al concluir la fase inicial, el Sistema no arma las llaves automáticamente. En su lugar, muestra el ranking general de todos los equipos (clasificados y no clasificados) ordenados por su rendimiento.
* **Lógica de Clasificación de Grupos:**
  * **Grupos Pares:** Clasifica exactamente la mitad de los integrantes.
  * **Grupos Impares:** Clasifica la mitad redondeada hacia abajo (ej. 5 equipos / 2 = 2 clasificados directos), enviando al equipo sobrante (porcentaje sobrante / 3er lugar) a una zona de "Repechaje".
* **Mapeo Manual de Llaves:** Utilizando la lista de rendimiento, el Organizador selecciona libremente qué equipo juega contra qué equipo (ej. cruzar el 1ro del A contra el 2do del B), definiendo las instancias y permitiendo cruces asimétricos o llaves de repechaje.
* **Reconfiguración de Reglas (Finales):** Antes de iniciar las eliminatorias, el Organizador puede sobreescribir las reglas del partido exclusivamente para estas instancias. Puede redefinir de forma independiente la cantidad de sets (1, 3, 5 o 7) y los puntos por set (5, 7 u 11 puntos).
* **Cierre del Torneo:** Se continúa el registro manual de resultados (cargando los puntos de cada set modificado) en cada instancia (octavos, cuartos, semis) hasta que el Sistema declare a un campeón definitivo.