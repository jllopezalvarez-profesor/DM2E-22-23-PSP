package es.iesclaradelrey.dm2e2223.ut05serviciosred.ejemplos.ejemplos02email;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class Ejemplo01SimpleEmail {

	private static final String SMTP_SERVER = "smtp.educa.madrid.org";
	private static final int SMTP_PORT = 587;
	private static final String USER_NAME = "jllopezalvarez";
	private static final String USER_EMAIL = "jllopezalvarez@educa.com";
	private static final String DST_EMAIL = "jllopezalvarez@educa.madrid.org";

	public static void main(String[] args) {

		Email email = new SimpleEmail();

		// HAcemos que muestre en System.out los mensajes de depuración.
		email.setDebug(true);

		// Servidor SMTP al que queremos conectar, y activar TLS
		email.setHostName(SMTP_SERVER);
		email.setSmtpPort(SMTP_PORT);
		email.setStartTLSRequired(true);

		// Usuario y contraseña. La contraseña se pide con JOptionPane para no guardarla
		// en GIT.
		String password = getPassword();
		email.setAuthentication(USER_NAME, password);
		//email.setAuthenticator(new DefaultAuthenticator(USER_NAME, password));

		// Construir y enviar el email
		try {
			email.setFrom(USER_EMAIL);
			email.addTo(DST_EMAIL);
			
			email.setSubject("Mail de prueba");
			email.setMsg("Este es el cuerpo del mail de prueba ... :-)");
			
			email.send();
			System.out.println("Email enviado");
		} catch (EmailException e) {
			System.out.println("Error al enviar el email.");
			e.printStackTrace();
		}
	}

	private static String getPassword() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Contraseña:");
		JPasswordField pass = new JPasswordField(15);
		panel.add(label);
		panel.add(pass);
		String[] options = new String[] { "Aceptar", "Cancelar" };
		int option = JOptionPane.showOptionDialog(null, panel, "Contraseña", JOptionPane.NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (option == JOptionPane.OK_OPTION) {
			return String.valueOf(pass.getPassword());
		}
		return null;
	}
}
