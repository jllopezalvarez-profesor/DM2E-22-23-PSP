package iesclaradelrey.dm2e2223.ut02procesos.ejemplos;

import java.io.IOException;

public class Ejemplo07RedireccionMismaSalida {
	public static void main(String args[]) throws IOException {
		ProcessBuilder pb = new ProcessBuilder("ls", ".la", "src");

		// la salida a consola
		pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
		Process p = pb.start();

	}
}// Ejemplo9
