package org.ordinaria.ev1.jllopezalvarez.ejercicio2;

public class Programa {
	private static final int VALOR_INFERIOR = 17;
	private static final int VALOR_SUPERIOR = 97;
	private static final int NUM_HILOS = 5;
	private static final int NUMS_POR_HILO = 5;
	private static final long LIMITE_TIEMPO = 5000;

	public static void main(String[] args) throws InterruptedException {

		GeneradorPrimos[] hilos = new GeneradorPrimos[NUM_HILOS];

		for (int i = 0; i < hilos.length; i++) {
			hilos[i] = new GeneradorPrimos(i, VALOR_INFERIOR, VALOR_SUPERIOR, NUMS_POR_HILO);
		}

		for (GeneradorPrimos generadorPrimos : hilos) {
			generadorPrimos.start();
		}

		Thread.sleep(LIMITE_TIEMPO);

		for (GeneradorPrimos generadorPrimos : hilos) {
			generadorPrimos.interrupt();
		}
		
		for (GeneradorPrimos generadorPrimos : hilos) {
			generadorPrimos.join();
		}

		System.out.println("Fin del programa. Números generados:");
		System.out.println(GeneradorPrimos.getGenerados());
	}

}
