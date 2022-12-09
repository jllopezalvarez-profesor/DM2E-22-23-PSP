package es.iesclaradelrey.dm2e2223.ut04comunicaciones.ejemplos.ejemplo01url;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class BinaryFileDownloader extends FileDownloader {

    public static final int BUFFER_BYTES = 2048;

    public BinaryFileDownloader(String url, String first, String... more) throws MalformedURLException {
        super(url, first, more);
    }

    @Override
    public void download() throws IOException {
        byte buffer[] = new byte[BUFFER_BYTES];
        int bytes;
        try (InputStream inputStream = url.openStream()) {
        	File outputFile = new File(path.toString());        	
        	outputFile.createNewFile();
            try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
                while ((bytes = inputStream.read(buffer, 0, BUFFER_BYTES)) != -1) {
                    fileOutputStream.write(buffer, 0, bytes);
                }
            }
        }
    }
}
