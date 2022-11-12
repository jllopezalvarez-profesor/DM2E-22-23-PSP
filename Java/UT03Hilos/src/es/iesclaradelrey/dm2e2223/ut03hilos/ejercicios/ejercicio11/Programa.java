package es.iesclaradelrey.dm2e2223.ut03hilos.ejercicios.ejercicio11;

public class Programa {

//	private static int CANTIDAD_NUMEROS = 999_999_999;
	private static int CANTIDAD_NUMEROS = 1_000_000;
	private static int MAX_NUMERO = 10;

	public static void main(String[] args) throws InterruptedException {
		// Creamos y llenamos el array de números
		long inicio = System.currentTimeMillis();
		int[] numeros = new int[CANTIDAD_NUMEROS];
		for (int i = 0; i < CANTIDAD_NUMEROS; i++) {
			numeros[i] = (int) (Math.random() * MAX_NUMERO) + 1;
		}
		long fin = System.currentTimeMillis();
		System.out.printf("Generado array de %d números. Tiempo para crearlo: %d ms.\n", CANTIDAD_NUMEROS,
				fin - inicio);

		// Mostramos el array para poder comprobar datos
		// for (int i : numeros) {
		// System.out.printf("%d ", i);
		// }
		// System.out.println();

		// Cálculo con multihilo.
		inicio = System.currentTimeMillis();
		double media = calcularMediaMultihilo(numeros);
		fin = System.currentTimeMillis();
		System.out.printf("La media calculada con hilos es %.4f. Calculada en %d ms.\n", media, fin - inicio);

		inicio = System.currentTimeMillis();
		media = calcularMedia(numeros);
		fin = System.currentTimeMillis();
		System.out.printf("La media calculada sin hilos es %.4f. Calculada en %d ms.\n", media, fin - inicio);

	}

	private static double calcularMedia(int[] numeros) {
		double media;
		long suma = 0;
		for (int n : numeros) {
			suma += n;
		}
		media = (double) suma / numeros.length;
		return media;
	}

	private static double calcularMediaMultihilo(int[] numeros) throws InterruptedException {
		// Obtenemos el número de hilos en el procesador
		int numHilos = Runtime.getRuntime().availableProcessors();

		// Calculamos el cantidad de números a procesar por cada hilo.
		// Esta división puede no ser exacta, así que habrá que tenerlo en cuenta.
		int numerosPorHilo = numeros.length / numHilos;

		// Creamos un hilo para cada grupo de números
		CalculadoraMedia[] calculadoras = new CalculadoraMedia[numHilos];
		for (int i = 0; i < numHilos; i++) {
			int posicionInicial = i * numerosPorHilo;
			// La división podía no ser exacta, en el último usamos la última posición
			int posicionFinal = (i < numHilos - 1) ? (posicionInicial + numerosPorHilo) - 1 : numeros.length - 1;
			// System.out.printf("Creando calculadora para el rango %d - %d.\n",
			// posicionInicial, posicionFinal);
			CalculadoraMedia calc = new CalculadoraMedia(numeros, posicionInicial, posicionFinal);
			calculadoras[i] = calc;
		}

		// Arrancamos los hilos
		for (CalculadoraMedia calculadoraMedia : calculadoras) {
			calculadoraMedia.start();
		}

		// Esperamos a que acaben
		for (CalculadoraMedia calculadoraMedia : calculadoras) {
			calculadoraMedia.join();
		}

		// Calculamos la media final
		double sumaMedias = 0;
		for (CalculadoraMedia calculadoraMedia : calculadoras) {
			sumaMedias += calculadoraMedia.getMedia();
			// System.out.println(calculadoraMedia.getMedia());
		}

		return sumaMedias / calculadoras.length;
	}

}
