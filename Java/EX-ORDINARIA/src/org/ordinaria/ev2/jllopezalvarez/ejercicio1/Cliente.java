package org.ordinaria.ev2.jllopezalvarez.ejercicio1;

import java.io.DataOutputStream;
import java.io.IOException;
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
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Cliente {

	private static final String PASSWORD_CLAVE = "PWD_CLAVE";  
	
	private static Scanner scanner = new Scanner(System.in);
	

	public static void main(String[] args) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, IOException {

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
					// Encriptamos el mensaje usando la contraseña para generar la clave
					String mensajeCifrado = cifrar(mensaje, PASSWORD_CLAVE);
					
					// Mostramos el mensaje cifrado antes de enviarlo
					System.out.printf("Mensaje a enviar (en Base64): '%s'.\n", mensajeCifrado);

					// Lo envíamos al servidor
					outputStream.writeUTF(mensajeCifrado);
				}

			}
		}
		System.out.println("Fin del programa.");
	}

	private static String cifrar(String texto, String password)
			throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		KeyPair claves = getKeyPair(password);
		Cipher rsa = Cipher.getInstance("RSA");
		rsa.init(Cipher.ENCRYPT_MODE, claves.getPublic());
		byte[] bytesAEncriptar = texto.getBytes(StandardCharsets.UTF_8);
		byte[] bytesEncriptados = rsa.doFinal(bytesAEncriptar);
		return Base64.getEncoder().encodeToString(bytesEncriptados);
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
