package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo16filosofosC;

import java.util.Random;
import java.util.concurrent.Semaphore;

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
	private Semaphore semaforoMesa;

	public Filosofo(int numero, Palillo palilloIzquierdo, Palillo palilloDerecho, Semaphore semaforoMesa) {
		this.numero = numero;
		this.palilloIzquierdo = palilloIzquierdo;
		this.palilloDerecho = palilloDerecho;
		this.semaforoMesa = semaforoMesa;
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

				// Antes de poder coger los palillos, el filósofo tiene que sentarse a la mesa, para eso hace un wait del semáforo.
				semaforoMesa.acquire();
				
				// Coge palillo izquierdo
				palilloIzquierdo.coger(this);

				// Espera un poco para coger el palillo derecho
				esperar();

				// Coge palillo derecho
				palilloDerecho.coger(this);

				// Come
				comer();

				// Deja palillos
				palilloIzquierdo.dejar();
				palilloDerecho.dejar();

				// Se levanta de la mesa después de haber dejado los palillos
				semaforoMesa.release();
				
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
		System.out.printf("El filósosfo %d espera %d ms para coger el segundo palillo.\n", this.numero, ESPERA_ENTRE_PALILLOS);
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
