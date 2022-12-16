package es.iesclaradelrey.dm2e2223.ut04comunicaciones.ejercicios.ejercicio03;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;


public abstract class FileDownloaderThread extends Thread {
	protected final URL url;
	protected final File directorioDescarga;

	public FileDownloaderThread(String url, File directorioDescarga) throws MalformedURLException {
		this.directorioDescarga = directorioDescarga;
		this.url = new URL(url);
	}
}
