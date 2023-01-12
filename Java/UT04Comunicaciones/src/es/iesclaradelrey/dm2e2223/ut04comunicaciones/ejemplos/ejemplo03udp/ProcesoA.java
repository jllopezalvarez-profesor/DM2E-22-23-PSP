package es.iesclaradelrey.dm2e2223.ut04comunicaciones.ejemplos.ejemplo03udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;

public class ProcesoA {

	private static final int TIMEOUT_RECEPCION = 2000;
	public static final int PUERTO = 33580;
	private static final int BUF_SIZE = 1024;
	private static final String REMOTE_HOST = "localhost";
	private static final String CHARSET_NAME = "UTF-8";
	private static final long SLEEP_TIME = 1000;

	public static void main(String[] args) {

		// Abrimos el socket UDP, que escucha en el puerto local establecido por la
		// constante PUERTO
		try (DatagramSocket socket = new DatagramSocket(PUERTO)) {

			// Fijamos un timeout para la recepción de datos, para que no esté eternamente esperando.
			socket.setSoTimeout(TIMEOUT_RECEPCION);
			
			// Este proceso se repite indefinidamente.
			while (true) {

				// Buffer para recibir los datos
				byte[] receiveBuffer = new byte[BUF_SIZE];

				// Datagrama para recibir los datos
				DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, BUF_SIZE);

				// Esperamos datagrama del proceso B. Este proceso es bloqueante.
				System.out.println("Esperando paquete del proceso B");
				// Convertimos a número. Controlamos el posible error de conversión
				int numero = 0;
				try {
					socket.receive(receivePacket);

					// Extraemos su contenido. Sabemos que estamos enviando números como si fueran
					// cadenas de caracteres, así que vamos a usar el constructor de String que
					// recibe un array de bytes y lo convierte a String usando el conjunto de
					// caracteres establecido, en este caso UTF-8. Hay que usar el constructor que
					// recibe qué bytes queremos usar (inicio y cuantos) porque si no, se tomará
					// todo el array del buffer
					String receivedMsg = new String(receivePacket.getData(), 0, receivePacket.getLength(),
							Charset.forName(CHARSET_NAME));

					try {
						numero = Integer.parseInt(receivedMsg);
						System.out.printf("Número recibido: %d.\n", numero);
					} catch (NumberFormatException e) {
						System.out.printf("Error en mensaje recibido. '%s' no se puede convertir a número.\n", receivedMsg);
					}
				} catch (SocketTimeoutException e) {
					System.out.println("Se ha alcanzado el timeout");
				}

				// Incrementamos el valor del número
				System.out.println("Incrementando el valor.");
				numero++;

				// Enviamos el valor incrementado al proceso B
				// Buffer creado del tamaño justo para la cadena que vamos a enviar. Usamos
				// UTF-8 para evitar problemas entre distintas máquinas.
				byte[] sendBuffer = String.valueOf(numero).getBytes(Charset.forName(CHARSET_NAME));

				// Datagrama para enviar los datos
				DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length,
						new InetSocketAddress(REMOTE_HOST, ProcesoB.PUERTO));

				// Enviamos datagrama al proceso B
				socket.send(sendPacket);

				// Esperamos un poco porque si nó no podemos ver la ejecución.
				Thread.sleep(SLEEP_TIME);
			}

		} catch (SocketException e) {
			System.out.println("No se ha podido abrir el socket UDP");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error al recibir datagrama UDP");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("El hilo principal ha sido interrumpido");
			e.printStackTrace();
		}

	}

}
