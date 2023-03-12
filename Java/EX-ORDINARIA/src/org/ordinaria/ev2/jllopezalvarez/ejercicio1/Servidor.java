package org.ordinaria.ev2.jllopezalvarez.ejercicio1;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Servidor {

	private static final String PASSWORD_CLAVE = "PWD_CLAVE";

	public static final int PORT = 10000;

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeyException,
			InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

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
							String mensajeCifrado = inputStream.readUTF();
							// Mostramos el mensaje recibido antes de descifrarlo
							System.out.printf("Mensaje recibido (cifrado y en Base64): '%s'.\n", mensajeCifrado);
							// Desciframos usando la contraseña
							String mensaje = descifrar(mensajeCifrado, PASSWORD_CLAVE);
							// Mostramos el mensaje descifrado
							System.out.printf("Mensaje recibido (descifrado): '%s'.\n", mensaje);
						}
					} catch (EOFException e) {
						// No hacemos nada porque esto es sólo para que salgamos del bucle.
					}
				}
			}
		}
		System.out.println("Fin del programa.");
	}

	private static String descifrar(String textoCifrado, String password)
			throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		KeyPair claves = getKeyPair(password);
		Cipher rsa = Cipher.getInstance("RSA");
		rsa.init(Cipher.DECRYPT_MODE, claves.getPrivate());
		byte[] bytesEncriptados = Base64.getDecoder().decode(textoCifrado);
		byte[] bytesDescifrados = rsa.doFinal(bytesEncriptados);
		return new String(bytesDescifrados, StandardCharsets.UTF_8);
	}

	private static KeyPair getKeyPair(String password)
			throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		byte[] seed = password.getBytes(StandardCharsets.UTF_8);
		SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
		rnd.setSeed(seed);

		RSAKeyGenParameterSpec spec = new RSAKeyGenParameterSpec(2048, RSAKeyGenParameterSpec.F4);
		KeyPairGenerator pairGenerator = KeyPairGenerator.getInstance("RSA");
		pairGenerator.initialize(spec, rnd);

		return pairGenerator.generateKeyPair();
	}
}
