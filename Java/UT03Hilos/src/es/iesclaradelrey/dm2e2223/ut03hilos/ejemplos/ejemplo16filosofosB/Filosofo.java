package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo16filosofosB;

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
	private boolean esZurdo;

	public Filosofo(int numero, Palillo palilloIzquierdo, Palillo palilloDerecho, boolean esZurdo) {
		this.numero = numero;
		this.palilloIzquierdo = palilloIzquierdo;
		this.palilloDerecho = palilloDerecho;
		this.esZurdo = esZurdo;
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

				// Coge primer palillo según sea zurdo o no
				if (this.esZurdo)
					palilloIzquierdo.coger(this);
				else
					palilloDerecho.coger(this);

				// Espera un poco para coger el palillo derecho
				esperar();

				// Coge segundo palillo según sea zurdo o no
				if (this.esZurdo)
					palilloDerecho.coger(this);
				else
					palilloIzquierdo.coger(this);

				// Come
				comer();

				// Deja palillos. El orden en que los deja es irrelevante, no es un problema de
				// cara a interbloqueos.
				palilloIzquierdo.dejar();
				palilloDerecho.dejar();

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
