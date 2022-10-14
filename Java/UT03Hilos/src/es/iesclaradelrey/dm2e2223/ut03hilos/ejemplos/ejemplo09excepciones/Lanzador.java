package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo09excepciones;

public class Lanzador {

	public static void main(String[] args) throws InterruptedException {
		HiloThread hilo = new HiloThread();
		hilo.setUncaughtExceptionHandler(new ManejadorExcepcion());
		hilo.start();
		System.out.println("Ya he lanzado el hilo");
		

	}

}
