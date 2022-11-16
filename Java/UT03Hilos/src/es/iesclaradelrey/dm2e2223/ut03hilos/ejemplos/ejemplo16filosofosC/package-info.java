/**
 * Ejemplo del problema de los filósofos. Para resolverlo usamos monitores para
 * implementar los palillos. Para resolver el problema de interbloqueos en este
 * caso no dejamos que se sienten todos los filósosfos a la mesa a la vez. Si
 * nay n filósosfos, sólo n-1 pueden estar sentados a la ves. Como queda un
 * sitio libre, no se produce el ciclo de bloqueos. Para controlar que no se
 * sientan más de n-1 filósosfos se usa un semáforo. El semáforo se inicializa a
 * n-1 en el lanzador de hilos, y se pasa como parámetro al constructor.
 */
package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo16filosofosC;