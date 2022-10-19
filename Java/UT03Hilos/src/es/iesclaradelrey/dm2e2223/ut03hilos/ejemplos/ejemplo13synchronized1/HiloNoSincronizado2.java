package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo13synchronized1;

public class HiloNoSincronizado2 extends Thread {
	private static final long NUMERO_ITERACIONES = 100;

	private static int suma = 0;

	public static int getSuma() {
		return suma;
	}

	// Este método, aunque sincronizado, no es static, por lo que bloquea llamadas
	// al método en el mismo objeto, no cualquier objeto de la clase. Esto hace que,
	// al modificar un atributo static no sea "seguro" desde un punto de vista
	// multihilo
	public synchronized int incrementa() {
		for (int i = 0; i < 10; i++) {
			suma++;
		}
		return suma;
	}

	public HiloNoSincronizado2(String nombreHilo) {
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
