package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo03crearhilo;

import es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo01crearhilo.HiloThread;
import es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo02crearhilo.HiloRunnable;

public class Lanzador {

	public static void main(String[] args) {
		HiloThread hilo01 = new HiloThread();
		HiloRunnable hiloRunnable = new HiloRunnable();
		Thread hilo02 = new Thread(hiloRunnable);
		hilo01.start();
		hilo02.start();
		System.out.println("Ya he lanzado los dos hilos");

	}

}
