package src.metier;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import src.Controleur;

public class LireSauvegarde
{
	public LireDossier charger(Controleur ctrl, String path)
	{
		LireDossier dossier = null;

		try
		{
			System.out.println("Répertoire courant : " + System.getProperty("user.dir"));
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));

			dossier = (LireDossier) in.readObject();
			in.close();
			dossier.reinitialiser(ctrl);
			ctrl.setLireDossier(dossier); 

			// Réinitialisation des champs transient
			dossier.reinitialiser(ctrl);
			System.out.println("Lecture de la sauvegarde terminée.");

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return dossier;
	}
}
