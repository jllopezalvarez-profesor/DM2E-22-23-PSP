package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo10excepciones;

import java.lang.Thread.UncaughtExceptionHandler;

public class Lanzador {

	public static void main(String[] args) throws InterruptedException {
		HiloThread hilo = new HiloThread();
		// Al igual que con los manejadores de eventos en Swing, podemos usar clases
		// anónimas
		hilo.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				System.err.print("Se ha producido una excepción en un hilo");
				System.err.println(" (capturado en clase anónima)...");
				System.err.println("Detalles:");
				e.printStackTrace();
			}
		});
		hilo.start();
		System.out.println("Ya he lanzado el hilo");

	}

}
