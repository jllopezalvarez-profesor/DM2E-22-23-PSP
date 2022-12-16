package es.iesclaradelrey.dm2e2223.ut04comunicaciones.ejercicios.ejercicio03;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.apache.commons.io.FilenameUtils;


public class BinaryFileDownloaderThread extends FileDownloaderThread {

    public static final int BUFFER_BYTES = 2048;

    public BinaryFileDownloaderThread(String url, File directorioDescarga) throws MalformedURLException {
        super(url, directorioDescarga);
    }

    @Override
    public void run() {
        byte buffer[] = new byte[BUFFER_BYTES];
        int bytes;
        try (InputStream inputStream = url.openStream()) {
        	File outputFile = createOutputFile(); 
        	//outputFile.createNewFile();
            try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
                while ((bytes = inputStream.read(buffer, 0, BUFFER_BYTES)) != -1) {
                    fileOutputStream.write(buffer, 0, bytes);
                }
            }
        } catch (IOException e) {
			System.err.println("Error al intentar descargar el fichero " + url.toString());
			e.printStackTrace();
		} 
    }

	private File createOutputFile() throws IOException {
		String nombreFichero = FilenameUtils.getName(url.getPath());
		String extension = "." + FilenameUtils.getExtension(nombreFichero);
		String ficheroSinExtension = FilenameUtils.removeExtension(nombreFichero) + "-";
		return File.createTempFile(ficheroSinExtension, extension, this.directorioDescarga);
	}

}
