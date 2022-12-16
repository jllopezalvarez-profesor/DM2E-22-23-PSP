package es.iesclaradelrey.dm2e2223.ut04comunicaciones.ejercicios.ejercicio05;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import es.iesclaradelrey.dm2e2223.ut04comunicaciones.ejercicios.ejercicio05.Server;

public class Cliente {

	public static void main(String[] args) throws UnknownHostException, IOException {

		try (Socket socket = new Socket("localhost", Server.SERVER_PORT_NUMBER)) {
			try (PrintWriter output = new PrintWriter(socket.getOutputStream())) {
				output.println("H");
				output.println("H");
				output.println("H");
				output.println("H");
				output.println("H");
				output.println("H");
				output.println("H");
			}
		}

	}

}
