package iesclaradelrey.dm2e2223.ut02procesos.ejemplos;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Ejemplo03CapturarSalida {

	public static void main(String[] args) throws IOException {
		System.out.println("Voy a lanzar un ls del directorio actual y a mostrarlo en la consola.");
		ProcessBuilder pb = new ProcessBuilder("kate");
		Process p = pb.start();
//		try(InputStream is = p.getErrorStream()){
//			int c;
//			while ((c=is.read()) != -1) {
//				System.out.print((char)c);
//			}
//		}
		
		// Obtener valores de salida
		try {
			int exitCode = p.waitFor();
			System.out.println("El programa ha terminado con código " + exitCode);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
