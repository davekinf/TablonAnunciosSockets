import java.util.Calendar;
import java.util.TimerTask;

import objetos.*;

public class Elimina30Dias extends TimerTask
{
	private Tablon t;
	
	public Elimina30Dias(Tablon t)
	{
		this.t=t;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(Anuncio a:t)
		{
			if(tiene30Dias(a))
			{
				t.delete(a);
			}
		}
	}
	
	public boolean tiene30Dias(Anuncio a)
	{
		long diferencia = a.getFecha()-Calendar.getInstance().getTimeInMillis();
		if(diferencia>=0)
		{
			return true;
		}
		return false;
	}
}
