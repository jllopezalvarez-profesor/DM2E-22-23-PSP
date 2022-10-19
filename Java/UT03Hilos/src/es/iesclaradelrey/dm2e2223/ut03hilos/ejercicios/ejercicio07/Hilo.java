package es.iesclaradelrey.dm2e2223.ut03hilos.ejercicios.ejercicio07;

import javax.swing.JProgressBar;
import javax.swing.JTextField;

public class Hilo extends Thread {

	private int numeroHilo;
	private int periodoSuenio;
	private JProgressBar barraProgreso;
	private JTextField campoProgreso;
	
	public Hilo(int numeroHilo, int periodoSuenio, JProgressBar barraProgreso, JTextField campoProgreso) {
		this.numeroHilo = numeroHilo;
		this.periodoSuenio = periodoSuenio;
		this.barraProgreso = barraProgreso;
		this.campoProgreso = campoProgreso;
	}

	@Override
	public void run() {
		for (int i = this.barraProgreso.getMinimum(); i <= this.barraProgreso.getMaximum(); i++) {
			try {
				this.barraProgreso.setValue(i);
				this.campoProgreso.setText(Integer.toString(i));
				System.out.printf("Hilo %d - Valor actual: %d - Me duermo %d ms.\n", this.numeroHilo, i, this.periodoSuenio);
				sleep(periodoSuenio);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
