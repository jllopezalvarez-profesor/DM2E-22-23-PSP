package es.iesclaradelrey.dm2e2223.ut05serviciosred.ejemplos.ejemplos01ftp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;

public class Ejemplo02ClienteFTPSExplicito {
	private static final String SERVER = "dm2e-ftp-inseguro.westeurope.cloudapp.azure.com";
	private static final String REMOTE_DIR = "/directorio-a";
	private static final String REMOTE_FILE = "readme.txt";
	private static final String LOCAL_DIR = "/home/jllopezalvarez/Downloads";
	private static final String LOCAL_FILE = "readme-a.txt";
	private static final String USER_NAME = "ftpuser";

	public static void main(String[] args) {
		// Creamos cliente
		FTPSClient client = null;

		try {
			// Si no se especifica en el constructor, se usa FTPS Explícito. Si se usa
			// new FTPSClient(true) se usaría FTPS implícito.
			client = new FTPSClient();

			client.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

			client.connect(SERVER);

			if (FTPReply.isPositiveCompletion(client.getReplyCode())) {

				String password = getPassword();

				if (client.login(USER_NAME, password)) {

					// Modo pasivo para que funcione desde redes NAT
					client.enterLocalPassiveMode();

					if (client.changeWorkingDirectory(REMOTE_DIR)) {
						// Obtener directorio actual
						String directorioActual = client.printWorkingDirectory();

						System.out.println("Directorio actual: " + directorioActual);

						// Obtenemos el contenido del directorio:
						FTPFile[] ficheros = client.listFiles();

						mostrarContenidoDirectorio(ficheros);

						// Abrimos stream para guardar el fichero.
						String localPath = Paths.get(LOCAL_DIR, LOCAL_FILE).toString();
						try (FileOutputStream outputStream = new FileOutputStream(localPath)) {
							// Descargamos fichero
							if (client.retrieveFile(REMOTE_FILE, outputStream)) {
								System.out.println("Fichero descargado.");
							} else {
								System.out.println("No se ha podido descargar el fichero");
							}
						}

					}
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

	private static String getPassword() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Contraseña:");
		JPasswordField pass = new JPasswordField(15);
		panel.add(label);
		panel.add(pass);
		String[] options = new String[] { "Aceptar", "Cancelar" };
		int option = JOptionPane.showOptionDialog(null, panel, "Contraseña", JOptionPane.NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
		if (option == JOptionPane.OK_OPTION) {
			return String.valueOf(pass.getPassword());
		}
		return null;
	}

	private static void mostrarContenidoDirectorio(FTPFile[] ficheros) {
		for (FTPFile fichero : ficheros) {
			if (fichero != null) {
				System.out.println(fichero.getName());
			}
		}

	}
}
