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

			if (dossier != null)
			{
				dossier.reinitialiser(ctrl);
				ctrl.setLireDossier(dossier); 

				// Réinitialisation des champs transient
				dossier.reinitialiser(ctrl);
				System.out.println("Lecture de la sauvegarde terminée.");
			}
			else
			{
				System.out.println("Erreur : la sauvegarde est vide ou corrompue.");
			}

		} catch (Exception e)
		{
			System.out.println("Erreur lors de la lecture de la sauvegarde : Fichier introuvable ou corrompu."); 
			//e.printStackTrace();
		}

		return dossier;
	}
}
