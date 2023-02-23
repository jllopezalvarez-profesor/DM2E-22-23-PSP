package es.iesclaradelrey.dm2e2223.ut06programacionsegura.ejemplos;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import net.datafaker.Faker;

public class Ejemplo04RsaEncryptDecrypt {

	private static final int TAMANIO_CLAVE = 2048;
	private static final String NOMBRE_ALGORITMO = "RSA";

	private static final String BEGIN_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----";
	private static final String END_PRIVATE_KEY = "-----END PRIVATE KEY-----";
	private static final String BEGIN_RSA_PUBLIC_KEY = "-----BEGIN RSA PUBLIC KEY-----";
	private static final String END_RSA_PUBLIC_KEY = "-----END RSA PUBLIC KEY-----";

	private static Faker faker = new Faker(new Locale("es"));

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException {

		PruebaPublicaYPrivada();
		PruebaPrivadaYPublica();

	}

	private static void PruebaPublicaYPrivada() throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		System.out.println("Pruena de encriptación con pública y desencriptación con privada");
		System.out.println("=".repeat(100));

		// Generamos un par de claves
		KeyPair claveAsimetrica = generateKeyPair(TAMANIO_CLAVE, NOMBRE_ALGORITMO);

		// Opcional, guardarla en disco para usarla luego.
		// Usar el método writeKeyPair, que guarda las dos por separado.

		// Texto "aleatorio· para encriptar
		String textoPlano = faker.theItCrowd().quotes();
		System.out.printf("Original: '%s'.\n", textoPlano);

		// Encriptamos el texto usando la clave pública. El método devuelve Base64.
		String textoEncriptado = encrypt(textoPlano, claveAsimetrica.getPublic(), NOMBRE_ALGORITMO);
		System.out.printf("Encriptado: '%s'.\n", textoEncriptado);

		// Opcional, leer las claves de disco.

		// Desencriptar el texto.
		String textoDesencriptado = decrypt(textoEncriptado, claveAsimetrica.getPrivate(), NOMBRE_ALGORITMO);
		System.out.printf("Desencriptado: '%s'.\n", textoDesencriptado);

		// Si intentamos desencriptar con la clave pública no funciona:
		try {
			textoDesencriptado = decrypt(textoEncriptado, claveAsimetrica.getPublic(), NOMBRE_ALGORITMO);
			System.out.printf("Desencriptado (con clave pública): '%s'.\n", textoDesencriptado);
		} catch (Exception e) {
			System.out.println("Error al intentar desencriptar con clave pública");
			e.printStackTrace();
		}

		System.out.println("\n\n");
	}

	private static void PruebaPrivadaYPublica() throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		System.out.println("Pruena de encriptación con privada y desencriptación con pública");
		System.out.println("=".repeat(100));

		// Generamos un par de claves
		KeyPair claveAsimetrica = generateKeyPair(TAMANIO_CLAVE, NOMBRE_ALGORITMO);

		// Opcional, guardarla en disco para usarla luego.
		// Usar el método writeKeyPair, que guarda las dos por separado.

		// Texto "aleatorio· para encriptar
		String textoPlano = faker.theItCrowd().quotes();
		System.out.printf("Original: '%s'.\n", textoPlano);

		// Encriptamos el texto usando la clave privada. El método devuelve Base64.
		String textoEncriptado = encrypt(textoPlano, claveAsimetrica.getPrivate(), NOMBRE_ALGORITMO);
		System.out.printf("Encriptado: '%s'.\n", textoEncriptado);

		// Opcional, leer las claves de disco.

		// Desencriptar el texto.
		String textoDesencriptado = decrypt(textoEncriptado, claveAsimetrica.getPublic(), NOMBRE_ALGORITMO);
		System.out.printf("Desencriptado: '%s'.\n", textoDesencriptado);

		// Si intentamos desencriptar con la clave privada no funciona:
		try {
			textoDesencriptado = decrypt(textoEncriptado, claveAsimetrica.getPrivate(), NOMBRE_ALGORITMO);
			System.out.printf("Desencriptado (con clave pública): '%s'.\n", textoDesencriptado);
		} catch (Exception e) {
			System.out.println("Error al intentar desencriptar con clave pública");
			e.printStackTrace();
		}

		System.out.println("\n\n");
	}

	public static KeyPair generateKeyPair(int bits, String algorithm) throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
		keyPairGenerator.initialize(bits);
		return keyPairGenerator.generateKeyPair();
	}

	public static KeyPair writeKeyPair(KeyPair keyPair, Path publicKeyPath, Path privateKeyPath, int bits,
			String algorithm) throws NoSuchAlgorithmException, IOException {
		Base64.Encoder pemEncoder = Base64.getMimeEncoder(64, System.lineSeparator().getBytes());
		try (PrintWriter pubKeyWriter = new PrintWriter(new FileOutputStream(publicKeyPath.toFile()))) {
			pubKeyWriter.println(BEGIN_RSA_PUBLIC_KEY);
			pubKeyWriter.println(pemEncoder.encodeToString(keyPair.getPublic().getEncoded()));
			pubKeyWriter.println(END_RSA_PUBLIC_KEY);
		}
		try (PrintWriter privKeyWriter = new PrintWriter(new FileOutputStream(privateKeyPath.toFile()))) {
			privKeyWriter.println(BEGIN_PRIVATE_KEY);
			privKeyWriter.println(pemEncoder.encodeToString(keyPair.getPrivate().getEncoded()));
			privKeyWriter.println(END_PRIVATE_KEY);
		}
		return keyPair;
	}

	public static KeyPair readKeyPair(Path publicKeyPath, Path privateKeyPath, String algorithm)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		PublicKey pub;
		try (FileInputStream pubKeyInputStream = new FileInputStream(publicKeyPath.toFile())) {
			String fileContents = new String(pubKeyInputStream.readAllBytes());
			String publicKeyPEM = fileContents.replace(BEGIN_RSA_PUBLIC_KEY, "").replaceAll(System.lineSeparator(), "")
					.replace(END_RSA_PUBLIC_KEY, "");
			byte[] pubKeyBytes = Base64.getDecoder().decode(publicKeyPEM);
			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(pubKeyBytes, algorithm);
			KeyFactory keyFacPub = KeyFactory.getInstance(algorithm);
			pub = keyFacPub.generatePublic(pubKeySpec);
		}
		PrivateKey priv;
		try (FileInputStream privKeyInputStream = new FileInputStream(privateKeyPath.toFile())) {
			String fileContents = new String(privKeyInputStream.readAllBytes());
			String privateKeyPEM = fileContents.replace(BEGIN_PRIVATE_KEY, "").replaceAll(System.lineSeparator(), "")
					.replace(END_PRIVATE_KEY, "");
			byte[] privKeyBytes = Base64.getDecoder().decode(privateKeyPEM);
			PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(privKeyBytes, algorithm);
			KeyFactory keyFacpriv = KeyFactory.getInstance(algorithm);
			priv = keyFacpriv.generatePrivate(privKeySpec);
		}

		KeyPair keyPair = new KeyPair(pub, priv);
		return keyPair;
	}

	public static String encrypt(String plainText, Key key, String algorithm) throws NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] plainTextBytes = plainText.getBytes(StandardCharsets.UTF_8);
		byte[] cypherBytes = cipher.doFinal(plainTextBytes);
		return Base64.getEncoder().encodeToString(cypherBytes);
	}

	public static String decrypt(String cypherText, Key key, String algorithm) throws NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] cypherTextBytes = Base64.getDecoder().decode(cypherText);
		byte[] plainTextBytes = cipher.doFinal(cypherTextBytes);
		return new String(plainTextBytes);
	}

}
