package src.metier;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import src.membres.Attribut;
import src.membres.Methode;

public class CreeData
{
	public void creerData(LireDossier dossier)
	{
		if (dossier == null)
		{
			System.out.println("Dossier null, aucun .data créé");
			return;
		}

		try
		{
			BufferedWriter writer =
				new BufferedWriter(new FileWriter("src/sauvegarde/save.data"));

			writer.write("===== SAUVEGARDE UML (LECTURE HUMAINE) =====");
			writer.newLine();
			writer.newLine();

			writer.write("Nombre de classes : " + dossier.getNbClasses());
			writer.newLine();
			writer.newLine();

			for (LireFichier lf : dossier.getListeFichiers())
			{
				writer.write("Classe : " + lf.getNomClasse());
				writer.newLine();

				writer.write("  Attributs :");
				writer.newLine();
				for (Attribut a : lf.getListeAttributs())
				{
					writer.write("    - " + a.toString());
					writer.newLine();
				}

				writer.write("  Méthodes :");
				writer.newLine();
				for (Methode m : lf.getListeMethodes())
				{
					writer.write("    - " + m.toString());
					writer.newLine();
				}

				writer.newLine();
			}

			writer.close();
			System.out.println("✔ Fichier save.data créé avec succès");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
