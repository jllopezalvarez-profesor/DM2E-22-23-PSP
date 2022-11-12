package es.iesclaradelrey.dm2e2223.ut03hilos.ejercicios.ejercicio12;

public class CalculadoraFactorial extends Thread {
	private int id;
	private int tiempoSuenioMs;
	private ColaNumeros cola;

	public CalculadoraFactorial(int id, int tiempoSuenioMs, ColaNumeros cola) {
		this.id = id;
		this.tiempoSuenioMs = tiempoSuenioMs;
		this.cola = cola;
	}

	@Override
	public void run() {
		// Mensaje indicando que hemos iniciado el hilo
		System.out.printf("Se ha iniciado la calculadora %d.\n", id);
		try {
			while (true) {
				// Obtenemos un número de la cola. Esto puede pasar el hilo a WAITING y puede
				// producir
				// excepción
				int numero = cola.Obtener(id);
				// Calculamos el factorial y lo mostramos en pantalla
				double fac = factorial(numero);
				System.out.printf("Calculadora %d - El factorial de %d es %.0f.\n", id, numero, fac);
				// Dormimos
				System.out.printf("La calculadora %d se duerme %d ms.\n", id, tiempoSuenioMs);
				sleep(tiempoSuenioMs);
				
				// Compruebo si me han interrumpido
				if (Thread.interrupted()) {
					throw new InterruptedException();
				}
			}
		} catch (InterruptedException e) {
			System.out.printf("La calculadora %d finaliza porque ha sido interrumpida.\n", id);
			e.printStackTrace();
		}

	}

	private double factorial(int numero) {
		double f = 1;
		while (numero > 1) {
			f = f * numero--;
		}
		return f;
	}
}
