package iesclaradelrey.dm2e2223.ut02procesos.ejemplos;

import java.io.IOException;

public class Ejemplo01ALanzarEditor {
   public static void main(String[] args) throws IOException  {	   
	   ProcessBuilder pb = new ProcessBuilder("kate");
	   Process p = pb.start();

   }
}

