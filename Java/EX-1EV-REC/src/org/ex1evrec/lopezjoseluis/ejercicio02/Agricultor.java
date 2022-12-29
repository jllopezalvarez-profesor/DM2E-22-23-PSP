package org.ex1evrec.lopezjoseluis.ejercicio02;

import java.util.Random;

public class Agricultor extends Thread {
	private static final int MIN_PRODUCCION = 10;
	private static final int MAX_PRODUCCION = 20;
	private static final int MIN_SLEEP = 1000;
	private static final int MAX_SLEEP = 2000;
	
	private Random rnd;
	private Almacen almacen;

	public Agricultor(String nombre, Almacen almacen) {
		super(nombre);
		this.rnd = new Random();
		this.almacen = almacen;
	}

	@Override
	public void run() {

		try {
			while (true) {
				int numCajasAEntregar = rnd.nextInt(MIN_PRODUCCION, MAX_PRODUCCION);
				
				// Entregar
				almacen.entregar(numCajasAEntregar, getName());
				
				// Irse a cultivar
				Thread.sleep(rnd.nextInt(MIN_SLEEP, MAX_SLEEP));
				
				if (Thread.interrupted()) {
					throw new InterruptedException();
				}
			}

		} catch (InterruptedException e) {
			System.out.printf("El agricultor %s ha sido interrumpido.\n", this.getName());
		}
	}

}
