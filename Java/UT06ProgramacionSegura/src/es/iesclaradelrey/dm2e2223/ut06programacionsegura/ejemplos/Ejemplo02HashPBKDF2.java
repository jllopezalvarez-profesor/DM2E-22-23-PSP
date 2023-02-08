package es.iesclaradelrey.dm2e2223.ut06programacionsegura.ejemplos;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Locale;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import net.datafaker.Faker;

public class Ejemplo02HashPBKDF2 {
	// Algoritmo que usaremos para generar el hash.
	private static final String HASH_ALGORITHM_NAME = "PBKDF2WithHmacSHA256";
	// Iteraciones que usará el algoritmo para añadir complejidad.
	private static final int HASH_ITERATIONS = 1_000_000;
	// 16 bytes para el salt. No es texto, es binario, más complicado de adivinar.
	private static final int SALT_SIZE_BYTES = 16;
	// 32 bytes para el hash. Este es el tamaño del hash generado.
	private static final int HASH_SIZE_BYTES = 32;

	public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException {
		// Objeto Faker para generar textos aleatorios.
		Faker faker = new Faker(new Locale("es"));
		// Si lo inicializamos con una semilla concreta, los resultados se repiten
		// Faker faker = new Faker(new Locale("es"), new Random(0));

		String password = faker.harryPotter().spell();
		String passwordSaltAndHash = hash(password);
		boolean verifyOk = verify(password, passwordSaltAndHash);

		System.out.printf("Algoritmo: %s\n", HASH_ALGORITHM_NAME);
		System.out.printf("Password en texto plano: %s\n", password);
		System.out.printf("Salt + hashed pass en Base64: %s\n", passwordSaltAndHash);
		System.out.printf("Resultado de la verificación: %s\n", verifyOk ? "OK" : "ERROR");
	}

	/**
	 * Método para generar un hash (en forma hash+salt) a partir de una contraseña
	 * 
	 * @param password la contraseña que queremos "hashear"
	 * @return una cadena en Base64 con la concatenación de salt + digest
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	private static String hash(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
		// Creamos el array de bytes para el salt
		byte[] salt = new byte[SALT_SIZE_BYTES];
		// Lo llenamos con bytes aleatorios.
		new SecureRandom().nextBytes(salt);
		// Generamos el digest (salt+digest)
		byte[] encoded = encode(password, salt);
		// Devolvemos el conjunto salt+digest en Base64
		return Base64.getEncoder().encodeToString(encoded);
	}

	/**
	 * Método para generar un hash (en forma salt+digest) a partir de una contraseña
	 * y un salt
	 * 
	 * @param password contraseña a hashear
	 * @param salt     salt para mejorar la seguridad del hash
	 * @return un array de bytes con la "concatenación" de salt + digest
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	private static byte[] encode(String password, byte[] salt)
			throws InvalidKeySpecException, NoSuchAlgorithmException {
		// Obtenemos una SecretKeyFactory para el algoritmo que queremos usar.
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(HASH_ALGORITHM_NAME);
		// Definimos las especificaciones (password, salt, iteraciones, tamaño de la
		// clave) de la generación de clave (que usamos para hacer hash)
		KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, HASH_ITERATIONS, HASH_SIZE_BYTES * 8);
		// Crear la clave (hash)
		byte[] generatedPassword = secretKeyFactory.generateSecret(keySpec).getEncoded();
		// Concatenar los dos arrays. Lo hacemos poniendo primero el salt y luego el
		// hash generado
		byte[] concatenated = new byte[SALT_SIZE_BYTES + HASH_SIZE_BYTES];
		System.arraycopy(salt, 0, concatenated, 0, SALT_SIZE_BYTES);
		System.arraycopy(generatedPassword, 0, concatenated, SALT_SIZE_BYTES, HASH_SIZE_BYTES);
		// Devolvemos el resultado
		return concatenated;
	}

	/**
	 * Comprueba si una contraseña correspone al hash/salt generado previamente.
	 * 
	 * @param password       La contraseña a verificar.
	 * @param hashedPassword La combinación de salt+hash en Base64 que se calculó
	 *                       originalmente.
	 * @return True si la contraseña corresponde al hash previamente generado.
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public static Boolean verify(String password, String hashedPassword)
			throws InvalidKeySpecException, NoSuchAlgorithmException {
		// Convertimos de Base64 a array de bytes
		byte[] concatenated = Base64.getDecoder().decode(hashedPassword);
		// Extraemos el salt usado previamente, para volver a usarlo al comprobar la
		// contraseña
		byte[] salt = new byte[SALT_SIZE_BYTES];
		System.arraycopy(concatenated, 0, salt, 0, SALT_SIZE_BYTES);
		// Repetimos el proceso de hash
		byte[] encoded = encode(password, salt);
		// Verificamos que son iguales.
		return Arrays.equals(concatenated, encoded);
	}
}
