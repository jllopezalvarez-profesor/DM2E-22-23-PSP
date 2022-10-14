package es.iesclaradelrey.dm2e2223.ut03hilos.ejercicios.ejercicio06;

public class Hilo extends Thread {
	private String texto;
	private boolean parar = false;
	
	public Hilo(String texto) {
		this.texto = texto;
	}
	
	public void parar() {
		this.parar = true;
	}
	
	@Override
	public void run() {
		while (!parar) {
			System.out.println(texto);
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
