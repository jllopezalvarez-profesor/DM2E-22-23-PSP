package es.iesclaradelrey.dm2e2223.ut06programacionsegura.ejercicios.ejercicio02;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
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

public class Servidor {
	public static final int PUERTO = 20_000;

	private static final String ALGORITMO = "AES";
	private static final String ALGORITMO_MODO_PADDING = "AES/CBC/PKCS5Padding";
	private static final int TAMANIO_CLAVE = 256;
	private static final String KEY_PASS = "DM2E";
	private static final String KEY_SALT = "DM2E";
	private static final String HASH_ALGORITHM = "PBKDF2WithHmacSHA256";
	private static final int HASH_ITERATIONS = 1_000_000;

	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
		// Creamos el ServerSocket
		try (ServerSocket socketServidor = new ServerSocket(PUERTO)) {
			// Escuchamos en el puerto indicado
			try (Socket socketComunicacion = socketServidor.accept()) {
				// Leemos todo el contenido del socket hasta que se cierre
				byte[] recibido = null;
				try (DataInputStream inputStream = new DataInputStream(socketComunicacion.getInputStream())) {
					recibido = inputStream.readAllBytes();
				}
				desencriptar(recibido);
			} catch (IOException e) {
				System.err.println("Error al esperar conexión del cliente.");
				e.printStackTrace();
			}
		} catch (IOException e) {
			System.err.println("Error al crear el objeto ServerSocket.");
			e.printStackTrace();
		}
	}

	public static void desencriptar(byte[] mensajeEncriptadoConIV) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException, InvalidKeySpecException {
		// Creamos el algoritmo para descencriptar
		Cipher aesCBCDesencriptar = Cipher.getInstance(ALGORITMO_MODO_PADDING);

		// Ha llegado, hay que "Desmontar" encriptadoConIV
		byte[] datosIvRecibido = new byte[aesCBCDesencriptar.getBlockSize()];
		byte[] datosEncriptadosRecibidos = new byte[mensajeEncriptadoConIV.length - aesCBCDesencriptar.getBlockSize()];
		System.arraycopy(mensajeEncriptadoConIV, 0, datosIvRecibido, 0, datosIvRecibido.length);
		System.arraycopy(mensajeEncriptadoConIV, datosIvRecibido.length, datosEncriptadosRecibidos, 0,
				datosEncriptadosRecibidos.length);

		// Creamos el vector de inicialización a partir de los bytes recibidos
		IvParameterSpec ivDesencriptar = new IvParameterSpec(datosIvRecibido);

		// Configuramos algoritmo de desencriptación
		Key clavePrivada = getKeyFromPassword(TAMANIO_CLAVE, KEY_PASS, KEY_SALT, HASH_ALGORITHM, HASH_ITERATIONS,
				ALGORITMO);

		aesCBCDesencriptar.init(Cipher.DECRYPT_MODE, clavePrivada, ivDesencriptar);
		// Desencriptamos y convertimos a String
		byte[] datosDesencriptadosRecibidos = aesCBCDesencriptar.doFinal(datosEncriptadosRecibidos);
		String mensajeRecibido = new String(datosDesencriptadosRecibidos, StandardCharsets.UTF_8);
		// Tenemos que convertir el array de bytes a texto.
		System.out.printf("Mensaje recibido: '%s'.\n", mensajeRecibido);

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
