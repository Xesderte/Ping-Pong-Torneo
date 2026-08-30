# 🎯 Análisis de Casos de Uso: Gestor Adaptativo de Torneos

## 👥 1. Identificación de los Entes (Actores)

Al ser una aplicación de escritorio gestionada por ti, la arquitectura es muy limpia. Tenemos dos actores principales:

* **👤 El Organizador (Usuario Humano):** Es el único actor que interactúa con la pantalla. Toma las decisiones, ingresa los datos y tiene la última palabra.
* **⚙️ El Sistema (Motor Lógico):** Es el actor "invisible". Recibe las órdenes del organizador, aplica la matemática interna (sorteos aleatorios, desempates de Sonneborn-Berger, cálculo de puntos) y devuelve la información procesada.

---

## 🚀 2. Análisis de las Acciones (Los Casos de Uso)

A continuación, se detalla todo lo que el **Organizador** necesita poder hacer dentro de la aplicación, ordenado cronológicamente según el ciclo de vida del torneo:

### 🛠️ Fase 1: Preparación Estructural y Formato
* **🏆 Seleccionar Formato del Torneo:** Elegir el tipo de competición principal: Liga (Todos contra todos) o Fase de Grupos (con posteriores eliminatorias).
* **📅 Configurar Modalidad de Encuentros:** Definir si los enfrentamientos serán a un solo encuentro (solo ida) o a dos encuentros (ida y vuelta).
* **⚙️ Configurar Reglas de Partido:** Definir el nombre del torneo, la cantidad de sets a jugar, el límite de puntos por set y si aplica la regla de diferencia/ventaja de 2 puntos.

### 🎲 Fase 2: Inscripción, Formación y Sorteos
* **📝 Inscribir Participantes:** Cargar la lista de nombres de las personas que van a jugar.
* **🔀 Formación de Equipos (Mini-Sorteo):** Definir si el torneo es de modalidad individual o grupal. Si es en equipos de a dos personas, pedirle al sistema que realice un mini-sorteo para agrupar a los jugadores de forma aleatoria.
* **📊 Asignación según Formato:** 
  * *Si es Fase de Grupos:* El sistema no impone restricciones sobre el tamaño. **El Organizador define libremente la cantidad de grupos y cuántos cupos tendrá cada uno**  Una vez definida esta estructura (simétrica o asimétrica), se le pide al sistema que realice el sorteo principal para distribuir aleatoriamente a los equipos en los cupos creados.
  * *Si es Liga (Todos contra todos):* No se requiere sorteo de división; el sistema simplemente genera de forma automática el fixture con todos los cruces necesarios.

### ⚔️ Fase 3: Desarrollo del Torneo (Fase de Grupos / Liga)
* **✍️ Registrar Resultados:** Cargar los puntos anotados por cada equipo por cada set al finalizar un partido. El sistema se encargará de calcular quién ganó el encuentro y sumar los puntos correspondientes a la tabla general.
* **👀 Visualizar Tablas (Métricas Globales):** Ver cómo van las posiciones calculadas en tiempo real. Para garantizar la transparencia de los desempates, el sistema mostrará una tabla detallada con las siguientes métricas globales para cada equipo:
  * **PJ, PG, PP:** Partidos Jugados, Ganados y Perdidos.
  * **Pts:** Puntos base del torneo (Score principal).
  * **DS, SG, SP:** Diferencia de Sets, Sets Ganados y Sets Perdidos.
  * **DP, PF, PC:** Diferencia de Puntos, Puntos a Favor y Puntos en Contra.
  * **SB:** Puntuación Sonneborn-Berger (Calidad del rival).
* **🔍 Historial de Enfrentamientos (Vista Detallada):** Dado que el Criterio 1 ("Enfrentamiento Directo") no es un valor numérico global, la fila de cada equipo en la tabla será interactiva (cliqueable). Al seleccionarla, se desplegará una ventana o panel secundario mostrando el historial exacto de todos los partidos jugados por ese equipo, sus rivales y los resultados detallados de cada set. Esto permite auditar los empates directos de forma limpia sin sobrecargar la pantalla principal.

### ⚠️ Fase 4: Manejo de Excepciones (El "Caos")
* **🔄 Reemplazar Jugador:** Editar el nombre de un participante si alguien abandona un dúo.
* **❌ Registrar Abandono Total:** Marcar a un equipo como retirado para que el sistema le asigne automáticamente derrotas en sus encuentros futuros (0 puntos) y sume los puntos a sus rivales.

### 🏆 Fase 5: Transición y Eliminatorias (La parte libre)
* **📋 Ver Lista de Rendimiento:** Consultar el ranking general ordenado de todos los equipos al terminar la fase inicial, independientemente de si clasificaron o no.
* **🧩 Armar Llaves Manualmente (Mapeo Libre):** Seleccionar qué equipo juega contra qué equipo en las instancias de eliminación directa, decidiendo libremente quién entra (ideal para repechajes y cruces a medida).
* **⚙️ Reconfigurar Reglas de Eliminatorias (Parametrización Específica):** Antes de iniciar los cruces, el sistema permite al organizador sobreescribir las reglas de partido exclusivamente para las instancias finales. Se podrá redefinir de forma independiente:
  * **La cantidad de sets:** (Formatos impares: 1, 3, 5 o 7).
  * **Los puntos por set:** (Límite objetivo: 5, 7 u 11 puntos).
* **⏭️ Avanzar Fases Eliminatorias:** Registrar los resultados (cargando los puntos de cada set modificado) para determinar quién gana en octavos, cuartos, semis, etc., hasta declarar un campeón definitivo.