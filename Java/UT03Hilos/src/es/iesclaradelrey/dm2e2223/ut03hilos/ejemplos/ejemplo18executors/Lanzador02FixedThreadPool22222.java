package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo18executors;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Lanzador02FixedThreadPool22222 {

	private static final int NUM_ITERACIONES = 5;
	private static final int TIEMPO_SUENIO = 250;
	private static final int CADA_CUANTO = 1_000;
	private static final int TIEMPO_ESPERA_EXECUTOR = 10_000;

	public static void main(String[] args) throws InterruptedException {

		Runnable hilo = new HiloRunnable("Hilo repetitivo", NUM_ITERACIONES, TIEMPO_SUENIO);

		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

		executor.scheduleWithFixedDelay(hilo, 3000, CADA_CUANTO, TimeUnit.MILLISECONDS);

		System.out.printf("El programa principal se duerme %d ms.\n", TIEMPO_ESPERA_EXECUTOR);
		Thread.sleep(TIEMPO_ESPERA_EXECUTOR);
		System.out.println("El programa principal ha despertado. Detiene el executor.");
		executor.shutdownNow();
		System.out.printf("El programa principal espera un máximo de %d ms a que se detenga el executor.\n", TIEMPO_ESPERA_EXECUTOR);
		executor.awaitTermination(TIEMPO_ESPERA_EXECUTOR, TimeUnit.MILLISECONDS);
		System.out.println("El programa principal ha salido de la espera para que termine el executor.");

	}

}
