package src.metier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe chargée de lire et analyser les relations d'héritage (extends) et
 * d'implémentation (implements) dans une déclaration de classe Java.
 * 
 * Exercice : Générateur de diagrammes UML
 * Groupe : 6
 * Auteurs : Groupe 6
 * Date de création : 10/12/2025 14:30
 */
public class LireHeritImple implements Serializable
{
   /*--------------------------------------------------------------*/
   /* Déclaration des attributs                                    */
   /*--------------------------------------------------------------*/
   
   private LireFichier                         lireFichier;
   private transient DecomposerLigne           decomposerLigne;
   private ArrayList<String>                   listeImplements;
   private HashMap<String, ArrayList<String>>  mapImplements;
   private HashMap<String, String>             mapExtends;
   private String[]                            mot;

   /*--------------------------------------------------------------*/
   /* Constructeur                                                 */
   /*--------------------------------------------------------------*/
   
   /**
	* Construit un analyseur d'héritage et d'implémentation.
	* @param lireFichier objet permettant l'accès au fichier analysé
	*/
   public LireHeritImple( LireFichier lireFichier )
   {
	  this.lireFichier     = lireFichier;
	  this.decomposerLigne = new DecomposerLigne();
	  this.mapImplements   = new HashMap<>();
	  this.mapExtends      = new HashMap<>();
	  this.listeImplements = new ArrayList<>();
   }

   /*--------------------------------------------------------------*/
   /* Modificateurs                                                */
   /*--------------------------------------------------------------*/
   
   /**
	* Définit une relation d'héritage entre une classe fille et sa classe mère.
	* @param classeFille nom de la classe fille
	* @param classeMere nom de la classe mère
	*/
   public void setHerit( String classeFille, String classeMere )
   {
	  this.mapExtends.put( classeFille, classeMere );
   }

   /**
	* Définit les interfaces implémentées par une classe.
	* @param classeFille nom de la classe implémentant les interfaces
	* @param listeInterfaces liste des interfaces implémentées
	*/
   public void setImplement( String classeFille, ArrayList<String> listeInterfaces )
   {
	  this.mapImplements.put( classeFille, listeInterfaces );
   }

   /*--------------------------------------------------------------*/
   /* Autres méthodes                                              */
   /*--------------------------------------------------------------*/
   
   /**
	* Analyse une ligne découpée pour détecter nom de classe, héritage et interfaces.
	* @param mots tableau de mots issus d'une ligne de code Java
	*/
   public void lireHeritImple( String[] mots )
   {
	  String nomClasse;
	  int    indexClass, indexExtends, indexImplements;

	  /* Recherche de l'index du mot "class" */
	  indexClass = -1;
	  for ( int i = 0; i < mots.length; i++ )
	  {
		 if ( mots[i].equals( "class" ) )
		 {
			indexClass = i;
			break;
		 }
	  }

	  /* Si "class" n'existe pas ou qu'il n'y a pas de nom après, on arrête */
	  if ( indexClass == -1 || indexClass + 1 >= mots.length )
		 return;

	  /* Le nom de la classe est le mot juste après "class" */
	  nomClasse = mots[indexClass + 1];

	  /* Recherche du mot "extends" */
	  indexExtends = -1;
	  for ( int i = indexClass + 2; i < mots.length; i++ )
	  {
		 if ( mots[i].equals( "extends" ) )
		 {
			indexExtends = i;
			break;
		 }
	  }

	  /* Recherche du mot "implements" */
	  indexImplements = -1;
	  for ( int i = indexClass + 2; i < mots.length; i++ )
	  {
		 if ( mots[i].equals( "implements" ) )
		 {
			indexImplements = i;
			break;
		 }
	  }

	  /*------------------------------------------*/
	  /* Gestion de l'héritage (extends)          */
	  /*------------------------------------------*/
	  
	  if ( indexExtends != -1 )
	  {
		 int endExtends;
		 
		 /* La fin du extends est soit "implements", soit la fin de la ligne */
		 endExtends = ( indexImplements != -1 ) ? indexImplements : mots.length;

		 /* Vérifie qu'il existe bien une classe après "extends" */
		 if ( indexExtends + 1 < endExtends )
		 {
			String superClass = mots[indexExtends + 1];
			this.mapExtends.put( nomClasse, superClass );
		 }
	  }

	  /*------------------------------------------*/
	  /* Gestion des interfaces (implements)      */
	  /*------------------------------------------*/
	  
	  if ( indexImplements != -1 )
	  {
		 ArrayList<String> lstInterface = new ArrayList<>();

		 /* Parcourt les mots après "implements" */
		 for ( int i = indexImplements + 1; i < mots.length; i++ )
		 {
			String mot = mots[i];

			/* Ignore les virgules et les mots vides */
			if ( !mot.equals( "," ) && !mot.isEmpty() )
			{
			   lstInterface.add( mot );
			}
		 }

		 /* Association de la classe avec ses interfaces */
		 this.mapImplements.put( nomClasse, lstInterface );
	  }
   }

   /*--------------------------------------------------------------*/
   /* Accesseurs                                                   */
   /*--------------------------------------------------------------*/
   
   /**
	* Retourne la map des interfaces implémentées par chaque classe.
	* @return map associant chaque classe à ses interfaces
	*/
   public HashMap<String, ArrayList<String>> getMapImplements()
   {
	  return this.mapImplements;
   }

   /**
	* Retourne la map des relations d'héritage.
	* @return map associant chaque classe à sa classe mère
	*/
   public HashMap<String, String> getMapExtends()
   {
	  return this.mapExtends;
   }
}