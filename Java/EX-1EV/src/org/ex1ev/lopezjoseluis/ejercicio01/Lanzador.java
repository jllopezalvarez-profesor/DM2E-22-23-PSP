package org.ex1ev.lopezjoseluis.ejercicio01;

public class Lanzador {

	private static final int CAPACIDAD_CAJA = 5;
	private static final int NUM_HINCHADORES = 5;
	private static final int NUM_PINCHADORES = 2;
	private static final int TIEMPO_ESPERA_MS = 10_000;

	public static void main(String[] args) {
		CajaGlobos globos = new CajaGlobos(CAPACIDAD_CAJA);

		Hinchador[] hinchadores = new Hinchador[NUM_HINCHADORES];
		for (int i = 0; i < hinchadores.length; i++) {
			hinchadores[i] = new Hinchador(globos, i + 1);
		}

		Pinchador[] pinchadores = new Pinchador[NUM_PINCHADORES];
		for (int i = 0; i < pinchadores.length; i++) {
			pinchadores[i] = new Pinchador(globos, i + 1);
		}

		// Iniciar hilos
		for (int i = 0; i < hinchadores.length; i++) {
			hinchadores[i].start();
		}
		for (int i = 0; i < pinchadores.length; i++) {
			pinchadores[i].start();
		}

		// Esperar el tiempo definido
		try {
			Thread.sleep(TIEMPO_ESPERA_MS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Paramos los hilos
		for (int i = 0; i < hinchadores.length; i++) {
			hinchadores[i].interrupt();
		}
		for (int i = 0; i < pinchadores.length; i++) {
			pinchadores[i].interrupt();
		}
		

	}

}
