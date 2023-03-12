package org.ordinaria.ev1.jllopezalvarez.ejercicio1;

import java.util.PriorityQueue;

public class SalaEspera {
	private final int tamanioMaximo;
	private final PriorityQueue<Paciente> pacientes;

	public SalaEspera(int tamanioMaximo) {
		this.tamanioMaximo = tamanioMaximo;
		this.pacientes = new PriorityQueue<>();
	}

	public synchronized void admitirPaciente(Paciente paciente) throws InterruptedException {
		// Esperamos hasta que haya espacio en la sala
		while (pacientes.size() >= tamanioMaximo) {
			System.out.printf("La sala de espera esta llena. El paciente nº %d tiene que esperar para entrar.\n",
					paciente.getOrden());
			wait();
		}

		// Añadimos el paciente a la sala
		pacientes.offer(paciente);

		// Avisamos a los demás de que se ha añadido
		notifyAll();
	}

	public synchronized Paciente tratarPaciente() throws InterruptedException {
		// Esperamos hasta que haya algún paciente en la sala
		while (pacientes.isEmpty()) {
			System.out.printf("La sala de espera esta vacía. Un médico tiene que esperar.\n");
			wait();
		}

		// Recuperamos el paciente que está primero en la cola
		Paciente siguientePaciente = pacientes.poll();

		// Avisamos a los demás de que se ha añadido
		notifyAll();

		// Devolvemos al paciente
		return siguientePaciente;
	}

}
