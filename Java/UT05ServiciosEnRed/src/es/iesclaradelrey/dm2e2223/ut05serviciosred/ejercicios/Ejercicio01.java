package es.iesclaradelrey.dm2e2223.ut05serviciosred.ejercicios;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class Ejercicio01 {
	private static final String SERVER = "ftp.rediris.es";
	private static final String REMOTE_DIR = "/sites/debian.org/debian";
	private static final String REMOTE_FILE = "README";
	private static final String LOCAL_DIR = "/home/jllopezalvarez/Downloads";
	private static final String LOCAL_FILE = "README";
	private static final String USER_NAME = "anonymous";
	private static final String USER_PASSWORD = "";

	public static void main(String[] args) {
		// Creamos cliente
		FTPClient client = null;

		try {
			client = new FTPClient();

			client.connect(SERVER);

			if (FTPReply.isPositiveCompletion(client.getReplyCode())) {

				if (client.login(USER_NAME, USER_PASSWORD)) {
					mostrarResultadoOperacion("Login OK", client);

					// Modo pasivo para que funcione desde redes NAT
					client.enterLocalPassiveMode();

					if (client.changeWorkingDirectory(REMOTE_DIR)) {
						mostrarResultadoOperacion("Cambio de directorio remoto OK.", client);

						// Abrimos stream para guardar el fichero.
						String localPath = Paths.get(LOCAL_DIR, LOCAL_FILE).toString();
						try (FileOutputStream outputStream = new FileOutputStream(localPath)) {
							// Descargamos fichero
							if (client.retrieveFile(REMOTE_FILE, outputStream)) {
								mostrarResultadoOperacion("Fichero descargado.", client);
							} else {
								mostrarResultadoOperacion("Error al descargar el fichero.", client);
							}
						}
					} else {
						mostrarResultadoOperacion("Error al cambiar directorio remoto.", client);

					}
				} else {
					mostrarResultadoOperacion("Error en login", client);
				}
			}

		} catch (IOException e) {
			System.out.println("Error al descargar el fichero: " + e.getMessage());
		} finally {
			if (client != null && client.isConnected()) {
				try {
					client.logout();
					client.disconnect();
				} catch (IOException e) {
					System.out.println("Error al descargar cerrar la conexión: " + e.getMessage());
				}
			}
		}

	}

	private static void mostrarResultadoOperacion(String mensaje, FTPClient client) {
		System.out.println(mensaje);
		System.out.println("Mensaje de respuesta del servidor:");
		System.out.println(client.getReplyString());
	}
}
