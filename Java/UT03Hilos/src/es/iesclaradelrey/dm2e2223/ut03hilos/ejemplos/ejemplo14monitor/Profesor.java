package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo14monitor;

public class Profesor extends Thread {
	
	private Saludos saludos;
	
	public Profesor(Saludos saludos) {
		this.saludos = saludos;
	}
	
	@Override
	public void run() {
		System.out.println("El profesor ha llegado a clase.");
		saludos.SaludoProfesor();
	}

}
