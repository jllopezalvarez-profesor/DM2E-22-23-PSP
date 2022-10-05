package es.iesclaradelrey.dm2e2223.ut00utilidades;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * 
 * @author jllopezalvarez
 *
 * Esto es un trabajo en progreso. Intentando conseguir que se identifique no sólo el SO sino también la distribucion específica.
 */
public class DeteccionSistemaOperativo {
	public static void main(String[] args) {
		System.out.println(DetectarSistemaOperativo());
		
	}

	private static SistemaOperativo DetectarSistemaOperativo() {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("windows")) return SistemaOperativo.WINDOWS;
		if (os.contains("linux")) return DetectarDistroLinux();
		return SistemaOperativo.DESCONOCIDO;
		
	}

	private static SistemaOperativo DetectarDistroLinux() {
		try {
			String contenidoEtcIssue = Files.readString(Path.of("/etc/issue")).toLowerCase();
			if (contenidoEtcIssue.contains("ubuntu")) return SistemaOperativo.UBUNTU;
		} catch (IOException e) {
		}
		return SistemaOperativo.LINUX_GENERIC;
	}
	
	
}
