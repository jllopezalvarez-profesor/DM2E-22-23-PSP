package org.ordinaria.ev1.jllopezalvarez.ejercicio2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneradorPrimos extends Thread {
	private static final int TIEMPO_ESPERA = 250;

	private static List<Integer> numerosGenerados = new ArrayList<>();

	private static Random random = new Random();

	private final int id;
	private final int minimo;
	private final int maximo;
	private final int cuantosGenerar;

	private int cuantosGenerados;

	public GeneradorPrimos(int id, int minimo, int maximo, int cuantosGenerar) {
		this.id = id;
		this.minimo = minimo;
		this.maximo = maximo;
		this.cuantosGenerar = cuantosGenerar;
		this.cuantosGenerados = 0;
	}

	@Override
	public void run() {
		try {
			System.out.printf("Hilo %d iniciando la generación de %d números primos aleatorios entre %d y %d.\n",
					this.id, this.cuantosGenerar, this.minimo, this.maximo);
			while (cuantosGenerados < this.cuantosGenerar) {
				int numAleatorio = random.nextInt(minimo, maximo + 1);
				if (esPrimo(numAleatorio)) {
					System.out.printf("Hilo %d - Generado el nº %d.\n", this.id, numAleatorio);
					addNumeroGenerado(numAleatorio);
					cuantosGenerados++;
				}
				Thread.sleep(TIEMPO_ESPERA);
				if (Thread.interrupted()) {
					throw new InterruptedException();
				}
			}
			System.out.printf("Hilo %d finaliza la generación de %d números primos aleatorios entre %d y %d.\n",
					this.id, this.cuantosGenerar, this.minimo, this.maximo);
		} catch (InterruptedException e) {
			System.out.printf(
					"Hilo %d interrumpido. Generados %d números primos aleatorios entre %d y %d, de los %d pedidos.\n",
					this.id, this.cuantosGenerados, this.minimo, this.maximo, this.cuantosGenerar);
		}

	}

	public static synchronized List<Integer> getGenerados() {
		return new ArrayList<>(numerosGenerados);
	}

	private synchronized void addNumeroGenerado(int numero) {
		numerosGenerados.add(numero);
	}

	private boolean esPrimo(int numero) {
		if (numero < 2)
			return false;
		if (numero == 2)
			return true;
		if (numero % 2 == 0)
			return false;
		for (int divisor = 3; divisor <= Math.sqrt(numero); divisor += 2) {
			if ((numero % divisor) == 0)
				return false;
		}
		return true;
	}

}
