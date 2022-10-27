package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo14monitor;

public class Saludos {
	public synchronized void SaludoAlumno(String nombreAlumno) {
		try {
			wait();
		} catch (InterruptedException e) {
			System.err.println("Interrupción mientras espero al profesor.");
			e.printStackTrace();
		}
		System.out.printf("%s dice: Hola profesor.\n", nombreAlumno);
	}
	
	public synchronized void SaludoProfesor() {
		System.out.println("Hola chicos y chicas!!!");
		notifyAll();
	}
}
