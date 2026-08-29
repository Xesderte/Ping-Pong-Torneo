# 📐 Arquitectura del Sistema: Análisis de Diagramas Requeridos

A continuación se detalla la justificación y necesidad analítica de cada uno de los diagramas propuestos para la construcción del Gestor Adaptativo de Torneos.

---

## 👥 1. Diagrama de Casos de Uso (Interacción Usuario-Sistema)

* **Por qué realizarlo:** Este diagrama es fundamental porque la arquitectura del aplicativo se basa en una división estricta de responsabilidades entre dos actores principales: el Organizador (Usuario Humano) y el Sistema (Motor Lógico)[cite: 5].
* **Análisis de su necesidad:** Permitirá mapear visualmente quién hace qué a lo largo de las cinco fases del torneo[cite: 3, 5]. Ayudará a delimitar las pantallas que interactúan con el Organizador (como seleccionar formatos, configurar reglas o armar llaves manualmente en eliminatorias) frente a las operaciones invisibles que debe ejecutar el Sistema (como realizar el mini-sorteo aleatorio de equipos, calcular el fixture o procesar la puntuación Sonneborn-Berger)[cite: 3, 5].

---

## 🔄 2. Diagramas de Flujo o Actividad (Algoritmos Lógicos)

* **Por qué realizarlo:** El sistema cuenta con reglas de negocio altamente condicionales y matemáticas que no pueden dejarse a la libre interpretación del programador[cite: 3, 4, 5].
* **Análisis de su necesidad:** Es críticamente necesario para modelar el "Motor Estricto de Desempate"[cite: 3]. Si hay un empate en los puntos base (Pts), el diagrama debe mostrar el flujo de decisión en cascada a través de los 8 criterios consecutivos (desde enfrentamiento directo hasta calidad del rival)[cite: 3, 4]. También es vital para diagramar la lógica de clasificación, ya que el sistema debe bifurcar su comportamiento dependiendo de si un grupo tiene una cantidad par de equipos (clasifica la mitad exacta) o impar (clasifica la mitad redondeada hacia abajo y envía un sobrante al repechaje)[cite: 3, 4].

---

## ⏱️ 3. Diagrama de Secuencia (Línea de Tiempo y Transaccionalidad)

* **Por qué realizarlo:** Durante la "Fase 3: Desarrollo del Torneo", el aplicativo calcula las posiciones en tiempo real cada vez que ocurre un evento[cite: 3, 5].
* **Análisis de su necesidad:** Necesitamos diagramar el "ping-pong" de información cronológico[cite: 3]. Por ejemplo, cuando el Organizador registra los puntos exactos de cada set al finalizar un encuentro, el diagrama de secuencia explicará cómo esa petición viaja al motor lógico, cómo el sistema calcula al ganador, asigna los puntos base predefinidos y actualiza en la base de datos todas las métricas globales (PJ, PG, DS, DP, etc.) antes de devolver la vista actualizada a la pantalla[cite: 3, 5].

---

## 🚥 4. Diagrama de Estados (Ciclo de Vida de las Entidades)

* **Por qué realizarlo:** El torneo es un ecosistema vivo donde los equipos y los partidos pueden sufrir alteraciones drásticas conocidas como "Manejo de Excepciones (El Caos)"[cite: 3, 4, 5].
* **Análisis de su necesidad:** Las entidades del sistema cambian de estado[cite: 3]. Un equipo o jugador puede pasar de un estado "Activo" a "Retirado" en caso de un abandono total[cite: 3, 4, 5]. El diagrama de estados debe modelar cómo esa transición dispara una regla automática en el sistema para asignarle 0 puntos (derrotas) en todos sus partidos pasados y futuros, y cómo altera el estado de sus rivales sumándoles los puntos de victoria[cite: 3, 4, 5].

---

## 🗄️ 5. Diagrama Entidad-Relación o de Clases (Arquitectura de Datos)

* **Por qué realizarlo:** El aplicativo debe gestionar formatos altamente parametrizables (Liga o Fase de Grupos) y métricas detalladas que requieren un historial preciso[cite: 3, 4, 5].
* **Análisis de su necesidad:** Debemos estructurar cómo se guardará la información en la base de datos[cite: 3]. Este diagrama explicará cómo una entidad "Torneo" se relaciona con entidades "Reglas" (almacenando la cantidad de sets, límite de puntos y regla de ventaja)[cite: 3, 4, 5]. Además, justificará la necesidad de relacionar "Equipos" con un registro detallado de "Partidos" y "Sets", lo cual es obligatorio para poder auditar visualmente el historial de enfrentamientos directos si se requiere desempatar[cite: 3, 5]. También deberá soportar la reconfiguración independiente de reglas durante la transición a las llaves eliminatorias[cite: 3, 5].