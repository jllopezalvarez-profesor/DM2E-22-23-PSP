package es.iesclaradelrey.dm2e2223.ut04comunicaciones.ejercicios.ejercicio04;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Programa {

	private static final int DEF_MAX_NUM_DESCARGAS = 2;

	public static void main(String[] args) throws InterruptedException {
		// Verificar longitud del array de parametros
		if (args.length < 2 || args.length > 3) {
			System.err.println("Número de parámetros incorrecto");
			mostrarUso();
			return;
		}

		String pathFicheroUrls = args[0];
		// Reemplazamos ~ por la home del usuario
		pathFicheroUrls = pathFicheroUrls.replaceFirst("^~", System.getProperty("user.home"));
		File ficheroUrls = new File(pathFicheroUrls);
		if (!ficheroUrls.exists()) {
			System.err.println("No se encuentra el fichero con las URL a descargar");
			mostrarUso();
			return;
		}

		String pathDirectorioDescarga = args[1];
		// Reemplazamos ~ por la home del usuario
		pathDirectorioDescarga = pathDirectorioDescarga.replaceFirst("^~", System.getProperty("user.home"));
		File directorioDescargas = new File(pathDirectorioDescarga);
		if (!directorioDescargas.exists()) {
			System.err.println("No se encuentra la carpeta de descargas");
			mostrarUso();
			return;
		}

		int numMaximoDescargas = DEF_MAX_NUM_DESCARGAS;
		if (args.length == 3) {
			try {
				numMaximoDescargas = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				System.err.println("Número máximo de descargas erróneo.");
				mostrarUso();
				return;
			}
		}

		ExecutorService executor = Executors.newFixedThreadPool(numMaximoDescargas);
		List<Future<?>> respuestas = new ArrayList<>();

		try (Scanner lectorUrls = new Scanner(ficheroUrls)) {
			while (lectorUrls.hasNextLine()) {
				String urlDescargar = lectorUrls.nextLine().trim();
				if (!urlDescargar.isBlank()) {
					try {
						Thread nuevoHilo = new BinaryFileDownloaderThread(urlDescargar, directorioDescargas);
						respuestas.add(executor.submit(nuevoHilo));
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

		// Esperamos..
		for (Future<?> respuesta : respuestas) {
			try {
				respuesta.get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Finalizamos el executor
		executor.shutdown();

	}

	private static void mostrarUso() {
		StringBuilder sb = new StringBuilder();
		sb.append("Forma de uso:\n");
		sb.append("java ");
		sb.append(Programa.class.getCanonicalName());
		sb.append(
				" <fichero con urls a descargar> <directorio en el que descargarlas> [<número máximo de descargas>]\n");
		sb.append("El directorio de descarga debe exisitr previamente.");
		System.out.println(sb.toString());
	}

}
