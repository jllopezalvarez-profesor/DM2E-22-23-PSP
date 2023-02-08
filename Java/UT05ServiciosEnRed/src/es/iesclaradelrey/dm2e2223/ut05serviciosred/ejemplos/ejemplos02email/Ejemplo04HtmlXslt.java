package es.iesclaradelrey.dm2e2223.ut05serviciosred.ejemplos.ejemplos02email;

import java.io.OutputStream;
import java.nio.file.Paths;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * 
 * Ejemplo de uso de XSLT para generar mensajes de correo HTML personalizados.
 * 
 * No está terminado. Se irá actualizando. 
 *
 */
public class Ejemplo04HtmlXslt {

	private static final String SMTP_SERVER = "smtp.educa.madrid.org";
	private static final int SMTP_PORT = 587;
	private static final String USER_NAME = "jllopezalvarez";
	private static final String USER_EMAIL = "jllopezalvarez@educa.madrid.org";
	private static final String DST_EMAIL = "jllopezalvarez@educa.madrid.org";
	private static final String PATH_FICHEROS_LOCALES = "data/emailHtml";
	private static final String PATH_FICHERO_HTML = Paths.get(PATH_FICHEROS_LOCALES, "email.html").toString();
	private static final String PATH_FICHERO_TXT = Paths.get(PATH_FICHEROS_LOCALES, "emailAlternative.txt").toString();
	private static final String PATH_FICHEROS_IMG = Paths.get(PATH_FICHEROS_LOCALES, "images").toString();

	public static void main(String[] args) throws ParserConfigurationException, TransformerException {

		Document d = getXmlUsuario("José Luis", "López");
		
		writeXml(d, System.out);
		

//		// Como el mensaje es HTML con imágenes embebidas se usa la clase ImageHtmlEmail
//		ImageHtmlEmail email = new ImageHtmlEmail();
//
//		// Hacemos que muestre en System.out los mensajes de depuración.
//		email.setDebug(true);
//
//		// Servidor SMTP al que queremos conectar, y activar TLS
//		email.setHostName(SMTP_SERVER);
//		email.setSmtpPort(SMTP_PORT);
//		email.setStartTLSRequired(true);
//
//		// Usuario y contraseña. La contraseña se pide con JOptionPane para no guardarla
//		// en GIT.
//		String password = getPassword();
//		email.setAuthenticator(new DefaultAuthenticator(USER_NAME, password));
//
//		// Construir y enviar el email
//		try {
//			// Origen, destinatario y asunto
//			email.setFrom(USER_EMAIL);
//			email.addTo(DST_EMAIL);
//			email.setSubject("Mail HTML de prueba, con imágenes");
//
//			// Fijamos el resolutor de paths relativos. Esto es para que se interprete bien
//			// la dirección de las rutas relativas.
//			email.setDataSourceResolver(new DataSourceFileResolver(new File(PATH_FICHEROS_LOCALES)));
//
//			// Usamos la clase Files de java.nio para facilitar la lectura de ficheros.
//			// Plantilla de email HTML obtenida de
//			// https://github.com/ColorlibHQ/email-templates
//			String contenidoHtmlCorreo = Files.readString(Paths.get(PATH_FICHERO_HTML));
//			String contenidoTextoCorreo = Files.readString(Paths.get(PATH_FICHERO_TXT));
//
//			// Html del mensaje
//			email.setHtmlMsg(contenidoHtmlCorreo);
//
//			// Texto alternativo
//			email.setTextMsg(contenidoTextoCorreo);
//
//			// Enviar
//			email.send();
//
//			System.out.println("Email enviado.");
//		} catch (EmailException e) {
//			System.out.println("Error al enviar el email.");
//			e.printStackTrace();
//		} catch (IOException e) {
//			System.out.println("Error E/S al preparar o enviar el email.");
//			e.printStackTrace();
//		}
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

	private static Document getXmlUsuario(String nombre, String apellidos) throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		Document doc = builder.newDocument();

		Element nodoNombre = doc.createElement("nombre");
		nodoNombre.setAttribute("nombre", nombre);
		nodoNombre.setAttribute("apellidos", apellidos);

		Element nodoUsuario = doc.createElement("usuario");
		nodoUsuario.appendChild(nodoNombre);
		doc.appendChild(nodoUsuario);

		return doc;
	}

	private static void writeXml(Document doc, OutputStream output) throws TransformerException {

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setParameter(OutputKeys.INDENT, "YES");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(output);

		transformer.transform(source, result);

	}
}
