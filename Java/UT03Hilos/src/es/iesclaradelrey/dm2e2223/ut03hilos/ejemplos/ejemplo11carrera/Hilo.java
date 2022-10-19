package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo11carrera;

public class Hilo extends Thread {
	private static final long NUMERO_ITERACIONES = 100;
	private static final int TIEMPO_SUENIO = 1;

	private static int suma = 0;

	public static int getSuma() {
		return suma;
	}

	public Hilo(String nombreHilo) {
		super(nombreHilo);
	}

	public void run() {
		System.out.printf("Iniciando el hilo '%s'.\n", this.getName());
		for (int i = 0; i < NUMERO_ITERACIONES; i++) {
			// Distanciamos la lectura de la escritura, para forzar más que se produzcan
			// condiciones de carrera
			int x = suma;
			try {
				sleep(TIEMPO_SUENIO);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			suma = x + 1;
			System.out.printf("Número vale %d ahora\n", suma);
		}
		System.out.printf("Soy el hilo '%s' y he terminado.\n", this.getName());
	}

}
