package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo17semaphore;

import java.util.concurrent.Semaphore;

public class Lanzador {
	private static final int NUM_CLIENTES = 100;
	private static final int MAX_CLIENTES_TIENDA = 10;

	public static void main(String[] args) {
		
		// La puerta se simula con un semáforo
		Semaphore puertaTienda = new Semaphore(MAX_CLIENTES_TIENDA);
		
		// Creamos los hilos clientes y los arrancamos
		for (int i = 0; i < NUM_CLIENTES; i++) {
			Cliente c = new Cliente(i+1, puertaTienda);
			c.start();
		}

		// No esperamos, no hace falta.

	}

}
