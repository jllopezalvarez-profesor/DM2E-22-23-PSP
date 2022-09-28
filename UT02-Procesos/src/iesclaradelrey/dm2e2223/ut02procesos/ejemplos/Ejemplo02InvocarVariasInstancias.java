package iesclaradelrey.dm2e2223.ut02procesos.ejemplos;

import java.io.IOException;
import java.util.Scanner;

public class Ejemplo02InvocarVariasInstancias {

	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		System.out.println("Voy a iniciar n instancias del editor de texto Kate.");

		System.out.println("Introduce el número de veces que quieres que se invoque el editor de texto.");
		int numProcesos = Integer.parseInt(sc.nextLine());

		for (int i = 0; i < numProcesos; i++) {
			ProcessBuilder pb = new ProcessBuilder("kate");
			pb.start();
		}
		System.out.printf("El editor de texto se debería haber abierto %d veces. Yo he acabado mi trabajo y finalizo.\n", numProcesos);
	}

}
