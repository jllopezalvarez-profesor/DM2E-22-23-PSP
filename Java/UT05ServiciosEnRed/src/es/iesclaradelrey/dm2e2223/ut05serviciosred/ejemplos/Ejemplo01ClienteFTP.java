package es.iesclaradelrey.dm2e2223.ut05serviciosred.ejemplos;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.nio.file.Paths;

import org.apache.commons.net.ftp.FTPClient;

public class Ejemplo01ClienteFTP {

	private static final String SERVIDOR = "ftp.rediris.es";
	private static final String USUARIO = "anonymous";
	private static final String PASSWORD = "";
	private static final String FICHERO_REMOTO = "welcome.msg";
	private static final String DIRECTORIO_LOCAL = Paths.get(System.getProperty("user.home"), "Downloads").toString();
	private static final String FICHERO_LOCAL = Paths.get(DIRECTORIO_LOCAL, FICHERO_REMOTO).toString();

	public static void main(String[] args) {

		FTPClient clienteFtp = new FTPClient();
		try {
			clienteFtp.connect(InetAddress.getByName(SERVIDOR));
			clienteFtp.login(USUARIO, PASSWORD);
			try (FileOutputStream ficheroLocal = new FileOutputStream(FICHERO_LOCAL)) {
				if (!clienteFtp.retrieveFile(FICHERO_REMOTO, ficheroLocal)) {
					System.out.println("Error al descargar el fichero remoto");
				}
				else
				{
					System.out.println("Fichero descargado");
				}
				System.out.printf("Mensaje de respuesta: %s.\n", clienteFtp.getReplyString());
			}
			clienteFtp.disconnect();
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

}
