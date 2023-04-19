package ejemplos.monitor;

import java.util.Queue;
import java.util.Random;

public class Productor extends Thread {

	private int id;
	private int cantidadAGenerar;
	private Monitor cola;
	private Random rnd = new Random();

	public Productor(int id, int cantidadAGenerar, Monitor cola) {
		this.id = id;
		this.cantidadAGenerar = cantidadAGenerar;
		this.cola = cola;
	}

	@Override
	public void run() {
		try {
			int cantidadGenerados = 0;
			while (cantidadGenerados < this.cantidadAGenerar) {
				
				int numeroGenerado = rnd.nextInt(1000);
				this.cola.add(numeroGenerado);
				cantidadGenerados++;
				
				Thread.sleep(100);
				
				if (Thread.interrupted()) {
					throw new InterruptedException();
				}
			}

		} catch (InterruptedException e) {
			System.out.printf("El hilo productor %s ha sido interrumpido\n", this.id);
		}

	}
}
