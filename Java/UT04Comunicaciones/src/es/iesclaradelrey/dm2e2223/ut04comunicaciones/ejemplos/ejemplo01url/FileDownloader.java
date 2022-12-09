package es.iesclaradelrey.dm2e2223.ut04comunicaciones.ejemplos.ejemplo01url;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class FileDownloader {
	protected final URL url;
	protected final Path path;

	public FileDownloader(String url, String first, String... more) throws MalformedURLException {
		Path testPath = Paths.get(first, more);
		if (!testPath.isAbsolute()) {
			testPath = Paths.get(System.getProperty("user.home"), "Downloads", testPath.toString());
		}
		path = testPath;
		this.url = new URL(url);
	}

	public abstract void download() throws IOException;
}
