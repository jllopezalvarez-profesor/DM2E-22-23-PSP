package es.iesclaradelrey.dm2e2223.ut00utilidades;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


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
		String[] cmd = {"/bin/sh", "-c", "cat /etc/os-release | grep '^NAME'"};
		String distro = "";
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			if ((distro = br.readLine()) == null) return SistemaOperativo.LINUX_DISTRO;
			distro = distro.replace("NAME=", "").replaceAll("\"", "");
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(distro);
		return  SistemaOperativo.LINUX_DISTRO;
	}
	
	
}
