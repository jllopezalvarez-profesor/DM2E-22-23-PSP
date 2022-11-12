package es.iesclaradelrey.dm2e2223.ut03hilos.ejercicios.ejercicio12;

import java.util.Scanner;

public class GeneradorNumeros extends Thread {
	private int id;
	private ColaNumeros cola;

	public GeneradorNumeros(int id, ColaNumeros cola) {
		this.id = id;
		this.cola = cola;
	}

	@Override
	public void run() {
		// Creo Scanner, que es un recurso que debe liberarse
		Scanner scanner = new Scanner(System.in);

		// Mensaje indicando que hemos iniciado el hilo
		System.out.printf("Se ha iniciado el generador %d.\n", id);
		try {
			// Preguntamos una primera vez
			System.out.print("Introduce un número. Menor que cero para terminar: ");
			int numero = Integer.parseInt(scanner.nextLine());
			
			while (numero > 0) {
				
				// Lo añadimos a la cola. Esto puede pasar el hilo a WAITING y puede producir
				// excepción
				cola.Aniadir(numero, id);
			
				// En cada vuelta del bucle, compruebo si he sido interrumpido.
				if (Thread.interrupted()) {
					throw new InterruptedException();
				}

				// Volvemos a preeguntar
				System.out.print("Introduce un número. Menor que cero para terminar: ");
				numero = Integer.parseInt(scanner.nextLine());
			}
		} catch (InterruptedException e) {
			System.out.printf("El generador %d finaliza porque ha sido interrumpido.\n", id);
			e.printStackTrace();
		} finally {
			scanner.close();
		}

	}

}
