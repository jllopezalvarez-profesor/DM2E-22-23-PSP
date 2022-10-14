package es.iesclaradelrey.dm2e2223.ut03hilos.ejercicios.ejercicio05;

public class Lanzador {

	private static int NUM_HILOS = 100;

	public static void main(String[] args) throws InterruptedException {
		
		Thread hilos[] = new Thread[NUM_HILOS];
		
		// Crear hilos
		for(int i =  0; i<NUM_HILOS; i++) {
			hilos[i] = new Hilo("Hilo nº"+ (i+1));
		} 
		
		// Arrancarlos:
		for (Thread hilo : hilos) {
			hilo.start();
		}
		
		// Esperar a todos los hilos
		for (Thread hilo : hilos) {
			hilo.join();
		}
		
		System.out.println("Los hilos han terminado.");
		System.out.printf("La suma final es %d\n", Hilo.getSuma());
	}

}
