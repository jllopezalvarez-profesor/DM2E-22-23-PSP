package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo13synchronized1;

public class HiloSincronizadoStatic extends Thread {
	private static final long NUMERO_ITERACIONES = 100;

	private static int suma = 0;

	public static int getSuma() {
		return suma;
	}

	// Este método, sincronizado y static bloquea llamadas al método se hagan desde
	// donde se hagan. Desde un objeto de la clase o desde fuera, con
	// HiloSincronizado.incrementa. Esto hace que ya sea seguro para
	// modificar un atributo static, desde un punto de vista multihilo
	public static synchronized int incrementa() {
		for (int i = 0; i < 10; i++) {
			suma++;
		}
		return suma;
	}

	public HiloSincronizadoStatic(String nombreHilo) {
		super(nombreHilo);
	}

	public void run() {
		System.out.printf("Iniciando el hilo '%s'.\n", this.getName());
		for (int i = 0; i < NUMERO_ITERACIONES; i++) {
			int valor = this.incrementa();
			System.out.printf("Número vale %d ahora\n", valor);
			try {
				sleep(10); // Para que de tiempo a que arranquen y se "interpongan" otros hilos
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.printf("Soy el hilo '%s' y he terminado.\n", this.getName());
	}

}
