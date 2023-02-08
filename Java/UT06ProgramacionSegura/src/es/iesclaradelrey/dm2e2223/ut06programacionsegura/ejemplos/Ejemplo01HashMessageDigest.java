package es.iesclaradelrey.dm2e2223.ut06programacionsegura.ejemplos;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Locale;
import java.util.Random;

import net.datafaker.Faker;

public class Ejemplo01HashMessageDigest {
	// Más nombres de algoritmos para MessageDigest en
	// https://docs.oracle.com/en/java/javase/19/docs/specs/security/standard-names.html#messagedigest-algorithms
	//private static final String HASH_ALGORITHM_NAME = "MD5";
	// private static final String HASH_ALGORITHM_NAME = "SHA-1";
	// private static final String HASH_ALGORITHM_NAME = "SHA-256";
	// private static final String HASH_ALGORITHM_NAME = "SHA-512/256";
	 private static final String HASH_ALGORITHM_NAME = "SHA3-512";

	public static void main(String[] args) throws NoSuchAlgorithmException {

		// Objeto Faker para generar textos aleatorios.
		Faker faker = new Faker(new Locale("ru"));
		// Si lo inicializamos con una semilla concreta, los resultados se repiten
		// Faker faker = new Faker(new Locale("es"), new Random(0));

		// Calculamos varios digests
		for (int i = 0; i < 5; i++) {
			// Creamos un texto para calcular su hash
			// String texto = faker.starWars().quotes();
			String texto = faker.name().fullName();

			// Calculamos el digest
			byte[] digest = calculaDigest(texto);

			// Convertimos el digest a base64 para poder mostralo en pantalla
			String digestB64 = Base64.getEncoder().encodeToString(digest);

			// Mostramos los resultados:
			System.out.printf("Algoritmo: %s\n", HASH_ALGORITHM_NAME);
			System.out.printf("Mensaje: %s\n", texto);
			System.out.printf("Longitud del digest binario: %s bytes\n", digest.length);
			System.out.printf("Digest en Base64: %s\n", digestB64);
			System.out.println("-".repeat(100));

		}
	}

	private static byte[] calculaDigest(String mensaje) throws NoSuchAlgorithmException {
		// Obtenemos una instancia de MessageDigest que usa el algoritmo deseado.
		MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM_NAME);

		// Convertimos el mensaje en texto en un array de bytes.
		byte[] bytesMensaje = mensaje.getBytes(StandardCharsets.UTF_8);

		// Calculamos el digest
		byte[] digestResult = digest.digest(bytesMensaje);

		// Devolvemos el resultado
		return digestResult;
	}

}
