package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo02crearhilo;

public class HiloRunnable implements Runnable {
	private static final int NUM_CICLOS = 10;
	private static final long MS_SUENIO = 1000;

	public void run() {
		System.out.println("Iniciando el hilo...");
		long idHilo = Thread.currentThread().getId();
		System.out.printf("Mi dentificador es: %d\n", idHilo);
		for (int i = 0; i < NUM_CICLOS; i++) {
			System.out.printf("En el ciclo %d del hilo %d.\n", i, idHilo);
			System.out.printf("El hilo %d se va a dormir %d ms.\n", idHilo, MS_SUENIO);
			try {
				Thread.sleep(MS_SUENIO);
			} catch (InterruptedException e) {
				System.err.printf("Error al dormir el hilo %d\n", idHilo);
				e.printStackTrace();
			}
		}
	}
}
