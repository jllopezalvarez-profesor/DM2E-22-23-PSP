package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo15monitor2;

public class Saludos {

	private boolean profeHaSaludado = false;

	public synchronized void SaludoAlumno(String nombreAlumno) {
		if (profeHaSaludado) {
			System.out.printf("%s dice: Siento llegar tarde.\n", nombreAlumno);
		} else {
			System.out.printf("%s dice: Voy a esperar al profe para saludar.\n", nombreAlumno);
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println("Interrupción mientras espero al profesor.");
				e.printStackTrace();
			}
			System.out.printf("%s dice: Hola profesor.\n", nombreAlumno);
		}

	}

	public synchronized void SaludoProfesor() {
		System.out.println("Hola chicos y chicas!!!");
		profeHaSaludado = true;
		notifyAll();
	}
}
