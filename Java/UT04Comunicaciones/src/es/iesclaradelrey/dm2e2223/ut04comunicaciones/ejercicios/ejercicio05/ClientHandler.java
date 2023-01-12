package es.iesclaradelrey.dm2e2223.ut04comunicaciones.ejercicios.ejercicio05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

	Socket socket;

	public ClientHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {

		try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter output = new PrintWriter(socket.getOutputStream()))

		{
			String lineaEntrada;
			while ((lineaEntrada = input.readLine()) != null) {
				System.out.println(lineaEntrada);
				if (Thread.interrupted()) {
					throw new InterruptedException();
				}
			}
		} catch (IOException e) {
			System.out.println("Error de E/S en ClientHandler.");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("ClientHandler interrumpido.");
			e.printStackTrace();
		}

	}
}
