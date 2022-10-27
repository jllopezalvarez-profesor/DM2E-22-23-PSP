package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo14monitor;

public class Alumno extends Thread {
	
	private Saludos saludos;
	
	public Alumno(String name, Saludos saludos) {
		super(name);
		this.saludos = saludos;
	}
	
	@Override
	public void run() {
		System.out.printf("El alumno %s ha llegado a clase.\n", getName());
		saludos.SaludoAlumno(this.getName());
	}
}
