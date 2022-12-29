package org.ex1evrec.lopezjoseluis.ejercicio01;

import java.util.Random;

public class Cliente extends Thread {
	private static final int MIN_COMPRA = 5;
	private static final int MAX_COMPRA = 10;
	private static final int MIN_SLEEP = 1000;
	private static final int MAX_SLEEP = 2000;
	
	private Random rnd;
	private Almacen almacen;

	public Cliente(String nombre, Almacen almacen) {
		super(nombre);
		this.rnd = new Random();
		this.almacen = almacen;
	}

	@Override
	public void run() {

		try {
			while (true) {
				int numCajasAComprar = rnd.nextInt(MIN_COMPRA, MAX_COMPRA);
				
				// Comprar
				almacen.comprar(numCajasAComprar, getName());
				
				// Llevar la compra a casa
				Thread.sleep(rnd.nextInt(MIN_SLEEP, MAX_SLEEP));
				
				if (Thread.interrupted()) {
					throw new InterruptedException();
				}
			}

		} catch (InterruptedException e) {
			System.out.printf("El cliente %s ha sido interrumpido.\n", this.getName());
		}
	}

}
