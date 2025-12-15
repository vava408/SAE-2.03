package src.metier;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import src.membres.Attribut;

public class LireAttribut implements Serializable
{
	LireFichier lireFichier;

	private ArrayList<Attribut> listeAttributs;
	private int compteurId = 0;


	// constructeur prend en paramètre la classe LireFichier
	public LireAttribut( LireFichier lireFichier ) 
	{
		this.lireFichier = lireFichier;
		this.listeAttributs = new ArrayList<>();
	}

	//retourne la liste des attributs lus
	public ArrayList<Attribut> getListeAttributs() 
	{
		return this.listeAttributs;
	}

	//lit un attribut à partir des mots (lignes) passés en paramètre
	public void lireAttribut(String[] mots) 
	{

		String visibilite = "default";
		String type = "";
		String nom = "";
		boolean isStatic = false;
		boolean isFinal = false;

		//Retrait du ;
		for ( int cpt = 0; cpt < mots.length; cpt++ )
			{
				mots[ cpt ] = mots[ cpt ].replace( ";", "" );
			}

		// 1. Analyse des mots
		for (String m : mots) 
		{

			//parcours du tableau des visibilités pour en trouver une qui correspond
			for ( String s : this.lireFichier.TAB_VISIBILITE )
			{
				if ( s.contains( m ) ) 
				{
					visibilite = m;
					continue;
				}
			}


			//même chose pour static
			if ( m.equals("static") ) 
			{
				isStatic = true;
				continue;
			}

			//même chose pour final
			if (m.equals("final")) 
			{
				isFinal = true;
				continue;
			}
		}

		// 2. Récupérer type et nom
		if (mots.length >= 2) 
		{
			nom  = mots[mots.length - 1];
			type = mots[mots.length - 2];
		}
		else
		{
			System.out.println("Impossible de lire type/nom dans : " + Arrays.toString(mots));
			return;
		}

		// 3. Créer l'objet attribut
		Attribut a = new Attribut(compteurId++, nom, type, visibilite, isStatic, isFinal);

		// 4. Ajouter à la liste
		this.listeAttributs.add(a);
	}

}
