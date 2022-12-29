package org.ex1evrec.lopezjoseluis.ejercicio02;

public class Programa {

	private static final int NUM_AGRICULTORES = 3;
	private static final int NUM_CLIENTES = 3;
	private static final int CAPACIDAD_ALMACEN = 200;
	private static final int OBJETIVO_CAJAS_VENDIDAS = 500;
	private static final long TIEMPO_ESPERA_ACTIVA = 1000;

	public static void main(String[] args) throws InterruptedException {
		Almacen almacen = new Almacen(CAPACIDAD_ALMACEN);
		Thread[] agricultores = new Thread[NUM_AGRICULTORES];
		for (int i = 0; i < agricultores.length; i++) {
			Agricultor a = new Agricultor("Agricultor " + (i + 1), almacen);
			agricultores[i] = a;
			a.start();
		}

		Thread[] clientes = new Thread[NUM_CLIENTES];
		for (int i = 0; i < clientes.length; i++) {
			Cliente c = new Cliente("Cliente " + (i + 1), almacen);
			clientes[i] = c;
			c.start();
		}

		while (almacen.getTotalCajasVendidas() < OBJETIVO_CAJAS_VENDIDAS) {
				// Hacemos espera activa con sleep.
				Thread.sleep(TIEMPO_ESPERA_ACTIVA);
			}

		for (Thread agricultor : agricultores) {
			agricultor.interrupt();
		}

		for (Thread cliente : clientes) {
			cliente.interrupt();
		}

		for (Thread agricultor : agricultores) {
			agricultor.join();
		}

		for (Thread cliente : clientes) {
			cliente.join();
		}
		
		System.out.printf("Programa finalizado. Total de cajas vendidas: %d.\n", almacen.getTotalCajasVendidas());
		
	}

}
