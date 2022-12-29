package org.ex1evrec.lopezjoseluis.ejercicio02;

public class Almacen {
	private final int capacidadAlmacen;
	private int cajasEnAlmacen;
	private int totalCajasVendidas;

	public Almacen(int capacidadAlmacen) {
		this.capacidadAlmacen = capacidadAlmacen;
		this.cajasEnAlmacen = 0;
		this.totalCajasVendidas = 0;
	}

	public synchronized void entregar(int numCajasAEntregar, String nombreAgricultor) throws InterruptedException {
		// Esperar si no hay espacio suficiente para las cajas
		while ((cajasEnAlmacen + numCajasAEntregar) > capacidadAlmacen) {
			System.out.printf("El agricultor %s espera porque no hay espacio para las %d cajas que quiere entregar.\n",
					nombreAgricultor, numCajasAEntregar);
			wait();
		}

		// Compramos las cajas
		cajasEnAlmacen += numCajasAEntregar;
		System.out.printf("El agricultor %s ha entregado %d cajas.\n", nombreAgricultor, numCajasAEntregar);

		// Avisamos a los demás de que hay cambios
		notifyAll();
	}

	public synchronized void comprar(int numCajasAComprar, String nombreCliente) throws InterruptedException {
		// Esperar si no hay suficientes cajas en el almacen
		while (cajasEnAlmacen < numCajasAComprar) {
			System.out.printf("El cliente %s espera porque no hay las %d cajas que quiere comprar.\n", nombreCliente,
					numCajasAComprar);
			wait();
		}

		// Compramos las cajas
		cajasEnAlmacen -= numCajasAComprar;
		totalCajasVendidas += numCajasAComprar;
		System.out.printf("El cliente %s ha comprado %d cajas.\n", nombreCliente, numCajasAComprar);

		// Avisamos a los demás de que hay cambios
		notifyAll();
	}

	public int getTotalCajasVendidas() {
		return totalCajasVendidas;
	}

}
