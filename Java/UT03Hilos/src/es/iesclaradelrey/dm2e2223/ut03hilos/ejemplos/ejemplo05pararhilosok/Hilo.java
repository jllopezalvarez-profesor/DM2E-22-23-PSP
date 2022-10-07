package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo05pararhilosok;

public class Hilo extends Thread {
	private static final long MS_SUENIO = 1000;
	
	private boolean parar = false;
	
	public void pararHilo() {
		this.parar = true;
	}

	public void run() {
		System.out.println("Iniciando el hilo...");
		long idHilo = this.getId();
		System.out.printf("Mi dentificador es: %d\n", idHilo);
		for (int i = 0; !parar; i++) {
			System.out.printf("En el ciclo %d del hilo %d.\n", i, idHilo);
			System.out.printf("El hilo %d se va a dormir %d ms.\n", idHilo, MS_SUENIO);
			try {
				sleep(MS_SUENIO);
			} catch (InterruptedException e) {
				System.err.printf("Error al dormir el hilo %d\n", idHilo);
				e.printStackTrace();
			}
		}
	}
}
