package es.iesclaradelrey.dm2e2223.ut06programacionsegura.ejercicios.ejercicio02;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Cliente {
	private static final String ALGORITMO = "AES";
	private static final String ALGORITMO_MODO_PADDING = "AES/CBC/PKCS5Padding";
	private static final int TAMANIO_CLAVE = 256;
	private static final String KEY_PASS = "DM2E";
	private static final String KEY_SALT = "DM2E";
	private static final String HASH_ALGORITHM = "PBKDF2WithHmacSHA256";
	private static final int HASH_ITERATIONS = 1_000_000;

	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args)
			throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		// Creamos el socket
		try (Socket socketComunicacion = new Socket("localhost", Servidor.PUERTO)) {
			try (DataOutputStream outputStream = new DataOutputStream(socketComunicacion.getOutputStream())) {
				System.out.println("¿Qué quieres enviar al servidor? ");
				String texto = scanner.nextLine();
				byte[] textoEncriptadoConIV = encriptar(texto);
				outputStream.write(textoEncriptadoConIV);
			}
		} catch (IOException e) {
			System.err.println("Error al iniciar la conexión con el servidor.");
			e.printStackTrace();
		}
	}

	public static byte[] encriptar(String mensaje)
			throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		Key clavePrivada = getKeyFromPassword(TAMANIO_CLAVE, KEY_PASS, KEY_SALT, HASH_ALGORITHM, HASH_ITERATIONS,
				ALGORITMO);
		Cipher aesCBCEncriptar = Cipher.getInstance(ALGORITMO_MODO_PADDING);
		IvParameterSpec iv = getIVFromRandom(aesCBCEncriptar.getBlockSize() * 8);
		aesCBCEncriptar.init(Cipher.ENCRYPT_MODE, clavePrivada, iv);

		byte[] encriptado = aesCBCEncriptar.doFinal(mensaje.getBytes(StandardCharsets.UTF_8));
		byte[] encriptadoConIV = new byte[iv.getIV().length + encriptado.length];
		System.arraycopy(iv.getIV(), 0, encriptadoConIV, 0, iv.getIV().length);
		System.arraycopy(encriptado, 0, encriptadoConIV, iv.getIV().length, encriptado.length);

		return encriptadoConIV;
	}

	public static IvParameterSpec getIVFromRandom(int blockSizeBits) {
		// Creamos el array de bytes para el IV
		byte[] iv = new byte[blockSizeBits / 8];
		// Generamos los bytres aleatorios.
		new SecureRandom().nextBytes(iv);
		// Devolvemos el nuevo IV
		return new IvParameterSpec(iv);
	}

	private static Key getKeyFromPassword(int keySizeBits, String password, String passwordSalt,
			String keyHashAlgorithmName, int keyHashIterations, String cipherAlgoritmName)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		// Obtenemos los caracteres del password.
		char[] passwordChars = password.toCharArray();
		// Obtenemos del mismo modo los bytes del salt. Si lo vamos a hacer en distintos
		// sistemas es buena idea interpretarlos con la misma codificación.
		byte[] saltBytes = passwordSalt.getBytes(StandardCharsets.UTF_8);
		// Creamos el objeto SecretKeyFactory, indicando el algoritmo de HASH que
		// usaremos para generar la clave.
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(keyHashAlgorithmName);
		// Creamos las especificaciones para el HASH: password, salt, iteraciones y
		// tamaño en bits
		KeySpec keySpec = new PBEKeySpec(passwordChars, saltBytes, keyHashIterations, keySizeBits);
		// Generamos la clave
		SecretKey key = keyFactory.generateSecret(keySpec);
		// Añadimos el algoritmo para el que la usaremos.
		return new SecretKeySpec(key.getEncoded(), cipherAlgoritmName);
	}

}
