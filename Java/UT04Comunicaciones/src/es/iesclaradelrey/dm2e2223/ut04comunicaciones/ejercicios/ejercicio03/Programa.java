package es.iesclaradelrey.dm2e2223.ut04comunicaciones.ejercicios.ejercicio03;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Programa {

	public static void main(String[] args) throws InterruptedException {
		// Verificar longitud del array de parametros
		if (args.length != 2) {
			System.err.println("Número de parámetros incorrecto");
			mostrarUso();
			return;
		}

		String pathFicheroUrls = args[0];
		String pathDirectorioDescarga = args[1];

		// Reemplazamos ~ por la home del usuario
		pathFicheroUrls = pathFicheroUrls.replaceFirst("^~", System.getProperty("user.home"));
		pathDirectorioDescarga = pathDirectorioDescarga.replaceFirst("^~", System.getProperty("user.home"));

		File ficheroUrls = new File(pathFicheroUrls);
		if (!ficheroUrls.exists()) {
			System.err.println("No se encuentra el fichero con las URL a descargar");
			mostrarUso();
			return;
		}

		File directorioDescargas = new File(pathDirectorioDescarga);
		if (!directorioDescargas.exists()) {
			System.err.println("No se encuentra la carpeta de descargas");
			mostrarUso();
			return;
		}

		List<Thread> hilosDescarga = new ArrayList<>();

		try (Scanner lectorUrls = new Scanner(ficheroUrls)) {
			while (lectorUrls.hasNextLine()) {
				String urlDescargar = lectorUrls.nextLine().trim();
				if (!urlDescargar.isBlank()) {
					try {
						Thread nuevoHilo = new BinaryFileDownloaderThread(urlDescargar, directorioDescargas);
						hilosDescarga.add(nuevoHilo);
						nuevoHilo.start();
					} catch (MalformedURLException e) {
						System.err.println("Error en URL a descargar: " + e.getMessage());
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("No se encuentra el fichero con las URL a descargar");
			mostrarUso();
			return;
		}

		// Esperamos a que terminen, aunque no sea necesario.
		for (Thread thread : hilosDescarga) {
			thread.join();
		}
	}

	private static void mostrarUso() {
		StringBuilder sb = new StringBuilder();
		sb.append("Forma de uso:\n");
		sb.append("java ");
		sb.append(Programa.class.getCanonicalName());
		sb.append(" <fichero con urls a descargar> <directorio en el que descargarlas>\n");
		sb.append("El directorio de descarga debe exisitr previamente.");
		System.out.println(sb.toString());
	}

}
