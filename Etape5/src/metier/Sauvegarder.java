package src.metier;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Classe Sauvegarder Rôle : Créer une sauvegarde sérialisée (.ser) d'un dossier
 * et générer un fichier .data associé si nécessaire. Exercice : Gestion des
 * sauvegardes 
 * @author : Groupe 6 
 * Date de création : 17/12/2025 14:20
 */
public class Sauvegarder
{
	/**
	 * Sauvegarde un dossier dans un fichier sérialisé.
	 *
	 * @param dossier
	 *            dossier à sauvegarder
	 */
	public void sauvegarder(LireDossier dossier)
	{
		try
		{
			// Création du flux de sortie pour la sérialisation
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/sauvegarde/save.ser"));

			// Écriture de l'objet dossier dans le fichier
			out.writeObject(dossier);
			out.close();

			// Création du fichier .data associé
			CreeData data = new CreeData();
			data.creerData(dossier);
		} catch (Exception e)
		{
			System.out.println("Erreur lors de la création de la sauvegarde.");
		}
	}
}
