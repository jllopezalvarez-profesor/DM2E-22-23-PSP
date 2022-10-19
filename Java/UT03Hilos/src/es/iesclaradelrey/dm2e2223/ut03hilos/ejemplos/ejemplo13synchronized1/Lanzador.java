package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo13synchronized1;

public class Lanzador {

	private static int NUM_HILOS = 100;

	public static void main(String[] args) throws InterruptedException {

		Thread hilos[] = new Thread[NUM_HILOS];

		// Crear hilos
		for (int i = 0; i < NUM_HILOS; i++) {
			// Para no sincronizados (sin nada)...
			// hilos[i] = new HiloNoSincronizado1("Hilo nº " + (i + 1));

			// Para mal sincronizados (con synchronized en instancia)...
			 //hilos[i] = new HiloNoSincronizado2("Hilo nº " + (i + 1));

			// Para sincronizados (con synchronized en static)...
			hilos[i] = new HiloSincronizadoStatic("Hilo nº " + (i + 1));
		}

		// Arrancarlos:
		for (Thread hilo : hilos) {
			hilo.start();
		}

		// Esperar a todos los hilos
		for (Thread hilo : hilos) {
			hilo.join();
		}

		System.out.println("Los hilos han terminado.");
		// Para no sincronizados (sin nada)...
		 //System.out.printf("La suma final es %d\n", HiloNoSincronizado1.getSuma());

		// Para mal sincronizados (con synchronized en instancia)...
		// System.out.printf("La suma final es %d\n", HiloNoSincronizado2.getSuma());

		// Para sincronizados (con synchronized en static)...
		System.out.printf("La suma final es %d\n", HiloSincronizadoStatic.getSuma());
	}

}
