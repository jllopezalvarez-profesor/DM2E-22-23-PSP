package es.iesclaradelrey.dm2e2223.ut03hilos.ejercicios.ejercicio01;

import java.util.Calendar;

public class Lanzador {

	public static void main(String[] args) throws InterruptedException {
		HiloSumador hiloSumador = new HiloSumador(5);
		HiloSumador hiloRestador = new HiloSumador(-5);
		
		long tiempo = System.currentTimeMillis();
		hiloSumador.start();
		hiloRestador.start();
		
		try {
			hiloSumador.join();
			hiloRestador.join();
		} catch (InterruptedException e) {
			System.err.println("Error al hacer el join de hilos");
			e.printStackTrace();
		}
		System.out.println("Los hilos han terminado.");
		System.out.printf("Han tardado %d ms", System.currentTimeMillis()-tiempo);
	}

}
