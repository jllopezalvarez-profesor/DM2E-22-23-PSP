package es.iesclaradelrey.dm2e2223.ut03hilos.ejercicios.ejercicio11;

public class CalculadoraMedia extends Thread {

	private int[] numeros;
	private int posicionInicial;
	private int posicionFinal;

	private double media;

	public CalculadoraMedia(int[] numeros, int posicionInicial, int posicionFinal) {
		this.numeros = numeros;
		this.posicionInicial = posicionInicial;
		this.posicionFinal = posicionFinal;
	}

	public double getMedia() {
		return media;
	}

	public void run() {
		// Usamos long por si hay muchos números y se desborda el tipo int;
		long suma = 0;

		// Acumulamos la suma
		for (int i = this.posicionInicial; i <= this.posicionFinal; i++) {
			suma += this.numeros[i];
		}

		// Calculamos la media
		this.media = (double) suma / ((this.posicionFinal - this.posicionInicial) + 1);
	}

}
