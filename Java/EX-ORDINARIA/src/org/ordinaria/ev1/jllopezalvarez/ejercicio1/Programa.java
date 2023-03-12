package org.ordinaria.ev1.jllopezalvarez.ejercicio1;

public class Programa {

	private static final int TAM_SALA_ESPERA = 5;
	private static final int TIEMPO_ENTRE_PACIENTES_SALA = 250;
	private static final int NUM_MEDICOS = 3;
	private static final int TIEMPO_ATENCION_PACIENTE = 2000;
	
	
	public static void main(String[] args) {
		SalaEspera salaEspera = new SalaEspera(TAM_SALA_ESPERA);
		Admision admision = new Admision(salaEspera, TIEMPO_ENTRE_PACIENTES_SALA);
		admision.start();
		for(int idMedico = 1; idMedico <= NUM_MEDICOS; idMedico++) {
			Medico medico = new Medico(idMedico, salaEspera, TIEMPO_ATENCION_PACIENTE);
			medico.start();
		}
		
				

	}

}
