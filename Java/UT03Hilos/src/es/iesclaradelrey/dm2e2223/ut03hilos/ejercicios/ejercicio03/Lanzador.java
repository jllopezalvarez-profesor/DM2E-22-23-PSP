package es.iesclaradelrey.dm2e2223.ut03hilos.ejercicios.ejercicio03;

import java.lang.Thread.State;

public class Lanzador {

	public static void main(String[] args) throws InterruptedException {
		HiloSumador hiloSumador = new HiloSumador("El sumador");
		HiloSumador hiloRestador = new HiloSumador("El restador", -1);
		
		long tiempo = System.currentTimeMillis();
		hiloSumador.start();
		hiloRestador.start();
		
		while ((hiloSumador.getState() != State.TERMINATED) || (hiloRestador.getState() != State.TERMINATED))
		{
			// Duermo una décima de segundo
			Thread.sleep(100);
		}

		System.out.println("Los hilos han terminado.");
		System.out.printf("Han tardado %d ms", System.currentTimeMillis()-tiempo);
	}

}
