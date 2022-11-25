package org.ex1ev.lopezjoseluis.ejercicio02;

public class Pinchador extends Thread {
	private CajaGlobos globos;
	private int numeroPinchador;

	public Pinchador(CajaGlobos globos, int numeroPinchador) {
		this.globos = globos;
		this.numeroPinchador = numeroPinchador;
	}

	@Override
	public void run() {
		try {
			while (true) {
//				System.out.printf("El pinchador %d intenta coger un globo para pincharlo.\n", numeroPinchador);
				Globo globo = globos.coger();
				System.out.printf("El pinchador %d pincha el globo que ha cogido, de color %s.\n", numeroPinchador, globo);
				
				System.out.printf("El pinchador %d descansa un rato para recuperarse del susto.\n", numeroPinchador);
				recuperarseSusto();
				// Comprobamos si hemos sido interrumpidos
				if (Thread.interrupted()) {
					throw new InterruptedException();
				}
			}
		} catch (InterruptedException e) {
			System.out.printf("El pinchador %d deja de pinchar globos porque le han dicho que pare.\n",
					numeroPinchador);
		}
	}

	private void recuperarseSusto() throws InterruptedException {
		int msDescanso = (int)(Math.random()*1000);
		Thread.sleep(msDescanso);
	}

}
