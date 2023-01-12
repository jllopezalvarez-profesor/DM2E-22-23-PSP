package es.iesclaradelrey.dm2e2223.ut04comunicaciones.ejemplos.ejemplo03udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ProcesoB {

	public static final int PUERTO = 33581;
	private static final int BUF_SIZE = 1024;
	private static final String REMOTE_HOST = "localhost";
	private static final Charset CHARSET = StandardCharsets.UTF_8;
	private static final long SLEEP_TIME = 1000;

	public static void main(String[] args) {

		// Variable entera para ir incrementando y enviando
		int numero = 0;

		// Abrimos el socket UDP
		try (DatagramSocket socket = new DatagramSocket(PUERTO)) {

			while (true) {
				// Preparamos el datagrama para enviarlo al proceso A
				// Buffer creado del tamaño justo para la cadena que vamos a enviar. Usamos
				// UTF-8 para evitar problemas entre distintas máquinas.
				byte[] sendBuffer = String.valueOf(numero).getBytes(CHARSET);

				// Datagrama para enviar los datos
				DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length,
						new InetSocketAddress(REMOTE_HOST, ProcesoA.PUERTO));

				// Enviamos datagrama al proceso A. Esta llamada no es bloqueante. El datagrama
				// se envía y nos olvidamos.
				System.out.println("Enviando paquete a proceso A.");
				socket.send(sendPacket);

				// Buffer para recibir los datos
				byte[] receiveBuffer = new byte[BUF_SIZE];

				// Datagrama para recibir los datos
				DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, BUF_SIZE);

				// Esperamos datagrama del proceso B. Esta llamada es bloqueante.
				System.out.println("Esperando paquete del proceso A.");
				socket.receive(receivePacket);

				// Extraemos su contenido. Sabemos que estamos enviando números como si fueran
				// cadenas de caracteres, así que vamos a usar el constructor de String que
				// recibe un array de bytes y lo convierte a String usando el conjunto de
				// caracteres establecido, en este caso UTF-8. Hay que usar el constructor que
				// recibe qué bytes queremos usar (inicio y cuantos) porque si no, se tomará
				// todo el array del buffer
				String receivedMsg = new String(receivePacket.getData(), 0, receivePacket.getLength(), CHARSET);

				// Convertimos a número. Controlamos el posible error de conversión
				try {
					numero = Integer.parseInt(receivedMsg);
					System.out.printf("Número recibido: %d.\n", numero);
				} catch (NumberFormatException e) {
					System.out.printf("Error en mensaje recibido. '%s' no se puede convertir a número.\n", receivedMsg);
					numero = 0;
				}

				// Incrementamos el valor del número
				System.out.println("Incrementando el valor.");
				numero++;

				// Esperamos un poco porque si nó no podemos ver la ejecución.
				Thread.sleep(SLEEP_TIME);

				// Volvemos al principio del bucle, para que se envíe de nuevo el valor
				// incrementado.
			}

		} catch (SocketException e) {
			System.out.println("No se ha podido abrir el socket UDP");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error al enviar o recibir datagrama UDP");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("El hilo principal ha sido interrumpido");
			e.printStackTrace();
		}
	}

}
