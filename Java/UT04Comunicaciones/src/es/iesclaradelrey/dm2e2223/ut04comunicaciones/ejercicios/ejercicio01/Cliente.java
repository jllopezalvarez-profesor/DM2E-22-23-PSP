package es.iesclaradelrey.dm2e2223.ut04comunicaciones.ejercicios.ejercicio01;

import java.io.IOException;
import java.net.Socket;

public class Cliente {

	public static void main(String[] args) {
		System.out.println("Se inicia el proceso cliente");

		// Creamos el socket
		try (Socket socketComunicacion = new Socket("localhost", Servidor.PUERTO)) {
			// Mostramos datos de los sockets
			System.out.println("Datos de la conexión establecida:");
			System.out.printf("Mi dirección: %s\n", socketComunicacion.getLocalAddress().toString());
			System.out.printf("Mi puerto: %s\n", socketComunicacion.getLocalPort());
			System.out.printf("Dirección del servidor: %s\n", socketComunicacion.getInetAddress().toString());
			System.out.printf("Puerto del servidro: %s\n", socketComunicacion.getPort());
		} catch (IOException e) {
			System.err.println("Error al iniciar la conexión con el servidor.");
			e.printStackTrace();
		}
	}
}
