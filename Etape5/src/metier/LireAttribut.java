package src.metier;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import src.membres.Attribut;
import src.membres.Methode;
import src.membres.Parametre;

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

/**
 * Vérifie pour chaque attribut si un getter existe, mais pas de setter,
 * et que l'attribut n'est pas initialisé via un constructeur.
 * Si ces conditions sont remplies, l'attribut est marqué comme "requete".
 */
public void verifierGetters(ArrayList<Methode> listeMethodes)
{
	// Récupération du nom de la classe pour identifier les constructeurs
	String nomClasse = this.lireFichier.getNomClasse();

	// Listes pour séparer constructeurs et autres méthodes
	ArrayList<Methode> listeConstructeurs = new ArrayList<>();
	ArrayList<Methode> autresMethodes = new ArrayList<>();

	// Séparation des constructeurs et des autres méthodes
	for (Methode m : listeMethodes)
	{
		if (m.getNom().equals(nomClasse))
		{
			listeConstructeurs.add(m);
		}
		else
		{
			autresMethodes.add(m);
		}
	}

	// Parcours de tous les attributs
	for (Attribut attribut : this.listeAttributs)
	{
		String nomAttribut = attribut.getNom();
		// On saute si le nom est null ou vide
		if (nomAttribut == null || nomAttribut.isEmpty())
			continue;

		// Construction des noms de getter et setter
		String getterName = "get" + nomAttribut.substring(0, 1).toUpperCase() + nomAttribut.substring(1);
		String setterName = "set" + nomAttribut.substring(0, 1).toUpperCase() + nomAttribut.substring(1);

		boolean hasGetter = false;
		boolean hasSetter = false;
		boolean initializedInConstructor = false;

		// Vérification de la présence d'un getter et d'un setter
		for (Methode methode : autresMethodes)
		{
			if (methode.getNom().equals(getterName) && methode.getParametre().isEmpty())
			{
				hasGetter = true;
			}
			if (methode.getNom().equals(setterName) && methode.getParametre().size() == 1)
			{
				hasSetter = true;
			}
		}

		// Vérification si l'attribut est initialisé dans un constructeur (paramètre)
		for (Methode constructeur : listeConstructeurs)
		{
			for (Parametre param : constructeur.getParametre())
			{
				// On compare le nom du paramètre avec le nom de l'attribut
				if (param.getNom().equals(nomAttribut))
				{
					initializedInConstructor = true;
					break;
				}
			}
			if (initializedInConstructor) break;
		}

		// Marquer l'attribut comme "requete" uniquement si :
		// 1. Un getter existe
		// 2. Aucun setter n'existe
		// 3. L'attribut n'est pas initialisé via un constructeur
		if (hasGetter && !hasSetter && !initializedInConstructor)
		{
			attribut.setRequete(true);
		}
	}
}

	

	//lit un attribut à partir des mots (lignes) passés en paramètre
	public void lireAttribut(String[] mots) 
	{
		String[] collectionsAddOnly = { "arraylist<", "list<", "set<", "hashset<", "linkedlist<", "treeset<" };
		String visibilite = "default";
		String type = "";
		String nom = "";
		boolean isStatic = false;
		boolean isFinal = false;
		boolean isAddOnly = false;
		boolean isRequete = false;

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

			//même chose pour requête (propriété UML)
			if (m.contains("requete") || m.contains("requête")) 
			{
				isRequete = true;
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

		// Vérifier si c'est une collection add-only
		String t = type.trim().toLowerCase();
		for (String coll : collectionsAddOnly)
		{
			if (t.contains(coll))
			{
				isAddOnly = true;
				break; 
			}
		}

		// 3. Créer l'objet attribut
		// isRequete sera défini plus tard via verifierGetters()
		Attribut a = new Attribut(compteurId++, nom, type, visibilite, isStatic, isFinal, isAddOnly, false);

		// 4. Ajouter à la liste
		this.listeAttributs.add(a);
	}

}
