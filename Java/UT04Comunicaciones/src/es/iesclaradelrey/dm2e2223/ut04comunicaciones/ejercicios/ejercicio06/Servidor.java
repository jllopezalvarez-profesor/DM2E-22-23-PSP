package es.iesclaradelrey.dm2e2223.ut04comunicaciones.ejercicios.ejercicio06;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Servidor {

	public static final int PUERTO = 45287;
	private static final int BUF_SIZE = 1024;
	private static final Charset CHARSET = StandardCharsets.UTF_8;

	public static void main(String[] args) {

		// Abrimos el socket UDP, que escucha en el puerto local establecido por la
		// constante PUERTO
		try (DatagramSocket socket = new DatagramSocket(PUERTO)) {

			// Este proceso se repite indefinidamente.
			while (true) {

				// Buffer para recibir los datos
				byte[] receiveBuffer = new byte[BUF_SIZE];

				// Datagrama para recibir los datos
				DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, BUF_SIZE);

				String peticion = "";

				// Esperamos petición de un cliente
				System.out.println("Esperando paquete de algún cliente...");
				try {
					socket.receive(receivePacket);

					// Extraemos el texto de la petición del cliente
					peticion = new String(receivePacket.getData(), 0, receivePacket.getLength(), CHARSET);
				} catch (IOException e) {
					System.out.printf("Error al recibir paquete de cliente: %s.\n", e.getMessage());
				}

				if (!peticion.isBlank()) {
					String respuesta = switch (peticion) {
					case "F": {
						LocalDate hoy = LocalDate.now();
						DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						yield hoy.format(dateTimeFormatter);
					}
					case "H": {
						LocalTime ahora = LocalTime.now();
						DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss");
						yield ahora.format(dateTimeFormatter);
					}
					default:
						yield "ERROR";
					};

					// Buffer creado del tamaño justo para la cadena que vamos a enviar. Usamos
					// UTF-8 para evitar problemas entre distintas máquinas.
					byte[] sendBuffer = respuesta.getBytes(CHARSET);

					// Datagrama para enviar los datos. Lo enviamos a la dirección de origen de la
					// petición.
					DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length,
							receivePacket.getSocketAddress());

					// Enviamos datagrama de respuesta al cliente
					try {
						socket.send(sendPacket);
					} catch (IOException e) {
						System.out.printf("Error al enviar paquete de respuesta al cliente: %s.\n", e.getMessage());
					}
				}
			}
		} catch (SocketException e) {
			System.out.println("No se ha podido abrir el socket UDP");
			e.printStackTrace();
		}
	}
}
