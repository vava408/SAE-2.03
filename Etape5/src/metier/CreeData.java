package src.metier;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import src.membres.Association;
import src.membres.Attribut;
import src.membres.Methode;

/**
 * Classe CreeData - Crée un fichier de sauvegarde des données.
 * Exercice : SAE 2.03
 * @author : Groupe 6
 * Date : 19/12/2025 14:30
 */
public class CreeData
{
	/**
	 * Crée un fichier de sauvegarde contenant les classes et associations.
	 * @param dossier le dossier contenant les fichiers à traiter
	 */
	public void creerData( LireDossier dossier )
	{
		if ( dossier == null )
		{
			System.out.println( "Dossier null, aucun .data créé" );
			return;
		}

		try
		{
			BufferedWriter writer =
				new BufferedWriter( new FileWriter( "src/sauvegarde/save.data" ) );

			/* Écrire chaque classe avec ses attributs et méthodes */
			for ( LireFichier lf : dossier.getListeFichiers() )
			{
				this.ecrireClasse( writer, lf );
			}

			writer.newLine();

			/* Écrire les associations */
			this.ecrireAssociations( writer, dossier );

			writer.newLine();

			/* Écrire les héritages */
			this.ecrireHeritages( writer, dossier );

			writer.newLine();

			/* Écrire les implémentations */
			this.ecrireImplementations( writer, dossier );

			writer.close();
			System.out.println( "✔ Fichier save.data créé avec succès" );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
	}

	/**
	 * Écrit une classe avec ses attributs et méthodes.
	 * @param writer le flux d'écriture
	 * @param lf le fichier de la classe
	 * @throws IOException si une erreur d'entrée/sortie survient
	 */
	private void ecrireClasse( BufferedWriter writer, LireFichier lf )
		throws IOException
	{
		writer.write( lf.getMotCle() + " " );
		writer.write( lf.getNomClasse() + " " + lf.getPosX() + " " +
			lf.getPosY() );
		writer.newLine();

		writer.newLine();
		/* Écrire les attributs de la classe */
		for ( Attribut a : lf.getListeAttributs() )
		{
			writer.write( "+ " + a.toString() );
			writer.newLine();
		}

		writer.newLine();
		/* Écrire les méthodes de la classe */
		for ( Methode m : lf.getListeMethodes() )
		{
			writer.write( "- " + m.toString() );
			writer.newLine();
		}
		writer.newLine();

		writer.write( "=== \n" );
	}

	/**
	 * Écrit les associations entre classes.
	 * @param writer le flux d'écriture
	 * @param dossier le dossier contenant les associations
	 * @throws IOException si une erreur d'entrée/sortie survient
	 */
	private void ecrireAssociations( BufferedWriter writer, LireDossier dossier )
		throws IOException
	{
		writer.write( "===== ASSOCIATIONS =====" );
		writer.newLine();

		for ( Association a : dossier.getListeAssociation() )
		{
			writer.write( "Association : " + a.toString() );
			writer.newLine();
		}
	}

	/**
	 * Écrit les héritages (extends) entre classes.
	 * @param writer le flux d'écriture
	 * @param dossier le dossier contenant les classes
	 * @throws IOException si une erreur d'entrée/sortie survient
	 */
	private void ecrireHeritages( BufferedWriter writer, LireDossier dossier )
		throws IOException
	{
		writer.write( "===== extends =====" );
		writer.newLine();

		for ( LireFichier lf : dossier.getListeFichiers() )
		{
			for ( String ext : lf.getMapHerit().keySet() )
			{
				writer.write( "Classe " + lf.getNomClasse() + " étend " +
					lf.getMapHerit().get( ext ) );
				writer.newLine();
			}
		}
	}

	/**
	 * Écrit les implémentations (implements) entre classes et interfaces.
	 * @param writer le flux d'écriture
	 * @param dossier le dossier contenant les classes
	 * @throws IOException si une erreur d'entrée/sortie survient
	 */
	private void ecrireImplementations( BufferedWriter writer,
		LireDossier dossier )
		throws IOException
	{
		writer.write( "===== implements =====" );
		writer.newLine();

		for ( LireFichier lf : dossier.getListeFichiers() )
		{
			for ( String key : lf.getMapImple().keySet() )
			{
				for ( String impl : lf.getMapImple().get( key ) )
				{
					writer.write( "Classe " + lf.getNomClasse() + " implémente " +
						impl );
					writer.newLine();
				}
			}
		}
	}
}