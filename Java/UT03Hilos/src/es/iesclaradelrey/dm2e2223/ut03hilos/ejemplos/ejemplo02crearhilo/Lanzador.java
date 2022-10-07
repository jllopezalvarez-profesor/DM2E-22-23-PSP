package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo02crearhilo;

public class Lanzador {

	public static void main(String[] args) {
		HiloRunnable hiloRunnable = new HiloRunnable();
		Thread hilo = new Thread(hiloRunnable);
		hilo.start();
		System.out.println("Ya he lanzado el hilo");

	}

}
