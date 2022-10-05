package iesclaradelrey.dm2e2223.ut02procesos.ejemplos;

import java.io.IOException;

public class Ejemplo01BInvocarComando {
	
	public static void main(String[] args) throws IOException {
		System.out.println(System.getProperty("os.name"));
		System.getProperties().list(System.out);
		
		System.out.println("Voy a iniciar una instancia del editor de texto Kate.");
		ProcessBuilder pb = new ProcessBuilder("kate");
		pb.start();
		pb = new ProcessBuilder("firefox");
		pb.start();
		System.out.println("El editor de texto se debería haber abierto. Yo he acabado mi trabajo y finalizo.");
	}

}
