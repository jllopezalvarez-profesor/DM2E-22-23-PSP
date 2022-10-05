package iesclaradelrey.dm2e2223.ut02procesos.ejemplos;

import java.io.*;

public class Ejemplo04BEjecutaLectura {

	public static void main(String[] args) throws IOException {

		File directorio = new File("." + File.separator + "bin"); // Para que funcione en Windows y Linux.
		ProcessBuilder pb = new ProcessBuilder("java", "iesclaradelrey.dm2e2223.ut02procesos.ejemplos.Ejemplo04ALectura");
		pb.directory(directorio);

		// Se ejecuta el proceso
		Process p = pb.start();

		// Escritura -- envia entrada
		OutputStream os = p.getOutputStream();
		os.write("Hola Manuel\n".getBytes());
		os.flush(); // Vacía el buffer de salida

		// Lectura -- obtiene la salida
		InputStream is = p.getInputStream();
		int c;
		while ((c = is.read()) != -1)
			System.out.print((char) c);
		is.close();

		// COMPROBACION DE ERROR - 0 bien - 1 mal
		int exitVal;
		try {
			exitVal = p.waitFor();
			System.out.println("Valor de Salida: " + exitVal);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		try {
			InputStream er = p.getErrorStream();
			BufferedReader brer = new BufferedReader(new InputStreamReader(er));
			String liner = null;
			while ((liner = brer.readLine()) != null)
				System.err.println("ERROR >" + liner);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}// Ejemplo5
