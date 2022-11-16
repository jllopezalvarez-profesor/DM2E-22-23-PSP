package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo16filosofosD;

import java.util.Random;

public class Filosofo extends Thread {

	// Objeto estático compartido entre filósofos para generar tiempos de espera.
	private static final Random RND = new Random();
	// Límites inferior y superior para los tiempos
	private static final int MIN_SLEEP = 100;
	private static final int MAX_SLEEP = 200;
	// Tiempo espera entre palillos
	private static final int ESPERA_ENTRE_PALILLOS = 200;

	private int numero;
	private Palillo palilloIzquierdo;
	private Palillo palilloDerecho;

	public Filosofo(int numero, Palillo palilloIzquierdo, Palillo palilloDerecho) {
		this.numero = numero;
		this.palilloIzquierdo = palilloIzquierdo;
		this.palilloDerecho = palilloDerecho;
	}

	public int getNumero() {
		return this.numero;
	}

	@Override
	public void run() {

		try {
			while (true) {
				// Piensa un rato
				pensar();

				// Intenta coger palillo izquierdo. Si no puede, se irá a pensar
				if (palilloIzquierdo.coger(this)) {
					// Espera un poco para coger el palillo derecho
					esperar();

					// Intenta ahora coger el palillo derecho. Si no puede, suelta el izquierdo y se
					// irá a pensar
					if (palilloDerecho.coger(this)) {

						// Come
						comer();

						// Deja palillo derecho
						palilloDerecho.dejar();
					}
					// Deja palillo izquierdo, bien porque ha soltado el derecho tras comer, bien
					// porque ha intentado coger el derecho y no ha podido
					palilloIzquierdo.dejar();

				}
				// Vuelta a empezar, pero sólo si no hemos sido interrumpidos.
				if (Thread.interrupted())
					throw new InterruptedException();
			}
		} catch (InterruptedException e) {
			System.out.printf("Se ha interrumpido la ejecución del filósofo %d\n", this.numero);
			e.printStackTrace();
		}

	}

	private void esperar() throws InterruptedException {
		System.out.printf("El filósosfo %d espera %d ms para coger el segundo palillo.\n", this.numero,
				ESPERA_ENTRE_PALILLOS);
		sleep(ESPERA_ENTRE_PALILLOS);
	}

	private void comer() throws InterruptedException {
		int tiempo = RND.nextInt(MIN_SLEEP, MAX_SLEEP);
		System.out.printf("El filósosfo %d come por %d ms.\n", this.numero, tiempo);
		sleep(tiempo);
	}

	private void pensar() throws InterruptedException {
		int tiempo = RND.nextInt(MIN_SLEEP, MAX_SLEEP);
		System.out.printf("El filósosfo %d piensa por %d ms.\n", this.numero, tiempo);
		sleep(tiempo);
	}

}
