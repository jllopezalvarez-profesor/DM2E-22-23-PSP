package ejemplos.monitor;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Programa {
	private static final int NUM_PRODUCTORES = 6;
	private static final int NUM_CONSUMIDORES = 5;
	private static final int CANTIDAD_NUMS_PRODUCTOR = 100;

	public static void main(String[] args) throws InterruptedException {
		// Queue<Integer> cola = new LinkedList<>();
		// Queue<Integer> cola = new ConcurrentLinkedQueue<>();
		Monitor monitor = new Monitor(10);

		Productor[] productores = new Productor[NUM_PRODUCTORES];
		for (int i = 0; i < NUM_PRODUCTORES; i++) {
			productores[i] = new Productor(i, CANTIDAD_NUMS_PRODUCTOR, monitor);
			productores[i].start();
		}
		Consumidor[] consumidores = new Consumidor[NUM_CONSUMIDORES];
		for (int i = 0; i < NUM_CONSUMIDORES; i++) {
			consumidores[i] = new Consumidor(i, monitor);
			consumidores[i].start();
		}
//		for (Productor productor : productores) {
//			productor.start();
//		}
//		for (Consumidor consumidor : consumidores) {
//			consumidor.start();
//		}
//		
		for (Productor productor : productores) {
			productor.join();
		}
		
		Thread.sleep(20000);
		
		for (Consumidor consumidor : consumidores) {
			consumidor.interrupt();
		}
		
		System.out.printf("Cantidad de números en la cola: %s\n", monitor.size());

	}
}
