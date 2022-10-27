package es.iesclaradelrey.dm2e2223.ut03hilos.ejercicios.ejercicio08;

public class Programa {

	// Es posible que si este número no es muy grande no haya suficientes hilos
	// peleando por el recurso y no se produzca la condición de carrera. Aún sin
	// usar synchronized en los métodos.
	private static final int NUM_OPERADORES = 10_000;
	private static final double SALDO_INICIAL = 100_000;
	private static final double MIN_A_INGRESAR = 1_000;
	private static final double MAX_A_INGRESAR = 10_000;

	public static void main(String[] args) {
		// Creamos el objeto saldo, con el saldo inicial
		// Este objeto es el que compartirán todos los hilos
		Saldo saldoCompartido = new Saldo(SALDO_INICIAL);

		// Creamos un array de hilos para guardar los operadores;
		Thread[] operadores = new OperadorCuenta[NUM_OPERADORES];

		// Acumulador para saber cuánto debería tener el saldo al terminar
		// Comenzamos con el saldo inicial.
		double totalEsperado = saldoCompartido.getSaldo();

		// Creamos los hilos
		for (int i = 0; i < NUM_OPERADORES; i++) {
			// Calculamos la cantidad para el operador, y la acumulamos para verificar al
			// final.
			double cantidadOperador = (Math.random() * (MAX_A_INGRESAR - MIN_A_INGRESAR)) + MIN_A_INGRESAR;
			totalEsperado += cantidadOperador;
			operadores[i] = new OperadorCuenta(saldoCompartido, "Operador " + (i + 1), cantidadOperador);
		}

		// Arrancamos los hilos
		for (Thread operador : operadores) {
			operador.start();
		}

		// Esperamos a que todos acaben
		for (Thread operador : operadores) {
			try {
				operador.join();
			} catch (InterruptedException e) {
				System.err.printf(
						"No se ha podido esperar al operador '%s' porque estaba interrumpido.\nDetalle de la excepción:\n",
						operador.getName());
				e.printStackTrace();
			}
		}

		// Comprobamos si el saldo final es el correcto.
		System.out.println("Han finalizado todos los operadores.");
		System.out.printf("El saldo esperado era %.4f, el saldo final es %.4f\n", totalEsperado,
				saldoCompartido.getSaldo());

	}

}
