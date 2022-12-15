package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo18executors;

import java.util.concurrent.Callable;

public class HiloCallable implements Callable<String> {

	String nombreHilo;
	int numIteraciones;
	int tiempoSuenio;

	public HiloCallable(String nombreHilo, int numIteraciones, int tiempoSuenio) {
		this.nombreHilo = nombreHilo;
		this.numIteraciones = numIteraciones;
		this.tiempoSuenio = tiempoSuenio;
	}

	@Override
	public String call() throws Exception {
		for (int i=1; i<=this.numIteraciones;i++) {
			System.out.printf("Iteración %d de hilo %s\n", i, nombreHilo);
			Thread.sleep(tiempoSuenio);
		}
		return String.format("El hilo %s ha terminado", nombreHilo);
	}

}
