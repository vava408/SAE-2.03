package src.metier;

import java.io.Serializable;
import java.util.ArrayList;
import src.membres.Methode;
import src.membres.Parametre;

/**
 * Classe chargée de lire et analyser les méthodes d'une classe Java.
 * Détecte la visibilité, les modificateurs (static, final), les paramètres
 * et le type de retour de chaque méthode ou constructeur.
 * 
 * Exercice : Générateur de diagrammes UML
 * Groupe : 6
 * Auteurs : Groupe 6
 * Date de création : 10/12/2025 14:45
 */
public class LireMethode implements Serializable
{
   /*--------------------------------------------------------------*/
   /* Déclaration des attributs                                    */
   /*--------------------------------------------------------------*/
   
   private LireFichier           lireFichier;
   private ArrayList<Methode>    listeMethodes;

   /*--------------------------------------------------------------*/
   /* Constructeurs                                                */
   /*--------------------------------------------------------------*/
   
   /**
	* Construit un analyseur de méthodes lié à un fichier.
	* @param lireFichier objet permettant l'accès au fichier analysé
	*/
   public LireMethode( LireFichier lireFichier )
   {
	  this.lireFichier   = lireFichier;
	  this.listeMethodes = new ArrayList<Methode>();
   }

   /**
	* Construit un analyseur de méthodes avec une liste existante.
	* @param lstMethode liste de méthodes à initialiser
	*/
   public LireMethode( ArrayList<Methode> lstMethode )
   {
	  this.listeMethodes = lstMethode;
   }

   /*--------------------------------------------------------------*/
   /* Accesseurs                                                   */
   /*--------------------------------------------------------------*/
   
   /**
	* Retourne la liste des méthodes analysées.
	* @return liste des méthodes de la classe
	*/
   public ArrayList<Methode> getListeMethodes()
   {
	  return this.listeMethodes;
   }

   /*--------------------------------------------------------------*/
   /* Autres méthodes                                              */
   /*--------------------------------------------------------------*/
   
   /**
	* Analyse une ligne découpée pour extraire signature et paramètres d'une méthode.
	* @param mots tableau de mots issus d'une ligne de code Java
	*/
   public void lireMethode( String[] mots )
   {

		ArrayList<Parametre>  tabParametre                ;
		String                visibilite   , nom          ;
		String                typeParametre, nomParametre ;
		String                typeRetour                  ;
		boolean               estStatic    , estFinal     ;
		boolean               constructeur                ;
		int                   nbParametre  , cpt          ;

		/* Initialisation des variables */
		nbParametre  = 0;
		nom          = "";
		typeRetour   = "";
		estStatic    = false;
		estFinal     = false;
		constructeur = false;
		tabParametre = new ArrayList<Parametre>();
		visibilite   = mots[0];
		cpt          = 1;

		/* Parcours des mots pour détecter modificateurs et nom de méthode */
		for ( int i = 0; i < mots.length; i++ )
		{
			String m = mots[i];
			
			if ( m == null )
				break;

			/* Détection de "static" */
			if ( m.equals( "static" ) )
			{
				estStatic = true;
				continue;
			}

			/* Détection de "final" */
			if ( m.equals( "final" ) )
			{
				estFinal = true;
				continue;
			}

			/* Vérification des modificateurs */
			for ( String mod : this.lireFichier.TAB_MODIFIEURS )
			{
				if ( mod.equals( m ) )
				{
				cpt++;
				continue;
				}
			}

			/* Détection d'un constructeur */
			if ( this.lireFichier.getNomClasse().equals( m ) )
			{
				constructeur = true;
			}

			/* Vérification des mots-clés interdits */
			for ( String mot : this.lireFichier.TAB_MOTCLE )
			{
				if ( m.equals( mot ) )
				return;
			}
		}

		/* Extraction du nom et du type de retour */
		if ( cpt <= mots.length )
		{
			if ( constructeur )
			{
				typeRetour = null;
				nom        = mots[cpt];
				cpt       += 1;
			}
			else
			{
				typeRetour = mots[cpt];
				nom        = mots[cpt + 1];
				cpt       += 2;
			}

			/* Extraction des paramètres */
			while ( cpt + 1 < mots.length )
			{
				typeParametre = mots[cpt];
				nomParametre  = mots[cpt + 1];
				nbParametre++;

				tabParametre.add( new Parametre( nbParametre, nomParametre, typeParametre ) );
				
				cpt += 2;
			}
		}

		/* Création et ajout de la méthode à la liste */
		Methode methode = new Methode( nom, visibilite, typeRetour, tabParametre, 
										estStatic, estFinal );
		this.listeMethodes.add( methode );
	}
}