package src.metier;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import src.membres.Association;
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

			for (LireFichier lf : dossier.getListeFichiers())
			{
				writer.write(lf.getMotCle() + " ");
				writer.write(lf.getNomClasse() + " " + lf.getPosX() + " " + lf.getPosY());
				writer.newLine();

				writer.newLine();
				for (Attribut a : lf.getListeAttributs())
				{
					writer.write("+ " + a.toString());
					writer.newLine();
				}

				writer.newLine();
				for (Methode m : lf.getListeMethodes())
				{
					writer.write("- " + m.toString());
					writer.newLine();
				}
				writer.newLine();

				writer.write("=== \n");
			}


			writer.newLine();

			writer.write("===== ASSOCIATIONS =====");
			writer.newLine();

			for (Association a : dossier.getListeAssociation())
			{
				writer.write("Association : " + a.toString());
				writer.newLine();
			}

			writer.newLine();

			writer.write("===== extends =====");
			writer.newLine();
			for (LireFichier lf : dossier.getListeFichiers())
			{
				for (String ext : lf.getMapHerit().keySet())
				{
					writer.write("Classe " + lf.getNomClasse() + " étend " + lf.getMapHerit().get(ext));
					writer.newLine();
				}
			}

			writer.newLine();

			writer.write("===== implements =====");
			writer.newLine();
			for (LireFichier lf : dossier.getListeFichiers())
			{
				for (String key : lf.getMapImple().keySet())
				{
					for (String impl : lf.getMapImple().get(key))
					{
						writer.write("Classe " + lf.getNomClasse() + " implémente " + impl);
						writer.newLine();
					}
				}
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