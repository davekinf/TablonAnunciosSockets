import java.io.*;
import java.net.*;
import objetos.Anuncio;
import objetos.Tablon;

public class TablonServ {
	
	public static void main(String[] args)
	{
		Tablon t = new Tablon();
		Anuncio presentacion = new Anuncio("admin","Bienvenido","Sea Lobre de navegar","admin@jopmail.com");
		t.add(presentacion);
		
		ServerSocket ss;
		try
		{
			Socket cliente;
			ss = new ServerSocket(12345);
			HiloPeticiones sh = null;
			while(true)
			{
				cliente = ss.accept();
				sh = new HiloPeticiones(cliente,t);
				sh.start();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
