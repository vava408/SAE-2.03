package src.metier;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import src.Controleur;

/**
 * Classe permettant de lire une sauvegarde sérialisée depuis un fichier.
 * Exercice : Gestion des sauvegardes 
 * @author : Groupe6
 * Date de création : 18/12/2025 10:30
 */
public class LireSauvegarde
{
	/**
	 * Charge un dossier sauvegardé depuis un fichier.
	 *
	 * @param ctrl
	 *            contrôleur principal de l'application
	 * @param path
	 *            chemin du fichier de sauvegarde
	 * @return le dossier chargé ou null en cas d'erreur
	 */
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
			System.out.println("Erreur lors de la lecture de la sauvegarde : " + "fichier introuvable ou corrompu.");
		}

		return dossier;
	}
}
