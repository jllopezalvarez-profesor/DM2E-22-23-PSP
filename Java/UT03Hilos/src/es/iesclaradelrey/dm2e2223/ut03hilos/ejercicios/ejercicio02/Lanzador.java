package es.iesclaradelrey.dm2e2223.ut03hilos.ejercicios.ejercicio02;

public class Lanzador {

	public static void main(String[] args) throws InterruptedException {
		HiloSumador hiloSumador = new HiloSumador("El sumador");
		HiloSumador hiloRestador = new HiloSumador("El restador", -1);
		
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
