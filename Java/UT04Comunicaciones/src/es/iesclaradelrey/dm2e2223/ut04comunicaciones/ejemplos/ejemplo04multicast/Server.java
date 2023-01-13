package es.iesclaradelrey.dm2e2223.ut04comunicaciones.ejemplos.ejemplo04multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class Server {

	public static final String MULTICAST_IP = "225.1.1.1";
	public static final int MULTICAST_PORT = 55964;

	private static final int BUF_SIZE = 1024;
	private static final Charset CHARSET = StandardCharsets.UTF_8;
	private static final long SLEEP_TIME = 2000;

	public static void main(String[] args) {

		try (MulticastSocket socket = new MulticastSocket()) {
			InetAddress grupoMulticast = InetAddress.getByName(MULTICAST_IP);
			while (true) {
				byte[] msg = LocalDateTime.now().toString().getBytes();
				System.out.println("Enviando multicast");
				DatagramPacket packet = new DatagramPacket(msg, msg.length, grupoMulticast, MULTICAST_PORT);
				try {
					socket.send(packet);
				} catch (IOException e) {
					System.out.println("Error al enviar paquete multicast.");
				}
				Thread.sleep(SLEEP_TIME);
			}
		} catch (IOException e) {
			System.out.println("Error al crear socket.");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
