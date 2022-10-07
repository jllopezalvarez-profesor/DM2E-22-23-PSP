package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo06esperarhilos;

import java.lang.Thread;

public class Lanzador {

	public static void main(String[] args) throws InterruptedException {
		Hilo hilo01 = new Hilo("Hilo A", 5);
		Hilo hilo02 = new Hilo("Hilo B", 12);
		Hilo hilo03 = new Hilo("Hilo C", 8);

		hilo01.start();
		hilo02.start();
		hilo03.start();

		System.out.println(
				"Ya he lanzado los dos hilos. Voy a esperar a que acaben, de forma activa, mirando su estado.");

		while (hilo01.getState() != Thread.State.TERMINATED || hilo02.getState() != Thread.State.TERMINATED
				|| hilo03.getState() != Thread.State.TERMINATED)
		{
			// Me duermo 1 segundo para que la espera no sea demasiado "intensa" con el procesador.
			Thread.sleep(1000);
		}

		System.out.println("Los hilos han terminado.");
		

	}

}
