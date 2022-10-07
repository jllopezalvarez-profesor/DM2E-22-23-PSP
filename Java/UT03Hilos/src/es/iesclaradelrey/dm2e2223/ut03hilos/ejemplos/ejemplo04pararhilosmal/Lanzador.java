package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo04pararhilosmal;

import es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo01crearhilo.HiloThread;
import es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo02crearhilo.HiloRunnable;

public class Lanzador {

	public static void main(String[] args) throws InterruptedException {
		HiloThread hilo01 = new HiloThread();
		HiloRunnable hiloRunnable = new HiloRunnable();
		Thread hilo02 = new Thread(hiloRunnable);
		hilo01.start();
		hilo02.start();
		System.out.println("Ya he lanzado los dos hilos. Voy a esperar 5 segundos y los voy a parar.");
		Thread.sleep(5000);
		// Esta forma de parar los hilos no es correcta. 
		// Puede conducir a bloqueos, espera infinita, ...
		hilo01.stop();
		hilo02.stop();
		System.out.println("Ya he parado los dos hilos.");

	}

}
