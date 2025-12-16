package src.metier;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Charger
{
	public Charger()
	{
		// À implémenter si besoin
	}

	public LireDossier charger()
	{
		LireDossier dossier = null;

		try
		{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("employee.ser"));
			dossier = (LireDossier) in.readObject();
			in.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return dossier;
	}
}
