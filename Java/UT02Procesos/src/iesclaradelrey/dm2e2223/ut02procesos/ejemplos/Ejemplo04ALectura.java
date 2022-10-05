package iesclaradelrey.dm2e2223.ut02procesos.ejemplos;

import java.io.*;
import java.util.Scanner;

public class Ejemplo04ALectura {
	public static void main(String[] args) {
		//conStreams();
		conScanner();
	}

	// Versión Streams
	public static void conStreams() {
		try (InputStreamReader in = new InputStreamReader(System.in); BufferedReader br = new BufferedReader(in)) {
			String texto;
			System.out.println("Introduce una cadena (la leeremos con Streams) ....");
			texto = br.readLine();
			System.out.println("Cadena escrita: " + texto);
			in.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Versión Scanner
	public static void conScanner() {
		try (Scanner sc = new Scanner(System.in)) {
			String texto;
			System.out.println("Introduce una cadena (la leeremos con Scanner) ....");
			texto = sc.nextLine();
			System.out.println("Cadena escrita: " + texto);
		}
	}
}
