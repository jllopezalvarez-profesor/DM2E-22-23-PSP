package es.iesclaradelrey.dm2e2223.ut03hilos.ejercicios.ejercicio05;

public class Hilo extends Thread {
	private static final long NUMERO_ITERACIONES = 100;

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
			suma++;
			System.out.printf("Número vale %d ahora\n", suma);
			try {
				sleep(10); // Para que de tiempo a que arranquen y se "interpongan" otros hilos
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.printf("Soy el hilo '%s' y he terminado.\n", this.getName());
	}


}
