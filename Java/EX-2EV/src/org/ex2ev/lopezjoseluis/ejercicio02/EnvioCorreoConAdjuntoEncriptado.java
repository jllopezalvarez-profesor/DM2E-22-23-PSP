package org.ex2ev.lopezjoseluis.ejercicio02;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

public class EnvioCorreoConAdjuntoEncriptado {

	private static final String SERVER_NAME = "smtp01.educa.madrid.org";
	private static final int SERVER_PORT = 465;
	private static final String USER_NAME = "jllopezalvarez";
	private static final String EMAIL_FROM = "jllopezalvarez@educa.madrid.org";
	private static final String EMAIL_TO = "jllopezalvarez@educa.madrid.org";

	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws EmailException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, FileNotFoundException, IllegalBlockSizeException,
			BadPaddingException, IOException {

		// Preguntamos la ubicación del fichero al usuario, y creamos un objeto file.
		System.out.println("Introduce la ubicación en disco del fichero que quieres enviar: ");
		String pathFichero = scanner.nextLine();
		File fichero = new File(pathFichero);

		// "/home/jllopezalvarez/resultados2.txt"
		if (!fichero.exists()) {
			System.out.println("El fichero no existe.");
			return;
		}
		
		System.out.printf("Introduce la contraseña para la cuenta de correo %s: ", EMAIL_FROM);
		
		String userPassword = scanner.nextLine();

		File ficheroEncriptado = new File(pathFichero + ".encriptado");

		encriptarFichero(fichero, ficheroEncriptado);

		// Creamos un adjunto para el fichero encriptado
		EmailAttachment adjunto = new EmailAttachment();
		adjunto.setPath(ficheroEncriptado.getAbsolutePath());
		adjunto.setDisposition(EmailAttachment.ATTACHMENT);
		adjunto.setDescription("Fichero encriptado. Necesitará desencriptarlo para acceder a su contenido.");
		adjunto.setName("encriptado.dat");

		MultiPartEmail email = new MultiPartEmail();

		// Hacemos que muestre en System.out los mensajes de depuración.
		email.setDebug(true);
		email.setHostName(SERVER_NAME);
		email.setSmtpPort(SERVER_PORT);
		email.setStartTLSEnabled(true);
		email.setAuthentication(USER_NAME, userPassword);
		email.setFrom(EMAIL_FROM);
		email.addTo(EMAIL_TO);
		email.setSubject("Envío de fichero encriptado.");
		email.setMsg("Enviando fichero encriptado " + fichero.getName());

		email.attach(adjunto);

		email.send();

		System.out.println("Correo enviado");
	}

	private static void encriptarFichero(File fichero, File ficheroEncriptado) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, FileNotFoundException,
			IOException, IllegalBlockSizeException, BadPaddingException {
		KeyGenerator generadorClave = KeyGenerator.getInstance("AES");
		Key clave = generadorClave.generateKey();

		Cipher cifradoAES = Cipher.getInstance("AES/CBC/PKCS5Padding");

		byte[] bytesIv = new byte[cifradoAES.getBlockSize()];
		new SecureRandom().nextBytes(bytesIv);
		IvParameterSpec iv = new IvParameterSpec(bytesIv);

		cifradoAES.init(Cipher.ENCRYPT_MODE, clave, iv);

		byte[] encriptado = cifradoAES.doFinal(Files.readAllBytes(fichero.toPath()));
		Files.write(ficheroEncriptado.toPath(), encriptado);
//			
//			
//			byte[] bufferLectura = new byte[1024];
//			byte[] bufferEncriptacion = new byte[1024];
//			int bytesLeidos;
//			while ((bytesLeidos = inputStream.read(bufferLectura)) != 0) {
//				bufferEncriptacion = cifradoAES.update(bufferLectura, 0, bytesLeidos);
//				outputStream.write(bufferEncriptacion);
//			}
//			byte[] datosFinales = cifradoAES.doFinal();
//			outputStream.write(datosFinales);
	}
}
