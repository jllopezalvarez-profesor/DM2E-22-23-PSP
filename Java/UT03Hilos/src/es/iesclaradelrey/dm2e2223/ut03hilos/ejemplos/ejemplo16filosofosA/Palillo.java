package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo16filosofosA;

public class Palillo {
	public int numero;
	private boolean disponible;
	private Filosofo quienLoUsa;

	public Palillo(int numero) {
		this.numero = numero;
		this.disponible = true;
		this.quienLoUsa = null;
	}

	public synchronized void coger(Filosofo filosofo) throws InterruptedException {
		while (!disponible)
			wait();
		System.out.printf("El filosofo %d coge el palillo %d.\n", filosofo.getNumero(), this.numero);
		this.quienLoUsa = filosofo;
		this.disponible = false;
	}

	public synchronized void dejar() throws InterruptedException {
		System.out.printf("El filosofo %d deja el palillo %d.\n", this.quienLoUsa.getNumero(), this.numero);
		this.quienLoUsa = null;
		this.disponible = true;
		notifyAll();
	}
	
}
