import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import objetos.Anuncio;
import objetos.Tablon;


public class HiloPeticiones extends Thread
{
	private Tablon t;
	private Socket s;
	private ObjectInputStream ois;
	private ObjectOutputStream ous;
	private BufferedReader b;
	private Writer w;
	private DataOutputStream dos;
	
	public HiloPeticiones(Socket cliente,Tablon t)
	{
		this.t = t;
		this.s=cliente;
		this.ous = null;
		this.ois = null;
		this.b = null;
		this.w = null;
		this.dos = null;
	}
	
	public void desconectar() 
	{
		try {this.s.close();}
		catch (IOException ex) {Logger.getLogger(HiloPeticiones.class.getName()).log(Level.SEVERE, null, ex);}
	}
	
	public void run()
	{
		int index = 0;
		
		try
		{
			while (true)
			{
				ss = s.accept();
				b = new BufferedReader(new InputStreamReader(ss.getInputStream()));
				w = (new OutputStreamWriter(ss.getOutputStream()));
				dos = new DataOutputStream(ss.getOutputStream());
				
				try
				{	
					String orden = b.readLine();
					while(orden.startsWith("CON")||orden.startsWith("PUB")||orden.startsWith("SIG")||orden.startsWith("ANT"))
					{
						//String orden = b.readLine();
						
						if(orden.startsWith("CON"))
						{
							//mandamos el inicial
							ous = new ObjectOutputStream(ss.getOutputStream());
							ous.writeObject(t.getAnuncio(index));
							ous.flush();
						}
						if(orden.startsWith("PUB"))
						{
							//Aqui llega a entrar
							dos.writeBoolean(true);
							dos.flush();
							ois = new ObjectInputStream(ss.getInputStream());
							Anuncio p = (Anuncio) ois.readObject();//falla aqui
							t.add(p);
							w.write("Publicacion con exito."+"\r\n");
							w.flush();
						}
						if(orden.startsWith("SIG"))
						{
							ous = new ObjectOutputStream(ss.getOutputStream());
							if(index == t.tam() - 1) //si estamos en el ultimo pasamos al principio
							{
								index = 0;
								ous.writeObject(t.getAnuncio(index));
								ous.flush();
							}
							else
							{
								index++;
								ous.writeObject(t.getAnuncio(index));
								ous.flush();
							}
						}
						if(orden.startsWith("ANT"))
						{
							ous = new ObjectOutputStream(ss.getOutputStream());
							if(index == 0)
							{
								index = t.tam() - 1;
								ous.writeObject(t.getAnuncio(index));
								ous.flush();
							}
							else
							{
								index--;
								ous.writeObject(t.getAnuncio(index));
								ous.flush();
							}
						}
						orden = b.readLine();
					}
					
				}
				catch(IOException e1)
				{
					e1.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(ss != null)
				try {
					ss.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(ois != null)
				try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			if(ous != null)
				try 
				{
					ous.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			if(w != null)
				try {
					w.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
