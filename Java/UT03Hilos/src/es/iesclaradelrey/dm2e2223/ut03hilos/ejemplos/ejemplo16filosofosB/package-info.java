/**
 * Ejemplo del problema de los filósofos. Para resolverlo usamos monitores para
 * implementar los palillos. En esta versión se resuelve el problema del
 * interbloqueo haciendo que algunos filósofos sean zurdos (cogen primero el
 * palillo izquierdo) y otros diestros (cogen primero el derecho). Esto elimina
 * el ciclo en elgrafo de bloqueos, en el que todos los filósofos cogían primero
 * el izquierdo y quedaban esperando indefinidamente al derecho.
 */
package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo16filosofosB;