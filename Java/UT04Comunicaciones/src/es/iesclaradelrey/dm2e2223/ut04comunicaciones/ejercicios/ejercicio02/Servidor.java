package es.iesclaradelrey.dm2e2223.ut04comunicaciones.ejercicios.ejercicio02;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

	public static final int PUERTO = 3000;

	public static void main(String[] args) {
		System.out.println("Se inicia el proceso servidor");

		// Creamos el ServerSocket
		try (ServerSocket socketServidor = new ServerSocket(PUERTO)) {
			// Escuchamos en el puerto indicado
			try (Socket socketComunicacion = socketServidor.accept()) {
				// Mostramos datos de los sockets
				System.out.println("Datos de la conexión establecida:");
				System.out.printf("Mi dirección: %s - Mi puerto: %s.\n",
						socketComunicacion.getLocalAddress().toString(), socketComunicacion.getLocalPort());
				System.out.printf("Dirección del cliente: %s - Puerto del cliente: %s\n",
						socketComunicacion.getInetAddress().toString(), socketComunicacion.getPort());

				// Abrimos los streams de entrada y salida
				try (DataOutputStream streamSalida = new DataOutputStream(socketComunicacion.getOutputStream());
						DataInputStream streamEntrada = new DataInputStream(socketComunicacion.getInputStream())) {

					// Leemos de binario. Controlamos fin con EOFException
					try {
						while (true) {
							// Leemos el número
							int numero = streamEntrada.readInt();
							System.out.printf("Recibido %d, doblamos y enviamos.\n", numero);
							// Doblamos y lo enviamos
							streamSalida.writeInt(numero * 2);
							streamSalida.flush();
						}
					} catch (EOFException eofEx) {
						System.out.println("El cliente ha cerrado la conexión.");
					}
				}
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
