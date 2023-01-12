package es.iesclaradelrey.dm2e2223.ut04comunicaciones.ejercicios.ejercicio05;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	public static final int SERVER_PORT_NUMBER = 3000;
	public static final int LIMITE_CONEXIONES = 10;

	public static void main(String[] args) throws IOException {

		try (ServerSocket ss = new ServerSocket(SERVER_PORT_NUMBER, LIMITE_CONEXIONES)) {

			// Creamos un executor para el control de los hilos.
			ExecutorService executor = Executors.newCachedThreadPool();

			// Variable para controlar cuando debe terminar el servidor.
			// En realidad no lo vamos a usar.
			boolean terminar = false;

			while (!terminar) {
				// Escuchamos en el puerto para aceptar conexiones
				// El servidor se bloquea hasta que se reciba una coxexión.
				Socket s = ss.accept();

				// Creamos un handler para el cliente
				ClientHandler handler = new ClientHandler(s);

				// Lo añadimos al executor. Él se encarga de arrancarlo y de gestionarlo.
				executor.submit(handler);
			}

			// Al terminar tenemos que cerrar el Executor. Si no se hace, se 
			executor.shutdown();
		}

	}

}
