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
		int index = 0; //para llevar control de donde se esta
		
		Socket ss = null;
		ObjectInputStream ois = null;
		ObjectOutputStream ous = null;
		BufferedReader b = null;
		Writer w = null;
		DataOutputStream dos = null;
		
		try
		{
			ServerSocket s = new ServerSocket(12345);
			while (true)
			{
				ss = s.accept();
				b = new BufferedReader(new InputStreamReader(ss.getInputStream()));
				w = (new OutputStreamWriter(ss.getOutputStream()));
				dos = new DataOutputStream(ss.getOutputStream());
				
				try
				{	
					String orden = b.readLine();
					
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
						publicar(t,p);
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

	public static void publicar(Tablon t,Anuncio p)
	{
		t.add(p);
	}

}
