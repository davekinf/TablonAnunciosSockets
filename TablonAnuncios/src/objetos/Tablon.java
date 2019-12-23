package objetos;
import java.io.*;
import java.util.*;

public class Tablon implements Serializable, Iterable<Anuncio>
{
	
	private  static ArrayList<Anuncio> tablon;
	
	public Tablon()
	{
		Tablon.tablon = new ArrayList<Anuncio>();
	}
	
	public  Anuncio getAnuncio(int i)
	{
		return Tablon.tablon.get(i);
	}
	
	public void add(Anuncio p)
	{
		Tablon.tablon.add(p);
	}
	public void delete(Anuncio p)
	{
		Tablon.tablon.remove(p);
	}
	public int tam()
	{
		return tablon.size();
	}
	public Iterator<Anuncio> iterator()
	{
		return tablon.iterator();
	}
}
