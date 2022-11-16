package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo16filosofosD;

public class Lanzador {
	private static final int NUM_FILOSOFOS = 5;
	private static final int NUM_PALILLOS = NUM_FILOSOFOS;

	public static void main(String[] args) {
		// Creamos los palillos
		Palillo[] palillos = new Palillo[NUM_PALILLOS];
		for (int i = 0; i < palillos.length; i++) {
			palillos[i] = new Palillo(i + 1); // Para numerar desde 1 usamos i+1;
		}

		// Creamos los filososofos. A cada uno le damos dos palillos.
		// El de la izquierda será el que tiene el mismo índice en el array.
		// El de la derecha será el del índice + 1;
		Filosofo[] filosofos = new Filosofo[NUM_FILOSOFOS];
		for (int i = 0; i < filosofos.length; i++) {
			// Por claridad uso variables para los palillos
			Palillo izquierdo = palillos[i];
			// Para el derecho, si nos pasamos, volvemos a 0
			Palillo derecho = palillos[(i + 1) % palillos.length];
			filosofos[i] = new Filosofo(i + 1, izquierdo, derecho);

		}

		// Arrancamos los hilos.
		for (int i = 0; i < filosofos.length; i++) {
			filosofos[i].start();
		}

		// No hacemos join, no es necesario...

	}

}
