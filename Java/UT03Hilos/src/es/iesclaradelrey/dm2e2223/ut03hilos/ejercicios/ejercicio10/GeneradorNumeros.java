package es.iesclaradelrey.dm2e2223.ut03hilos.ejercicios.ejercicio10;

public class GeneradorNumeros extends Thread {
	private int id;
	private int tiempoSuenioMs;
	private int maxNumeroAGenerar;
	private ColaNumeros cola;

	public GeneradorNumeros(int id, int tiempoSuenioMs, int maxNumeroAGenerar, ColaNumeros cola) {
		this.id = id;
		this.tiempoSuenioMs = tiempoSuenioMs;
		this.maxNumeroAGenerar = maxNumeroAGenerar;
		this.cola = cola;
	}

	@Override
	public void run() {
		// Mensaje indicando que hemos iniciado el hilo
		System.out.printf("Se ha iniciado el generador %d.\n", id);
		try {
			while (true) {
				// Generamos un número aleatorio:
				int numero = (int) (Math.random() * (maxNumeroAGenerar + 1));
				// Lo añadimos a la cola. Esto puede pasar el hilo a WAITING y puede producir
				// excepción
				cola.Aniadir(numero, id);
				// Dormimos
				System.out.printf("El generador %d se duerme %d ms.\n", id, tiempoSuenioMs);
				sleep(tiempoSuenioMs);
			}
		} catch (InterruptedException e) {
			System.out.printf("El generador %d finaliza porque ha sido interrumpido.\n", id);
			e.printStackTrace();
		}

	}

}
