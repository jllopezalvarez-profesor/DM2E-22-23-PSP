package es.iesclaradelrey.dm2e2223.ut04comunicaciones.ejercicios.ejercicio01;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

	public static final int PUERTO = 1024;

	public static void main(String[] args) {
		System.out.println("Se inicia el proceso servidor");

		// Creamos el ServerSocket
		try (ServerSocket socketServidor = new ServerSocket(PUERTO)) {
			// Escuchamos en el puerto indicado
			try (Socket socketComunicacion = socketServidor.accept()) {
				// Mostramos datos de los sockets
				System.out.println("Datos de la conexión establecida:");
				System.out.printf("Mi dirección: %s\n", socketComunicacion.getLocalAddress().toString());
				System.out.printf("Mi puerto: %s\n", socketComunicacion.getLocalPort());
				System.out.printf("Dirección del cliente: %s\n", socketComunicacion.getInetAddress().toString());
				System.out.printf("Puerto del cliente: %s\n", socketComunicacion.getPort());
			} catch (IOException e) {
				System.err.println("Error al esperar conexión del cliente.");
				e.printStackTrace();
			}
		} catch (IOException e) {
			System.err.println("Error al crear el objeto ServerSocket.");
			e.printStackTrace();
		}
	}
}
