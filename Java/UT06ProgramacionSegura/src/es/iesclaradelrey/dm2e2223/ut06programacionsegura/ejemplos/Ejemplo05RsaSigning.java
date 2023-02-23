package es.iesclaradelrey.dm2e2223.ut06programacionsegura.ejemplos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;

public class Ejemplo05RsaSigning {
	private static final int TAMANIO_CLAVE = 2048;
	private static final String NOMBRE_ALGORITMO = "RSA";
	private static final String NOMBRE_ALGORITMO_FIRMA = "SHA256withRSA";
	private static final String PATH_FICHERO = "datos/StarshipTroopers.txt";

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
		System.out.println("Pruena de firma digital con clave privada y verificación con pública");
		System.out.println("=".repeat(100));

		// Generamos un par de claves
		KeyPair claveAsimetrica = generateKeyPair(TAMANIO_CLAVE, NOMBRE_ALGORITMO);

		// Opcional, guardarla en disco para usarla luego.
		// Ver ejemplo de encriptación RSA
		
		// Calculamos la firma del fichero usando la clave privada:
		String firmaFichero = signFile(Path.of(PATH_FICHERO), claveAsimetrica.getPrivate(), NOMBRE_ALGORITMO_FIRMA);
		System.out.printf("Firma generada: '%s'.\n", firmaFichero);

		// Opcional, leer clave pública de disco
		// Ver ejemplo de encriptación RSA
		
		// Verificar firma con la clave pública
		boolean firmaOk = verifyFileSignature(Path.of(PATH_FICHERO), firmaFichero, claveAsimetrica.getPublic(), NOMBRE_ALGORITMO_FIRMA);
		System.out.printf("¿Verificación OK?: %s\n", firmaOk);

		System.out.println("\n\n");

	}

	public static KeyPair generateKeyPair(int bits, String algorithm) throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
		keyPairGenerator.initialize(bits);
		return keyPairGenerator.generateKeyPair();
	}

	public static String signFile(Path file, PrivateKey privateKey, String signingAlgorithm)
			throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
		Signature signature = Signature.getInstance(signingAlgorithm);
		signature.initSign(privateKey);
		byte[] messageBytes = Files.readAllBytes(file);
		signature.update(messageBytes);
		byte[] digitalSignature = signature.sign();
		return Base64.getEncoder().encodeToString(digitalSignature);
	}

	public static boolean verifyFileSignature(Path file, String digitalSignature, PublicKey publicKey,
			String signingAlgorithm)
			throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
		byte[] receivedSignature = Base64.getDecoder().decode(digitalSignature);
		Signature signature = Signature.getInstance(signingAlgorithm);
		signature.initVerify(publicKey);
		byte[] messageBytes = Files.readAllBytes(file);
		signature.update(messageBytes);
		return signature.verify(receivedSignature);
	}
}
