package es.iesclaradelrey.dm2e2223.ut04comunicaciones.ejercicios.ejercicio05;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	public static final int SERVER_PORT_NUMBER = 3000;

	public static void main(String[] args) throws IOException {

		ServerSocket ss = new ServerSocket(SERVER_PORT_NUMBER);

		ExecutorService executor = Executors.newCachedThreadPool();

		while (true) {
			Socket s = ss.accept();

			ClientHandler handler = new ClientHandler(s);
			executor.submit(handler);

		}

	}

}
