package es.iesclaradelrey.dm2e2223.ut06programacionsegura.ejercicios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Ejercicio01 {

	private static final String ALGORITMO = "SHA-256";
	private static final String EXTENSION = ALGORITMO.equals("MD5") ? ".md5" : ".sha1"; 

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Introduce la URL del fichero .jar");
			String strUrlJar = scanner.nextLine();
			String strUrlMd5 = strUrlJar + EXTENSION;

			URL urlJar = new URL(strUrlJar);
			URL urlMd5 = new URL(strUrlMd5);

			try (InputStream jarStream = urlJar.openStream();
					BufferedReader md5Reader = new BufferedReader(new InputStreamReader(urlMd5.openStream()))) {
				String md5 = md5Reader.readLine();

				MessageDigest hash = MessageDigest.getInstance(ALGORITMO);

				byte[] buffer = new byte[1024];

				int bytesLeidos = jarStream.read(buffer, 0, 1024);

				while (bytesLeidos > 0) {
					hash.update(buffer, 0, bytesLeidos);
					bytesLeidos = jarStream.read(buffer, 0, 1024);
				}

				byte[] digest = hash.digest();

				String digestTexto = binToHex(digest);

				System.out.println(md5);
				System.out.println(digestTexto);

			}

		}

	}

	private static String binToHex(byte[] digest) {
		StringBuilder sb = new StringBuilder();
		for (byte b : digest) {
			sb.append(String.format("%02x", b));
		}

		return sb.toString();
	}

}
