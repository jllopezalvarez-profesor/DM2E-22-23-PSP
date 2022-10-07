package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo06esperarhilos;

public class Hilo extends Thread {
	private static final long MS_SUENIO = 500;

	private int numCiclos;

	public Hilo(String nombreHilo, int numCiclos) {
		super(nombreHilo);
		this.numCiclos = numCiclos;
	}

	public void run() {
		System.out.println("Iniciando el hilo...");
		long idHilo = this.getId();
		String nombreHilo = this.getName();
		System.out.printf("Me llamo %s y mi dentificador es: %d\n", nombreHilo, idHilo);
		for (int i = 0; i < numCiclos; i++) {
			System.out.printf("En el ciclo %d del hilo %s.\n", i, nombreHilo);
			System.out.printf("El hilo %s se va a dormir %d ms.\n", nombreHilo, MS_SUENIO);
			try {
				sleep(MS_SUENIO);
			} catch (InterruptedException e) {
				System.err.printf("Error al dormir el hilo %s\n", nombreHilo);
				e.printStackTrace();
			}
		}
		System.out.printf("El hilo %s ha terminado\n", nombreHilo);

	}
}
