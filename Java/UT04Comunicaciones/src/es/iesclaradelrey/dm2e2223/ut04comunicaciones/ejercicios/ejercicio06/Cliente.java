package es.iesclaradelrey.dm2e2223.ut04comunicaciones.ejercicios.ejercicio06;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Cliente {

	private static final int BUF_SIZE = 1024;
	private static final String SERVER_HOST = "localhost";
	private static final Charset CHARSET = StandardCharsets.UTF_8;

	public static void main(String[] args) {

		// Abrimos un Scanner para pedir datos al usuario.
		// Abrimos el socket UDP. Usamos un puerto aleatorio.
		try (Scanner scanner = new Scanner(System.in); DatagramSocket socket = new DatagramSocket()) {
			String peticion = "";
			do {
				System.out.print("¿Qué petición quieres lanzar al servidor (H/F)? ");
				peticion = scanner.nextLine();
				if (!peticion.equals("FIN")) {
					if (peticion.isBlank()) {
						System.out.println("Tienes que escribir el texto de la petición. No puede estar vacía.");
					} else {
						// Preparamos el datagrama para enviarlo al servidor
						// Buffer creado del tamaño justo para la cadena que vamos a enviar. Usamos
						// UTF-8 para evitar problemas entre distintas máquinas.
						byte[] sendBuffer = peticion.getBytes(CHARSET);

						// Datagrama para enviar los datos
						DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length,
								new InetSocketAddress(SERVER_HOST, Servidor.PUERTO));

						// Enviamos datagrama al servidor. Esta llamada no es bloqueante. El datagrama
						// se envía y nos olvidamos.
						System.out.println("Enviando petición al servidor.");
						try {
							socket.send(sendPacket);
						} catch (IOException e) {
							System.out.printf("Error al enviar paquete de petición al servidor: %s.\n", e.getMessage());
						}

						// Buffer para recibir los datos
						byte[] receiveBuffer = new byte[BUF_SIZE];

						// Datagrama para recibir los datos
						DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, BUF_SIZE);

						// Esperamos datagrama de respuesta del servidor. Esta llamada es bloqueante.
						System.out.println("Esperando respuesta del servidor.");
						try {
							socket.receive(receivePacket);

							// Extraemos contenido de la respuesta y la mostramos. Vamos a usar el
							// constructor de String que recibe un array de bytes y lo convierte a String
							// usando el conjunto de caracteres establecido, en este caso UTF-8. Hay que
							// usar el constructor que recibe qué bytes queremos usar (inicio y cuantos)
							// porque si no, se tomará todo el array del buffer
							String respuesta = new String(receivePacket.getData(), 0, receivePacket.getLength(),
									CHARSET);

							System.out.printf("Respuesta del servidor: %s.\n", respuesta);
						} catch (IOException e) {
							System.out.printf("Error al recibir paquete de respuesta del servidor: %s.\n",
									e.getMessage());
						}
					}
				}
			} while (!peticion.equals("FIN"));

		} catch (SocketException e) {
			System.out.println("No se ha podido abrir el socket UDP");
			e.printStackTrace();
		}
	}

}
