package es.iesclaradelrey.dm2e2223.ut03hilos.ejercicios.ejercicio02;

public class HiloSumador extends Thread {
	private static final long CUANTOS_NUMEROS = 2_000_000_000L;
	
	private int factor = 1;
	
	public HiloSumador(String nombreHilo, int factor) {
		super(nombreHilo);
		this.factor = factor;
	}
	
	public HiloSumador(String nombreHilo) {
		this(nombreHilo, 1);
	}

	public void run() {
		System.out.printf("Iniciando el hilo sumador '%s' con factor %d.\n", this.getName(), this.factor);
		long suma = 0;
		for (long i=1; i<=CUANTOS_NUMEROS;i++) {
			suma+=(this.factor*i);
			
		}
			
		System.out.printf("La suma de los primeros %d numeros usando factor %d es %d\n", CUANTOS_NUMEROS, this.factor, suma);
		System.out.printf("Soy el hilo '%s' y he terminado.\n", this.getName());
	}
}
