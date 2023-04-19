package ejemplos.monitor;

import java.util.Random;

public class Consumidor extends Thread{

	private int id;
	private Monitor cola;
	private Random rnd = new Random();

	public Consumidor(int id, Monitor cola) {
		this.id = id;
		this.cola = cola;
	}

	@Override
	public void run() {
		try {
			while (true) {
				
				int numero = this.cola.getFirst();
				//System.out.printf("El consumidor %s está consumiendo el número %s\n", this.id, numero);
				
				Thread.sleep(100);
				
				if (Thread.interrupted()) {
					throw new InterruptedException();
				}
			}

		} catch (InterruptedException e) {
			System.out.printf("El hilo consumidor %s ha sido interrumpido\n", this.id);
		}

	}
}
