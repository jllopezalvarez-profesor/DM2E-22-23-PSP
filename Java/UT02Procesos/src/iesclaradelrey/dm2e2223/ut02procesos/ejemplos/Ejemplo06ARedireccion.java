package iesclaradelrey.dm2e2223.ut02procesos.ejemplos;

import java.io.File;
import java.io.IOException;
 
public class Ejemplo06ARedireccion { 
  public static void main(String args[]) throws IOException {
    ProcessBuilder pb = new ProcessBuilder("ls","-la" ,"/etc");
    
    File fOut = new File("salida.txt");
    File fErr = new File("error.txt");
 
    pb.redirectOutput(fOut);
    pb.redirectError(fErr); 
    pb.start(); 
  }
}// Ejemplo7
