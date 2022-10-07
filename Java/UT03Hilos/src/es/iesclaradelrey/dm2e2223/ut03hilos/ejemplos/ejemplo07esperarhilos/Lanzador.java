package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo07esperarhilos;

public class Lanzador {

	public static void main(String[] args) throws InterruptedException {
		Hilo hilo01 = new Hilo("Hilo A", 5);
		Hilo hilo02 = new Hilo("Hilo B", 12);
		Hilo hilo03 = new Hilo("Hilo C", 8);

		hilo01.start();
		hilo02.start();
		hilo03.start();

		System.out.println("Ya he lanzado los dos hilos. Voy a esperar a que acaben.");

		try {
			hilo01.join();
			hilo02.join();
			hilo03.join();

		} catch (InterruptedException e) {
			System.err.println("Error al hacer el join de hilos");
			e.printStackTrace();
		}
		System.out.println("Los hilos han terminado.");

	}

}
