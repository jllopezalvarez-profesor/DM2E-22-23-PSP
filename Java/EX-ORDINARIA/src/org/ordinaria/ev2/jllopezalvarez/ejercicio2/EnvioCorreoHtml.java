package org.ordinaria.ev2.jllopezalvarez.ejercicio2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceFileResolver;

public class EnvioCorreoHtml {

	private static final String SERVER_NAME = "smtp01.educa.madrid.org";
	private static final int SERVER_PORT = 465;
	private static final String USER_NAME = "jllopezalvarez";
	private static final String EMAIL_FROM = "jllopezalvarez@educa.madrid.org";
	private static final String EMAIL_TO = "jllopezalvarez@educa.madrid.org";

	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws EmailException, IOException {

		// Preguntamos la ubicación del fichero al usuario, y creamos un objeto file.
		System.out.println("Introduce la ubicación en disco del fichero HTML que quieres enviar: ");
		String pathFichero = scanner.nextLine();
		File fichero = new File(pathFichero);

		// /home/jllopezalvarez/repos/22-23/DM2E-22-23-PSP/Java/EX-ORDINARIA-1EV/data/index.html
		if (!fichero.exists() || !fichero.isFile()) {
			System.out.println("El fichero no existe o no es un fichero.");
			return;
		}

		System.out.println("Introduce el texto alternativo para el correo HTML:");
		String textoAlternativo = scanner.nextLine();

		System.out.printf("Introduce la contraseña para la cuenta de correo %s: ", EMAIL_FROM);

		String userPassword = scanner.nextLine();

		ImageHtmlEmail email = new ImageHtmlEmail();

		// Hacemos que muestre en System.out los mensajes de depuración.
		email.setDebug(true);

		email.setHostName(SERVER_NAME);
		email.setSmtpPort(SERVER_PORT);
		email.setStartTLSEnabled(true);
		email.setAuthentication(USER_NAME, userPassword);
		email.setFrom(EMAIL_FROM);
		email.addTo(EMAIL_TO);
		email.setSubject("Envío de correo HTML");
		email.setHtmlMsg(Files.readString(fichero.toPath()));
		email.setTextMsg(textoAlternativo);
		email.setDataSourceResolver(new DataSourceFileResolver(fichero.getParentFile()));

		email.send();

		System.out.println("Correo enviado");
	}

}
