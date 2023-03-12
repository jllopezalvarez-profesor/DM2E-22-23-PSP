package org.ordinaria.ev1.jllopezalvarez.ejercicio1;

public class Admision extends Thread {
	private final int tiempoEntrePacientes;
	private SalaEspera salaEspera;

	public Admision(SalaEspera salaEspera, int tiempoEntrePacientes) {
		this.salaEspera = salaEspera;
		this.tiempoEntrePacientes = tiempoEntrePacientes;
	}

	@Override
	public void run() {
		int numeroOrden = 1;
		try {
			while (true) {

				Paciente paciente = new Paciente(numeroOrden++, (int) (Math.random() * 101));
				System.out.printf("Admisión va a admitir al paciente nº %d con gravedad %d.\n", paciente.getOrden(),
						paciente.getGravedad());
				salaEspera.admitirPaciente(paciente);
				System.out.printf("Paciente nº %d con gravedad %d admitido.\n", paciente.getOrden(),
						paciente.getGravedad());
				Thread.sleep(tiempoEntrePacientes);
				if (Thread.interrupted()) {
					throw new InterruptedException();
				}
			}
		} catch (InterruptedException e) {
			System.out.println("El hilo de admisión ha sido interrumpido.");
		}
	}

}
