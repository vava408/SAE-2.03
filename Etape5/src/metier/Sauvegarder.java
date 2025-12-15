package src.metier;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Sauvegarder
{
	public Sauvegarder()
	{
		// À implémenter
	}

	public void sauvegarder(LireDossier dossier)
	{
		try
		{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("employee.ser"));
			out.writeObject(dossier);
			out.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
