package org.ex2ev.lopezjoseluis.ejercicio01;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Servidor {

	private static final int HASH_LENGTH_BASE64 = 88;
	public static final int PORT = 10000;

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

		System.out.println("Abriendo socket de servidor");
		try (ServerSocket severSocket = new ServerSocket(PORT)) {
			System.out.println("Aceptando conexión con el cliente");
			try (Socket socket = severSocket.accept()) {
				// Abrimos Stream de datos
				System.out.println("Abriendo stream de datos");
				try (DataInputStream inputStream = new DataInputStream(socket.getInputStream())) {
					// Leemos hasta que se produzca la excepción EOFException
					try {
						while (true) {
							String mensajeConHash = inputStream.readUTF();
							// Dividimos el hash y el mensaje. En Base64 son 88 caracteres. El resto será el
							// mensaje.
							String hashBase64 = mensajeConHash.substring(0, HASH_LENGTH_BASE64);
							String mensaje = mensajeConHash.substring(HASH_LENGTH_BASE64);
							// Volvemos a calcular el hash
							String hashRecalculado = calculaHash(mensaje);
							// Mostramos las partes:
							System.out.printf("Mensaje recibido (con digest incluido): '%s'.\n", mensajeConHash);
							System.out.printf("Hash (Base64): '%s'.\n", hashRecalculado);
							System.out.printf("Mensaje: '%s'.\n", mensaje);
							// Indicamos si ha habido error
							if (hashRecalculado.equals(hashBase64)) {
								System.out.println("El mensaje no se ha alterado.");
							} else {
								System.out.println("Atención: El mensaje se ha alterado.");
							}
						}
					} catch (EOFException e) {
						// No hacemos nada porque esto es sólo para que salgamos del bucle.
					}
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
