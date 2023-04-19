package ejemplos.monitor;

import java.util.LinkedList;
import java.util.Queue;

public class Monitor {
	private Queue<Integer> cola = new LinkedList<>();

	private int tamanioMaximo;

	public Monitor(int tamanioMaximo) {
		this.tamanioMaximo = tamanioMaximo;
	}

	public synchronized void add(int numero) throws InterruptedException {
		while (this.cola.size() >= this.tamanioMaximo) {

			System.out.println("Un hilo se para porque la cola se ha llenado");
			wait();
		}

		// Esto está mal porque no está en el mismo while que la condición de tamaño.
		// Podría darse el caso de que la cola tuviera más de tamanioMaximo elementos
		while (!cola.isEmpty() && (cola.peek() % 2 != numero % 2)) {
			wait();
		}

		cola.add(numero);

		notifyAll();
	}

	public synchronized int getFirst() throws InterruptedException {
		while (this.cola.isEmpty()) {
			System.out.println("Un hilo se para porque no hay nada en la cola");
			wait();
		}

		int numero = this.cola.poll();

		notifyAll();

		return numero;
	}

	public synchronized int size() {
		return this.cola.size();
	}

}
