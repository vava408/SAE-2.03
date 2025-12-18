/**------------------------------------------------------------------------
*- Classe Sauvegarde                                                     
*- Rôle : Creer une sauvegarde en .ser qui est d binaire puis creer un data si on veux lire la sauvegarde
*- Date de création : 17/12/2025
 * 
 * @author Groupe6
 * @version Étape finale
 * ------------------------------------------------------------------------ */

/**
 * La classe {@code Sauvegarde} fournit une fonctionnalité utilitaire
 * permettant d'exporter une image de type {@link java.awt.image.BufferedImage}
 * vers un fichier image au format PNG.
 * <p>
 * Elle se charge :
 * <ul>
 *   <li>de vérifier l'extension du fichier</li>
 *   <li>d'écrire l'image sur le disque</li>
 *   <li>d'informer l'utilisateur du succès ou de l'échec via une boîte de dialogue</li>
 * </ul>
 */

package src.metier;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class Sauvegarder
{
	public void sauvegarder(LireDossier dossier)
	{
		try
		{
			ObjectOutputStream out =
				new ObjectOutputStream(
					new FileOutputStream("src/sauvegarde/save.ser"));

			out.writeObject(dossier);
			out.close();

			//  Création du .data
			CreeData data = new CreeData();
			data.creerData(dossier);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}