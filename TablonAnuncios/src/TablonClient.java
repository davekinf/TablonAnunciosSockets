import java.io.*;
import java.net.*;
import java.util.*;
import objetos.Anuncio;
import objetos.Tablon;
public class TablonClient {
	
	public static void main(String[] args)
	{
		String n;
		System.out.println("1. Ver.");
		System.out.println("2. Publicar.");
		System.out.println("Salir pulsar cualquier otra tecla.");
		System.out.println("Introduce opción: ");
		Scanner e = new Scanner(System.in);
		n = e.next();
		
		while((n.equals("1"))||(n.equals("2")))
		{
			switch(n)
			{
				case "1": 
				{
					TablonClient.ver();
					break;
				}
				case "2":
				{
					Anuncio p = TablonClient.crearAnuncio();
					System.out.println("Perfil creado");
					TablonClient.publicar(p); 
					break;
				}
			}
			System.out.println("1. Ver.");
			System.out.println("2. Publicar.");
			System.out.println("Salir pulsar cualquier otra tecla.");
			System.out.println("Introduce opción: ");
			n = e.next();
		}
	}
	
	public static void ver()
	{
		Socket s = null;           //Deben estar fuera del try para poder cerrarlos en el finally
		Writer w = null;
		ObjectInputStream dis = null;
		InputStream is = null;
		
		try
		{
			s = new Socket("localhost",12345); //establecemos la conexion PETA
			w = new OutputStreamWriter(s.getOutputStream()); //Para escribir
			
			//Mostramos primer anuncio
			
			Anuncio resultado = null; 
		    String primerContacto = "CON";//empieza por con es el comienzo
			w.write(primerContacto+"\r\n"); //cargamos el nombre
			w.flush();//enviamos la marca
			
			//is=s.getInputStream();
			dis = dis=new ObjectInputStream(s.getInputStream());
			
			resultado = (Anuncio) dis.readObject(); 
			resultado.mostrar();
			
			String n;
			System.out.println("1. Siguiente.");
			System.out.println("2. Anterior.");
			System.out.println("3. Salir.");
			System.out.println("Introduce opción: ");
			Scanner e = new Scanner(System.in);
					
			
			n = e.next();
			
			while((n.equals("1"))||(n.equals("2")))
			{
				switch(n)
				{
					case "1": 
					{
						String marca = "SIG";//empieza por SIG  para indicarle al servidor que muestre el siguiente.
						w.write(marca+"\r\n"); //cargamos el nombre
						w.flush();//enviamos el nombre
						
						dis=new ObjectInputStream(s.getInputStream());
						resultado = (Anuncio) dis.readObject(); 
						resultado.mostrar();
						break;
					}
					case "2":
					{
						String marca = "ANT";//empieza por ANT para indicarle al servidor que quere el anterior
						w.write(marca+"\r\n"); //cargamos el nombre
						w.flush();//enviamos el nombre
						dis=new ObjectInputStream(s.getInputStream());
						resultado = (Anuncio) dis.readObject(); 
						resultado.mostrar();
						break;
					}
				}
				
				System.out.println("1. Siguiente.");
				System.out.println("2. Anterior.");
				System.out.println("Cualquier tecla: Salir.");
				System.out.println("Introduce opción: ");
				n = e.next();
			}
		}
		catch (IOException ex)
		{
			 ex.printStackTrace();
		} 
		catch (ClassNotFoundException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally//se trata aparte y como puede fallar cada cerrar cada uno debe ir con su propio try/catch
		{
			if(s != null)
				try {
					s.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			if(dis != null)
				try {
					dis.close();
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
	
	public static void publicar(Anuncio p)
	{
		Socket s = null;           //Deben estar fuera del try para poder cerrarlos en el finally
		ObjectOutputStream o = null;
		DataInputStream r = null;
		Writer w = null;
		try
		{
			s = new Socket("localhost",12345); //establecemos la conexion
		    r = new DataInputStream(s.getInputStream());//Para leer
		    w = (new OutputStreamWriter(s.getOutputStream()));
		  
		    w.write("PUB"+"\r\n");
		    w.flush();
		    
		    boolean preparado = r.readBoolean();
		    if(preparado) System.out.println("Listo para enviar anuncio.");
		    
			o = new ObjectOutputStream(s.getOutputStream()); //Para escribor

		    o.writeObject(p); //cargamos el perfil
		    o.flush(); //mandamos el perfil
		    
		    System.out.println("Respuesta del servidor: " + r.readLine());
		    
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(s != null)
				try {
					s.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(r != null)
				try {
					r.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			if(o != null)
				try 
				{
					o.close();
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
	
	public static Anuncio crearAnuncio()
	{
		Scanner escaner = new Scanner(System.in);
		String nombre;
		String correo;
		String mensaje;
		String titulo;
		
		System.out.println("Introduce nombre: ");
		nombre = escaner.nextLine();

		System.out.println("Introduce titulo: ");
		titulo = escaner.nextLine();
		
		System.out.println("Introduce mensaje: ");
		mensaje = escaner.nextLine();
		
		System.out.println("Introduce correo: ");
		correo = escaner.nextLine();
		
		Anuncio p = new Anuncio(nombre,titulo,mensaje,correo);
		return p;
	}
	
	public static void mostrar(Anuncio resultado)
	{
		if(resultado == null)
		{
			System.out.println("No hay.");
		}
		else 
		{
			resultado.mostrar();
		}
	}

}
