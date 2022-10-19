package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo12atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class Hilo extends Thread {
	private static final long NUMERO_ITERACIONES = 1000;

	private static AtomicInteger suma = new AtomicInteger(0);

	public static int getSuma() {
		return suma.get();
	}

	public Hilo(String nombreHilo) {
		super(nombreHilo);
	}

	public void run() {
		System.out.printf("Iniciando el hilo '%s'.\n", this.getName());
		for (int i = 0; i < NUMERO_ITERACIONES; i++) {
			int valor = suma.addAndGet(1);
			System.out.printf("Número vale %d ahora\n", valor);
			try {
				sleep(10); // Para que de tiempo a que arranquen y se "interpongan" otros hilos
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.printf("Soy el hilo '%s' y he terminado.\n", this.getName());
	}

}
