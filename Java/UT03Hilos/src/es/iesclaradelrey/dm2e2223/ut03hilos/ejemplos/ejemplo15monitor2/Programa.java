package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo15monitor2;

public class Programa {
	public static void main(String[] args) {
		Saludos saludos = new Saludos();
		Profesor profesor = new Profesor(saludos);
		Alumno alumno1 = new Alumno("Paco", saludos);
		Alumno alumno2 = new Alumno("Hector", saludos);
		Alumno alumno3 = new Alumno("Jihad", saludos);

		profesor.start();
		alumno1.start();
		alumno2.start();
		alumno3.start();
	}
	
	

}
