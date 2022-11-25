package org.ex1ev.lopezjoseluis.ejercicio01;

public class Hinchador extends Thread {
	private CajaGlobos globos;
	private int numeroHinchador;

	public Hinchador(CajaGlobos globos, int numeroHinchador) {
		this.globos = globos;
		this.numeroHinchador = numeroHinchador;
	}

	@Override
	public void run() {
		try {
			while (true) {
				Globo nuevoGlobo = generaGloboAleatorio();
//				System.out.printf("El hinchador %d intenta añadir un globo de color %s.\n", numeroHinchador,
//						nuevoGlobo);
				globos.aniadir(nuevoGlobo);
				System.out.printf("El hinchador %d descansa un rato para recuperar el aliento.\n", numeroHinchador);
				recuperarAliento();
				// Comprobamos si hemos sido interrumpidos
				if (Thread.interrupted()) {
					throw new InterruptedException();
				}
			}
		} catch (InterruptedException e) {
			System.out.printf("El hinchador %d deja de hinchar globos porque le han dicho que pare.\n",
					numeroHinchador);
		}
	}

	private Globo generaGloboAleatorio() {
		int valorGlobo = (int)(Math.random() * Globo.values().length);
		return Globo.values()[valorGlobo];
	}

	private void recuperarAliento() throws InterruptedException {
		int msDescanso = (int)(Math.random()*1000);
		Thread.sleep(msDescanso);
	}

}
