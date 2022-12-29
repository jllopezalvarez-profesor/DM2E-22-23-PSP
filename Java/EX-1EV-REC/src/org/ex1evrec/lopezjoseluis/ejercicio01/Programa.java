package org.ex1evrec.lopezjoseluis.ejercicio01;

public class Programa {

	private static final int NUM_AGRICULTORES = 3;
	private static final int NUM_CLIENTES = 3;
	private static final int CAPACIDAD_ALMACEN = 200;

	public static void main(String[] args) {
		Almacen almacen = new Almacen(CAPACIDAD_ALMACEN);
		Thread[] agricultores = new Thread[NUM_AGRICULTORES];
		for (int i = 0; i < agricultores.length; i++) {
			Agricultor a = new Agricultor("Agricultor " + (i + 1), almacen);
			a.start();
			agricultores[i] = a;
		}

		Thread[] clientes = new Thread[NUM_CLIENTES];
		for (int i = 0; i < clientes.length; i++) {
			Cliente c = new Cliente("Cliente " + (i + 1), almacen);
			c.start();
			clientes[i] = c;
		}

	}

}
