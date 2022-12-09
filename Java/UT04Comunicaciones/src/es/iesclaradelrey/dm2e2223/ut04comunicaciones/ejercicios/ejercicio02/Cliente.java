package es.iesclaradelrey.dm2e2223.ut04comunicaciones.ejercicios.ejercicio02;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

	public static void main(String[] args) {
		System.out.println("Se inicia el proceso cliente");

		// Creamos el socket
		try (Socket socketComunicacion = new Socket("localhost", Servidor.PUERTO)) {
			// Mostramos datos de los sockets
			System.out.println("Datos de la conexión establecida:");
			System.out.printf("Mi dirección: %s - Mi puerto: %s\n", socketComunicacion.getLocalAddress().toString(),
					socketComunicacion.getLocalPort());
			System.out.printf("Dirección del servidor: %s - Puerto del servidor: %s\n",
					socketComunicacion.getInetAddress().toString(), socketComunicacion.getPort());

			// Abrimos los streams de entrada y salida
			try (DataOutputStream streamSalida = new DataOutputStream(socketComunicacion.getOutputStream());
					DataInputStream streamEntrada = new DataInputStream(socketComunicacion.getInputStream());
					Scanner scanner = new Scanner(System.in)) {

				System.out.print("Introduce un número (0 para terminar): ");
				int numero = Integer.parseInt(scanner.nextLine());
				// Mientras el usuario no introduzca un cero, seguimos preguntando y enviando.
				while (numero != 0) {
					// Mandamos el número
					streamSalida.writeInt(numero);
					// Recogemos el resultado y lo mostramos
					int doble = streamEntrada.readInt();
					System.out.printf("El doble de %d es %d\n", numero, doble);
					System.out.print("Introduce un número (0 para terminar): ");
					numero = Integer.parseInt(scanner.nextLine());
				}
			}
		} catch (IOException e) {
			System.err.println("Error al iniciar la conexión con el servidor.");
			e.printStackTrace();
		}
	}
}
