package es.iesclaradelrey.dm2e2223.ut03hilos.ejemplos.ejemplo17semaphore;

import java.util.concurrent.Semaphore;

public class Cliente extends Thread {

	private static final int MAX_ESPERA_MS = 500;

	private int idCliente;
	private Semaphore puertaTienda;

	public Cliente(int idCliente, Semaphore puertaTienda) {
		this.idCliente = idCliente;
		this.puertaTienda = puertaTienda;
	}

	@Override
	public void run() {
		// Cada cliente intenta entrar en la tienda. Si no puede espera hasta que pueda
		// hacerlo, cuando entra, compra y se va

		try {
			// Despistarse un poco
			esperarUnPoco();
			// Entrar en la tienda, si no puede se espera
			System.out.printf("El cliente %d va a intentar entrar.\n", this.idCliente);
			puertaTienda.acquire();
			System.out.printf("El cliente %d ha conseguido entrar.\n", this.idCliente);
			
			// Compra un rato
			comprarUnRato();
			
			// Sale de la tienda
			System.out.printf("El cliente %d sale de la tienda.\n", this.idCliente);
			puertaTienda.release();
		} catch (InterruptedException e) {
			System.out.printf("El cliente %d ha sido interrumpido.\n", this.idCliente);
			e.printStackTrace();
		}

	}

	private void esperarUnPoco() throws InterruptedException {
		int ms = (int) (Math.random() * MAX_ESPERA_MS);
		System.out.printf("El cliente %d se despista durante %d ms.\n", this.idCliente, ms);
		Thread.sleep(ms);
	}

	private void comprarUnRato() throws InterruptedException {
		int ms = (int) (Math.random() * MAX_ESPERA_MS);
		System.out.printf("El cliente %d se pone a comprar un rato, %d ms.\n", this.idCliente, ms);
		Thread.sleep(ms);
	}

}
