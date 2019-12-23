package objetos;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
//import javax.activation.*;



public class Correo 
{

	private MimeMessage mensaje;
	private Properties properties;
	private Session sesion;
	private String asunto;
	private String contenido;
	
	public Correo(String to,String from, String a, String c)
	{
		properties.setProperty("mail.smtp.host", "smtp.gmail.com");

		// TLS si está disponible
		properties.setProperty("mail.smtp.starttls.enable", "true");

		// Puerto de gmail para envio de correos
		properties.setProperty("mail.smtp.port","587");

		// Nombre del usuario
		properties.setProperty("mail.smtp.user", from);

		// Si requiere o no usuario y password para conectarse.
		properties.setProperty("mail.smtp.auth", "true");
		
		sesion = Session.getDefaultInstance(properties); //cargamos la sesion con las propiedades anteriores
		sesion.setDebug(true); //Para obetener informacion por pantalla de lo que esta pasando.
		
		mensaje = new MimeMessage(sesion);
		
		try 
		{
			mensaje.setFrom(new InternetAddress(from));
		} 
		catch (AddressException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (MessagingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try 
		{
			mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		} 
		catch (AddressException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (MessagingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
