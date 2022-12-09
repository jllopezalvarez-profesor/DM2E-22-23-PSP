package es.iesclaradelrey.dm2e2223.ut04comunicaciones.ejemplos.ejemplo01url;

import java.io.*;
import java.net.MalformedURLException;

public class TextFileDownloader extends FileDownloader {

	public TextFileDownloader(String url, String first, String... more) throws MalformedURLException {
		super(url, first, more);
	}

	@Override
	public void download() throws IOException {
		try (InputStream inputStream = url.openStream()) {
			try (InputStreamReader streamReader = new InputStreamReader(inputStream)) {
				try (BufferedReader bufferedReader = new BufferedReader(streamReader)) {
					File outputFile = new File(path.toString());
					outputFile.createNewFile();
					try (FileWriter fileWriter = new FileWriter(outputFile)) {
						try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
							String line;
							while ((line = bufferedReader.readLine()) != null) {
								bufferedWriter.write(line);
								bufferedWriter.newLine();
							}
						}
					}
				}
			}
		}
	}
}
