package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo18executors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Lanzador02FixedThreadPool {

	private static final int NUM_HILOS = 10;
	private static final int NUM_HILOS_CONCURRENTES = 2;
	private static final int NUM_ITERACIONES = 5;
	private static final int TIEMPO_SUENIO = 250;

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		ArrayList<Callable<String>> hilos = new ArrayList<>();

		for (int i = 0; i < NUM_HILOS; i++) {
			Callable<String> hilo = new HiloCallable(String.format("Hilo %d", i + 1), NUM_ITERACIONES, TIEMPO_SUENIO);
			hilos.add(hilo);
		}

		ExecutorService executor = Executors.newFixedThreadPool(NUM_HILOS_CONCURRENTES);
		List<Future<String>> resultados = executor.invokeAll(hilos);
		
		for (Future<String> future : resultados) {
			if (future.isDone()) {
				System.out.println(future.get());
			}
		}
		
		executor.shutdown();

	}

}
