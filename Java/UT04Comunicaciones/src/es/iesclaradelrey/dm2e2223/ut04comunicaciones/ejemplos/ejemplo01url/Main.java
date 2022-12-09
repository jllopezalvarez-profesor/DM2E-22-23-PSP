package es.iesclaradelrey.dm2e2223.ut04comunicaciones.ejemplos.ejemplo01url;

import java.io.IOException;
import java.net.MalformedURLException;

public class Main {

    public static void main(String[] args) {
        try {
            // Descargar a un fichero en la carpetas de descargas del usuario, porque la ruta de descarga no es absoluta.
            FileDownloader fileDownloader = new TextFileDownloader("https://docs.oracle.com/javase/tutorial/networking/urls/index.html", "url.html");
            fileDownloader.download();

            // Descargar a un fichero específico 
            fileDownloader = new TextFileDownloader("https://docs.oracle.com/javase/tutorial/networking/urls/index.html", System.getProperty("user.home"), "Downloads", "url1.html");
            fileDownloader.download();

            // Descargar un fichero binario usando la clase incorrecta. No podremos abrirlo.
            fileDownloader = new TextFileDownloader("https://iesclaradelrey.es/portal/images/logos/fpclaradelreycm_110x50.png", "logoclara_incorrecto.png");
            fileDownloader.download();

            // Descarga de fichero binario con la clase correcta.
            fileDownloader = new BinaryFileDownloader("https://iesclaradelrey.es/portal/images/logos/fpclaradelreycm_110x50.png", "logoclara.png");
            fileDownloader.download();

            // El binario descarga también texto
            fileDownloader = new BinaryFileDownloader("https://docs.oracle.com/javase/tutorial/networking/urls/index.html", System.getProperty("user.home"), "Downloads", "url2.html");
            fileDownloader.download();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
