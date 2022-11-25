package es.iesclaradelrey.dm2e2223.ut03hilos.ejercicios.ejercicio10;

import java.util.LinkedList;

public class ColaNumeros {
	private LinkedList<Integer> cola;
	private int tamanio;

	public ColaNumeros(int tamanio) {
		this.tamanio = tamanio;
		this.cola = new LinkedList<>();
	}
	
	public int getOcupacionCola() {
		return this.cola.size();
	}

	public synchronized void Aniadir(int numero, int quienAniade) throws InterruptedException {
		// Mientras la cola esté llena, tenemos que esperar
		while (this.cola.size() >= tamanio) {
			System.out.printf("La cola está llena. El generador %d espera hasta que haya espacio.\n", quienAniade);
			this.wait();
		}

		// Ha quedado sitio. Añadimos el elemento al final de la cola
		this.cola.addLast(numero);

		// Notificamos a todos que ha cambiado algo.
		this.notifyAll();
	}

	public synchronized int Obtener(int quienPide) throws InterruptedException {
		// Mientras la cola esté vacía, tenemos que esperar
		while (this.cola.isEmpty()) {
			System.out.printf("La cola está vacía. La calculadora %d espera hasta que haya un elemento.\n", quienPide);
			this.wait();
		}

		// Ya hay sitio. Obtenemos el primer elemento de la cola, eliminándolo.
		int valor = this.cola.removeFirst();

		// Notificamos a todos que ha cambiado algo.
		this.notifyAll();
		
		// Devolvemos el valor obtenido
		return valor;
	}

}
