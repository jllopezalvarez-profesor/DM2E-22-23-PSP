package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo18executors;

public class HiloRunnable implements Runnable {

	String nombreHilo;
	int numIteraciones;
	int tiempoSuenio;

	public HiloRunnable(String nombreHilo, int numIteraciones, int tiempoSuenio) {
		this.nombreHilo = nombreHilo;
		this.numIteraciones = numIteraciones;
		this.tiempoSuenio = tiempoSuenio;
	}

	@Override
	public void run() {
		try {
			for (int i = 1; i <= this.numIteraciones; i++) {
				System.out.printf("Iteración %d de hilo %s\n", i, nombreHilo);
				Thread.sleep(tiempoSuenio);
				if (Thread.interrupted()) {
					throw new InterruptedException();
				}
			}
		} catch (InterruptedException e) {
			System.out.printf("El hilo %s ha sido interrumpido\n", nombreHilo);
		} finally {
			System.out.printf("El hilo %s ha terminado\n", nombreHilo);
		}
	}

}
