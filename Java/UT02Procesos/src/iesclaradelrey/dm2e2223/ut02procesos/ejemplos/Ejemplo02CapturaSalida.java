package iesclaradelrey.dm2e2223.ut02procesos.ejemplos;

import java.io.*;

public class Ejemplo02CapturaSalida {
	public static void main(String[] args) throws IOException {

		// Primer parámetro, el nombre del programa, segundo, el primer parámetro del
		// programa, tercero, el segundo, y así...
		Process p = new ProcessBuilder("ls", "-la", "src").start();
		try {
			// Capturamos la salida del programa
			InputStream is = p.getInputStream();

			// Leemos caracter a caracter y lo mostramos en consola
			int c;
			while ((c = is.read()) != -1)
				System.out.print((char) c);

			// Cerramos el fichero
			is.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Espera a que acabe el proceso
		try {
			// Esperamos y recogemos el código de salida. Un 0 es que no hay error.
			int exitVal = p.waitFor();
			System.out.println("Valor de Salida: " + exitVal);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
