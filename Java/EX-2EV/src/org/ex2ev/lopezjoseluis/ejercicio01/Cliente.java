package org.ex2ev.lopezjoseluis.ejercicio01;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;

public class Cliente {

	private static Scanner scanner = new Scanner(System.in);
	private static Random random = new Random();

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

		System.out.println("Abriendo socket de cliente");
		try (Socket socket = new Socket("localhost", Servidor.PORT)) {
			// Abrimos Stream de datos
			System.out.println("Abriendo stream de datos");
			try (DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
				// Leemos hasta que el usuario escriba "fin"

				String mensaje;
				System.out.println(
						"Introduce el mensaje que quieres enviar. Pueden ser varias líneas. Cuando introduzcas una línea vacía el programa se detendrá.");

				while (!(mensaje = scanner.nextLine()).isEmpty()) {
					// Calculamos el hash
					String hashBase64 = calculaHash(mensaje);
					System.out.printf("Hash (Base64): '%s'.\n", hashBase64);

					// TODO: modificar aleatoriamente 2 de cada 10 veces.
					if (random.nextInt(0, 10) > 7) {
						mensaje = mensaje.toUpperCase();
					}
					
					// Concatenamos el digest y el mensaje
					String mensajeConHash = hashBase64 + mensaje;
					System.out.printf("Mensaje enviado (con digest incluido): '%s'.\n", mensajeConHash);

					// Lo envíamos al servidor
					outputStream.writeUTF(mensajeConHash);
				}

			}
		}
		System.out.println("Fin del programa.");
	}

	private static String calculaHash(String mensaje) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA512");
		byte[] hash = messageDigest.digest(mensaje.getBytes(StandardCharsets.UTF_16));
		return Base64.getEncoder().encodeToString(hash);
	}

}
