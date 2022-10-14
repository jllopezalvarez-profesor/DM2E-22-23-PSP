package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo08daemon;

public class Lanzador {

	public static void main(String[] args) throws InterruptedException {

		HiloThread hilo = new HiloThread();
		// Si comentamos esta línea no acaba hasta que acabe el hilo
		hilo.setDaemon(true);
		hilo.start();
		System.out.println("Ya he lanzado el hilo");
		// Dormimos el hilo pricncipal para que de tiempo a ver algo
		// de lo que hace el demonio
		Thread.sleep(5000);
		System.out.println("Finaliza el programa");

	}

}
