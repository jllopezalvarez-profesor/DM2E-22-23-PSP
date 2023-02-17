package es.iesclaradelrey.dm2e2223.ut06programacionsegura.ejemplos;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import net.datafaker.Faker;

public class Ejemplo03AEsEncryptDecrypt {

	private static final String KEY_HASH_ALGORITHM = "PBKDF2WithHmacSHA256";
	private static final int KEY_HASH_ITERATIONS = 65536;
	private static final int KEY_SIZE_BITS = 256;
	private static final String KEY_SEED_PASSWORD = "Estos no son los androides que buscáis";
	private static final String KEY_SEED_SALT = "¡Corred, insensatos!";
	private static final int IV_SIZE_BITS = 128; // Para AES CBC - 16 bytes por bloque
	private static final String CIPHER_ALGORITHM_AES = "AES";
	private static final String CIPHER_ALGORITHM_AES_ECB_PKCS5PADDING = "AES/ECB/PKCS5Padding";
	private static final String CIPHER_ALGORITHM_AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";

	private static Faker faker = new Faker(new Locale("es"));

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException,
			InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

		pruebaAesEcb();
		pruebaAesCbc();

	}

	private static void pruebaAesEcb() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		System.out.println("=".repeat(100));
		System.out.println("Prueba de encriptación AES ECB");
		System.out.println("=".repeat(100));

		// Generamos un texto a encriptar:
		String textoOriginal = faker.backToTheFuture().quote();
		System.out.printf("Texto original: '%s'.\n", textoOriginal);

		// Creamos una clave privada
		Key clavePrivada = getKeyFromPassword(KEY_SIZE_BITS, KEY_SEED_PASSWORD, KEY_SEED_SALT, KEY_HASH_ALGORITHM,
				KEY_HASH_ITERATIONS, CIPHER_ALGORITHM_AES);

		// Encriptamos. No necesitamos IV en este modo.
		byte[] datosEncriptados = encryptTextECB(clavePrivada, textoOriginal);
		System.out.printf("Encriptado (en hexadecimal): '%s'.\n", toHex(datosEncriptados));
		System.out.printf("Tamaño de los datos encriptados: %d (%d bytes).\n", datosEncriptados.length,
				datosEncriptados.length / 8);

		// Desencriptamos
		String textoDesencriptado = decryptTextECB(clavePrivada, datosEncriptados);
		System.out.printf("Texto desencriptado: '%s'.\n\n", textoDesencriptado);

		
	}

	private static void pruebaAesCbc() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		System.out.println("=".repeat(100));
		System.out.println("Prueba de encriptación AES CBC");
		System.out.println("=".repeat(100));

		// Generamos un texto a encriptar:
		String textoOriginal = faker.backToTheFuture().quote();
		System.out.printf("Texto original: '%s'.\n", textoOriginal);

		// Creamos una clave privada
		Key clavePrivada = getKeyFromPassword(KEY_SIZE_BITS, KEY_SEED_PASSWORD, KEY_SEED_SALT, KEY_HASH_ALGORITHM,
				KEY_HASH_ITERATIONS, CIPHER_ALGORITHM_AES);

		// Creamos un VI del mismo tamaño que el bloque de datos del algoritmo
		IvParameterSpec iv = getIVFromRandom(IV_SIZE_BITS);

		// Encriptamos
		byte[] datosEncriptados = encryptTextCBC(clavePrivada, iv, textoOriginal);
		System.out.printf("Encriptado (en hexadecimal): '%s'.\n", toHex(datosEncriptados));
		System.out.printf("Tamaño de los datos encriptados: %d (%d bytes).\n", datosEncriptados.length,
				datosEncriptados.length / 8);

		// Desencriptamos
		String textoDesencriptado = decryptTextCBC(clavePrivada, iv, datosEncriptados);
		System.out.printf("Texto desencriptado: '%s'.\n", textoDesencriptado);
	}

	/**
	 * Genera una clave privada aleatoria para cierto algoritmo. Los nombres de
	 * algoritmos están en
	 * https://docs.oracle.com/en/java/javase/17/docs/specs/security/standard-names.html
	 * 
	 * @param keySizeBits        tamaño de la clave en bits
	 * @param cipherAlgoritmName nombre del algoritmo para el que se usará
	 * @return la clave aleatoria
	 */
	private static Key getRandomKeyFromRandom(int keySizeBits, String cipherAlgoritmName) {
		// Creamos un array de bytes para la clave. 8 bits por byte.
		byte[] keyBytes = new byte[keySizeBits / 8];
		// Usamos SecureRandom. Nunca Random, que no es realmente aleatorio.
		SecureRandom secureRandom = new SecureRandom();
		// Generamos los bytes aleatorios
		secureRandom.nextBytes(keyBytes);
		// Devolvemos la clave. La clave incluye el nombre del algoritmo para el que se
		// usará.
		return new SecretKeySpec(keyBytes, cipherAlgoritmName);
	}

	/**
	 * Genera una clave privada aleatoria para cierto algoritmo. Los nombres de
	 * algoritmos están en
	 * https://docs.oracle.com/en/java/javase/17/docs/specs/security/standard-names.html
	 * La diferencia fundamental con el método que usa SecureRandom es que este
	 * lanza excepción si usamos un tamaño de clave no válido para el algoritmo
	 * especificado. Además KeyGenerator ya usa SecureRandom por defecto.
	 * 
	 * @param keySizeBits        tamaño de la clave en bits
	 * @param cipherAlgoritmName nombre del algoritmo para el que se usará
	 * @return la clave aleatoria
	 * @throws NoSuchAlgorithmException
	 */
	private static Key getRandomKeyFromKeyGenerator(int keySizeBits, String cipherAlgoritmName)
			throws NoSuchAlgorithmException {
		// Creamos un generador de claves para el algoritmo
		KeyGenerator keyGenerator = KeyGenerator.getInstance(cipherAlgoritmName);
		// Especificamos el tamaño de la clave en bits.
		keyGenerator.init(keySizeBits);
		// Generamos la clave
		return keyGenerator.generateKey();
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

	/**
	 * Crea un vector de inicialización aleatorio del tamaño en bits especificado
	 * 
	 * @param keySizeBits tamaño del vector en bits
	 * @return el vector de inicialización.
	 */
	public static IvParameterSpec getIVFromRandom(int keySizeBits) {
		// Creamos el array de bytes para el IV
		byte[] iv = new byte[keySizeBits / 8];
		// Generamos los bytres aleatorios.
		new SecureRandom().nextBytes(iv);
		// Devolvemos el nuevo IV
		return new IvParameterSpec(iv);
	}

	/**
	 * Encripta una cadena de texto usando AES en modo ECB (Electronic CodeBook)
	 * Este modo no necesita vector de inicialización
	 * 
	 * @param privateKey clave privada para encriptar
	 * @param plainText  texto a encriptar
	 * @return el texto encriptado
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */

	private static byte[] encryptTextECB(Key privateKey, String plainText) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		// Creamos el algoritmo. Incluye el algoritmo, el modo y el padding.
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_AES_ECB_PKCS5PADDING);
		// Parametrizamos, indicando si vamos a cifrar o descifrar y la clave
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		// Encriptamos "de golpe". Se puede hacer añadiendo poco a poco.
		// Tenemos que convertir el texto a array de bytes.
		// Se encripta siempre en binario.
		return cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
	}

	private static String decryptTextECB(Key privateKey, byte[] encryptedText) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		// Creamos el algoritmo. Incluye el algoritmo, el modo y el padding.
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_AES_ECB_PKCS5PADDING);
		// Parametrizamos, indicando si vamos a cifrar o descifrar y la clave
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		// Desencriptamos "de golpe". Se puede hacer añadiendo poco a poco.
		byte[] decryptedTextBytes = cipher.doFinal(encryptedText);
		// Tenemos que convertir el array de bytes a texto.
		return new String(decryptedTextBytes, StandardCharsets.UTF_8);
	}

	private static byte[] encryptTextCBC(Key privateKey, IvParameterSpec iv, String plainText)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, InvalidAlgorithmParameterException {
		// Creamos el algoritmo. Incluye el algoritmo, el modo y el padding.
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_AES_CBC_PKCS5PADDING);
		// Parametrizamos, indicando si vamos a cifrar o descifrar, la clave y el vector
		// de inicialización
		cipher.init(Cipher.ENCRYPT_MODE, privateKey, iv);
		// Encriptamos "de golpe". Se puede hacer añadiendo poco a poco.
		// Tenemos que convertir el texto a array de bytes.
		// Se encripta siempre en binario.
		return cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
	}

	private static String decryptTextCBC(Key privateKey, IvParameterSpec iv, byte[] encryptedText)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, InvalidAlgorithmParameterException {
		// Creamos el algoritmo. Incluye el algoritmo, el modo y el padding.
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_AES_CBC_PKCS5PADDING);
		// Parametrizamos, indicando si vamos a cifrar o descifrar, la clave y el vector
		// de inicialización
		cipher.init(Cipher.DECRYPT_MODE, privateKey, iv);
		// Desencriptamos "de golpe". Se puede hacer añadiendo poco a poco.
		byte[] decryptedTextBytes = cipher.doFinal(encryptedText);
		// Tenemos que convertir el array de bytes a texto.
		return new String(decryptedTextBytes, StandardCharsets.UTF_8);
	}

	private static String toHex(byte[] data) {
		StringBuilder sb = new StringBuilder();
		for (byte b : data) {
			sb.append(String.format("%02X", b));
		}
		return sb.toString();
	}
}
