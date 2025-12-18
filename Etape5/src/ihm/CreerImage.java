/** ------------------------------------------------------------------------
 * Classe CreerImage
 * Rôle : Gestion de l'export d'une image BufferedImage vers un fichier PNG
 *        sur le disque.
 * Date de création : 17/12/2025
 * 
 * @author Groupe6
 * @version Étape finale
 * ------------------------------------------------------------------------ */

package src.ihm;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * La classe {@code CreerImage} fournit une fonctionnalité utilitaire permettant
 * d'exporter une image de type {@link java.awt.image.BufferedImage} vers un
 * fichier image au format PNG.
 * 
 * Elle se charge notamment :
 * 
 * de vérifier la présence de l'extension .png
 * d'écrire l'image sur le disque
 * d'informer l'utilisateur du succès ou de l'échec de l'opération
 * 
 */
public class CreerImage
{

	/**
	 * Exporte une image {@link BufferedImage} vers un fichier PNG.
	 * 
	 * Si le chemin fourni ne se termine pas par l'extension .png,
	 * celle-ci est automatiquement ajoutée.
	 *
	 * @param img
	 *            l'image à exporter (ne doit pas être {@code null})
	 * @param path
	 *            le chemin du fichier de destination (avec ou sans extension)
	 * @return {@code true} si l'export s'est déroulé avec succès, {@code false}
	 *         en cas d'erreur
	 */
	public boolean exportToImage(BufferedImage img, String path)
	{
		// Affiche dans la console le chemin d'export demandé
		System.out.println("Exportation de l'image vers : " + path);

		try
		{
			// Vérifie que le chemin contient l'extension .png
			if (!path.endsWith(".png"))
			{
				path += ".png";
			}

			// Création du fichier de sortie
			File outputFile = new File(path);

			// Écriture de l'image au format PNG sur le disque
			ImageIO.write(img, "png", outputFile);

			// Message de confirmation pour l'utilisateur
			JOptionPane.showMessageDialog(null, "Image exportée avec succès : " + outputFile.getAbsolutePath());

			// Message de confirmation dans la console
			System.out.println("Image créée avec succès : " + outputFile.getAbsolutePath());

			return true;
		} catch (Exception e)
		{
			// Affichage de la trace de l'erreur pour le débogage
			e.printStackTrace();

			// Message d'erreur affiché à l'utilisateur
			JOptionPane.showMessageDialog(null, "Erreur lors de l'export : " + e.getMessage());

			return false;
		}
	}
}
