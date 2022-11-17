package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo16filosofosD;

public class Palillo {

	private static final int MIN_TIEMPO_ESPERA = 50;
	private static final int MAX_TIEMPO_ESPERA = 100;

	public int numero;
	private boolean disponible;
	private Filosofo quienLoUsa;

	public Palillo(int numero) {
		this.numero = numero;
		this.disponible = true;
		this.quienLoUsa = null;
	}

	/**
	 * 
	 * @param filosofo el hilo que intenta tomar el control del palillo
	 * @return true si se ha podido tomar control del palillo, false en caso
	 *         contrario
	 * @throws InterruptedException
	 */

	public synchronized boolean coger(Filosofo filosofo) throws InterruptedException {
		if (!disponible) {
			int tiempoEspera = ((int) Math.random() * (MAX_TIEMPO_ESPERA - MIN_TIEMPO_ESPERA)) + MIN_TIEMPO_ESPERA;
			wait(tiempoEspera);
			// Si al salir del wait no está disponible, devolvemos false. Si lo está,
			// tomamos control del palillo.
			if (!disponible)
				return false;
		}

		System.out.printf("El filosofo %d coge el palillo %d.\n", filosofo.getNumero(), this.numero);
		this.quienLoUsa = filosofo;
		this.disponible = false;
		return true;
	}

	public synchronized void dejar() throws InterruptedException {
		System.out.printf("El filosofo %d deja el palillo %d.\n", this.quienLoUsa.getNumero(), this.numero);
		this.quienLoUsa = null;
		this.disponible = true;
		notifyAll();
	}
}
