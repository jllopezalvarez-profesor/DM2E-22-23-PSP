package org.ex1ev.lopezjoseluis.ejercicio02;

import java.util.LinkedList;

public class CajaGlobos {

	private LinkedList<Globo> globos;
	private int tamanioMaximoCola;

	public CajaGlobos(int tamanioMaximoCola) {
		this.globos = new LinkedList<Globo>();
		this.tamanioMaximoCola = tamanioMaximoCola;
	}

	public synchronized void aniadir(Globo globo) throws InterruptedException {
		// Traza con los globos en la caja
		System.out.printf(
				"Un hinchador está intentando añadir un globo %s a la caja. Antes de añadir, en la caja tenemos: %s.\n",
				globo, globos);

		// Esperamos hasta que haya espacio en la cola
		while ((globos.size() >= tamanioMaximoCola) || (globos.size() > 0 && globos.peekLast() == globo)) {
			wait();
		}

		// Añadimos el globo en la cola
		globos.add(globo);

		// Avisamos a los demás de que se ha añadido
		notifyAll();
	}

	public synchronized Globo coger() throws InterruptedException {
		// Traza con los globos en la caja
		System.out.printf(
				"Un pinchador está intentando tomar un globo de la caja. Antes de cogerlo, en la caja tenemos: %s.\n",
				globos);

		// Esperamos hasta que haya algún globo en la cola
		while (globos.isEmpty()) {
			wait();
		}

		// Sacamos el globo de la cola cola
		Globo globo = globos.remove();

		// Avisamos a los demás de que se ha quitado
		notifyAll();

		// Devolvemos el globo
		return globo;
	}

}
