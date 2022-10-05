package iesclaradelrey.dm2e2223.ut02procesos.ejemplos;

import java.io.File;
import java.io.IOException;

public class Ejemplo06BRedireccion {
	public static void main(String args[]) throws IOException {
		
		ejemploLinux();

	}
	
	private static void ejemploWindows() throws IOException {
		ProcessBuilder pb = new ProcessBuilder("CMD");
		

		File fBat = new File("script.bat");
		File fOut = new File("salida.txt");
		File fErr = new File("error.txt");

		pb.redirectInput(fBat);
		pb.redirectOutput(fOut);
		pb.redirectError(fErr);
		pb.start();
	}
	
	private static void ejemploLinux() throws IOException {
		ProcessBuilder pb = new ProcessBuilder("/bin/bash");
		

		File fBat = new File("script.sh");
		File fOut = new File("salida.txt");
		File fErr = new File("error.txt");

		pb.redirectInput(fBat);
		pb.redirectOutput(fOut);
		pb.redirectError(fErr);
		pb.start();
	}
}
