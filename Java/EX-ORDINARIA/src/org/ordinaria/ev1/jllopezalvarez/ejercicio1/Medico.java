package org.ordinaria.ev1.jllopezalvarez.ejercicio1;

import java.util.Random;

public class Medico extends Thread {
	private int numColegiado;
	private final int tiempoAtendiendoPaciente;
	private SalaEspera salaEspera;
	private Random rnd = new Random();

	public Medico(int numColegiado, SalaEspera salaEspera, int tiempoAtendiendoPaciente) {
		this.numColegiado = numColegiado;
		this.salaEspera = salaEspera;
		this.tiempoAtendiendoPaciente = tiempoAtendiendoPaciente;
	}

	@Override
	public void run() {
		try {
			while (true) {
				System.out.printf("El médico %d está libre para tratar a un paciente.\n", this.numColegiado);
				Paciente paciente = salaEspera.tratarPaciente();
				System.out.printf("El médico %d trata al paciente nº %d con gravedad %d.\n", this.numColegiado,
						paciente.getOrden(), paciente.getGravedad());
				Thread.sleep(this.tiempoAtendiendoPaciente);
				Thread.sleep(rnd.nextInt(this.tiempoAtendiendoPaciente));
				if (Thread.interrupted()) {
					throw new InterruptedException();
				}
			}
		} catch (InterruptedException e) {
			System.out.printf("El hilo del médico %d ha sido interrumpido.\n", this.numColegiado);
		}
	}

}
