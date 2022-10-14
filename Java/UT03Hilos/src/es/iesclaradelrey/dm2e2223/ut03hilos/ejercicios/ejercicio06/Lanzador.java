package es.iesclaradelrey.dm2e2223.ut03hilos.ejercicios.ejercicio06;

public class Lanzador {

	public static void main(String[] args) throws InterruptedException {
		Hilo hiloTic= new Hilo("TIC");
		Hilo hiloTac = new Hilo("TAC");
		
		hiloTic.start();
		hiloTac.start();
		
		hiloTic.join();
		hiloTac.join();

		System.out.println("Los hilos han terminado.");
	}

}
