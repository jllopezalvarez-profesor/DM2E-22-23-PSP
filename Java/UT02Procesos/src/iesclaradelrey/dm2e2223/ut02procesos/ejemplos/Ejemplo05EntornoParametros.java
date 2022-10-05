package iesclaradelrey.dm2e2223.ut02procesos.ejemplos;


import java.io.*;
import java.util.*;

public class Ejemplo05EntornoParametros {
	public static void main(String args[]) {
		
		//File directorio = new File(".\\bin");
		
		ProcessBuilder test = new ProcessBuilder();
		Map<String, String> entorno = test.environment();
		System.out.println("Variables de entorno:");
		System.out.println(entorno);

		test = new ProcessBuilder("java", "iesclaradelrey.dm2e2223.ut02procesos.ejemplos.Ejemplo04ALectura", "José Luis");		
		// devuelve el nombre del proceso y sus argumentos
		List<String> l = test.command();
		Iterator<String> iter = l.iterator();
		System.out.println("\nArgumentos del comando:");
		while (iter.hasNext())
			System.out.println(iter.next());

		// ejecutamos el comando LS
		test = test.command("ls", "-la", "src");
		try {
			Process p = test.start();
			InputStream is = p.getInputStream();
		    
			System.out.println();
			// mostramos en pantalla caracter a caracter
			 int c;
			 while ((c = is.read()) != -1)
				System.out.print((char) c);
			 is.close();
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}// Ejemplo6
