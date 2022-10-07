package es.iesclaradelrey.dm2e2223.ut03hilos.ejercicios.ejercicio01;

public class HiloSumador extends Thread {
	private static final long CUANTOS_NUMEROS = 2_000_000_000L;
	
	private int factor = 1;
	
	public HiloSumador(int factor) {
		this.factor = factor;
	}
	
	public HiloSumador() {
		this(1);
	}

	public void run() {
		System.out.printf("Iniciando el hilo sumador con factor %d.\n", this.factor);
		long suma = 0;
		for (long i=1; i<=CUANTOS_NUMEROS;i++) {
			suma+=(this.factor*i);
			
		}
			
		System.out.printf("La suma de los primeros %d numeros usando factor %d es %d\n", CUANTOS_NUMEROS, this.factor, suma);
	}
}
