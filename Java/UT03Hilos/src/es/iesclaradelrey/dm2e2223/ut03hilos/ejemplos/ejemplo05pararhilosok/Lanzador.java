package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo05pararhilosok;

public class Lanzador {

	public static void main(String[] args) throws InterruptedException {
		Hilo hilo01 = new Hilo();
		Hilo hilo02 = new Hilo();

		hilo01.start();
		hilo02.start();
		System.out.println("Ya he lanzado los dos hilos. Voy a esperar 5 segundos y los voy a parar.");
		Thread.sleep(5000);
		hilo01.pararHilo();
		hilo02.pararHilo();
		System.out.println("Ya he he dicho a los dos hilos que se paren.");

	}

}
