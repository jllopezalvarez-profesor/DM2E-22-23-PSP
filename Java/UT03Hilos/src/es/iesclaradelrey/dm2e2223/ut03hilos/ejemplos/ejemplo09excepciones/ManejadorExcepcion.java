package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo09excepciones;

import java.lang.Thread.UncaughtExceptionHandler;

public class ManejadorExcepcion implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.err.println("Se ha producido una excepción en un hilo...");
		System.err.println("Detalles:");
		e.printStackTrace();
	}
}
