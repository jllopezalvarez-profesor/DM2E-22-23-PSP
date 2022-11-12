package es.iesclaradelrey.dm2e2223.ut03hilos.ejercicios.ejercicio12;

import java.lang.Thread.UncaughtExceptionHandler;

public class Programa {

	private static int TAMANIO_COLA = 5;
	private static int MAX_NUMERO_GENERAR = 10;
	private static int TIEMPO_SUENIO_GENERADORES_MS = 5;
	private static int TIEMPO_SUENIO_CALCULADORAS_MS = 10_000;

	public static void main(String[] args) {

		class GestorExcepciones implements UncaughtExceptionHandler {

			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.err.println("Error en hilo...");
				e.printStackTrace();
			}

		}

		GestorExcepciones gestorExcepciones = new GestorExcepciones();

		ColaNumeros cola = new ColaNumeros(TAMANIO_COLA);
		GeneradorNumeros generador = new GeneradorNumeros(1, cola);
		CalculadoraFactorial calc1 = new CalculadoraFactorial(1, TIEMPO_SUENIO_CALCULADORAS_MS, cola);
		CalculadoraFactorial calc2 = new CalculadoraFactorial(2, TIEMPO_SUENIO_CALCULADORAS_MS, cola);
		CalculadoraFactorial calc3 = new CalculadoraFactorial(3, TIEMPO_SUENIO_CALCULADORAS_MS, cola);
		CalculadoraFactorial calc4 = new CalculadoraFactorial(4, TIEMPO_SUENIO_CALCULADORAS_MS, cola);
		CalculadoraFactorial calc5 = new CalculadoraFactorial(5, TIEMPO_SUENIO_CALCULADORAS_MS, cola);

		generador.setUncaughtExceptionHandler(gestorExcepciones);
		calc1.setUncaughtExceptionHandler(gestorExcepciones);
		calc2.setUncaughtExceptionHandler(gestorExcepciones);
		calc3.setUncaughtExceptionHandler(gestorExcepciones);
		calc4.setUncaughtExceptionHandler(gestorExcepciones);
		calc5.setUncaughtExceptionHandler(gestorExcepciones);

		generador.start();
		calc1.start();
		calc2.start();
		calc3.start();
		calc4.start();
		calc5.start();
		
		try {
			generador.join();
		} catch (InterruptedException e) {
			System.err.println("No puedo esperar al generador porque ya se había interrumpido.");
			e.printStackTrace();
		}
		
		calc1.interrupt();
		calc2.interrupt();
		calc3.interrupt();
		calc4.interrupt();
		calc5.interrupt();

	}

}
