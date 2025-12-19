package src;

import java.util.ArrayList;
import java.util.HashMap;
import src.ihm.FrameUML;
import src.ihm.Vue;
import src.membres.Association;
import src.membres.Attribut;
import src.membres.Methode;
import src.metier.LireData;
import src.metier.LireDossier;
import src.metier.LireFichier;
import src.metier.LireSauvegarde;
import src.metier.Sauvegarder;

/**
 * Contrôleur principal de l'application de génération de diagrammes UML.
 * Fait le lien entre l'interface graphique (Vue, FrameUML) et la logique métier
 * (lecture de fichiers, sauvegarde, chargement).
 * 
 * Exercice : Générateur de diagrammes UML
 * Groupe : 6
 * Auteurs : Groupe 6
 * Date de création : 10/12/2025 16:00
 */
public class Controleur
{
	/*--------------------------------------------------------------*/
	/* Déclaration des attributs                                    */
	/*--------------------------------------------------------------*/

	private LireDossier    lireDossier;
	private FrameUML       frameUML;
	private Vue            vue;
	private Sauvegarder    sauvegarder;
	private LireSauvegarde charger;

	/*--------------------------------------------------------------*/
	/* Constructeur                                                 */
	/*--------------------------------------------------------------*/

	/**
	* Construit le contrôleur et initialise l'interface graphique.
	*/
	public Controleur()
	{
		this.vue         = new Vue( this );
		this.frameUML    = new FrameUML( this );
		this.sauvegarder = new Sauvegarder();
		this.charger     = new LireSauvegarde();
	}

	/*--------------------------------------------------------------*/
	/* Modificateurs                                                */
	/*--------------------------------------------------------------*/

	/**
	* Définit la position d'une classe dans le diagramme.
	* @param lF le fichier représentant la classe
	* @param x position horizontale
	* @param y position verticale
	*/
	public void setPosition( LireFichier lF, int x, int y )
	{
		this.lireDossier.setPosition( lF, x, y );
	}

	/**
	* Définit le dossier de lecture des fichiers.
	* @param dossier le nouveau dossier à utiliser
	*/
	public void setLireDossier( LireDossier dossier )
	{
		this.lireDossier = dossier;
	}

	/*--------------------------------------------------------------*/
	/* Accesseurs                                                   */
	/*--------------------------------------------------------------*/

	/**
	* Retourne l'objet de lecture du dossier.
	* @return le lecteur de dossier
	*/
	public LireDossier getLireDossier()
	{
		return this.lireDossier;
	}

	/**
	* Retourne la fenêtre principale de l'application.
	* @return la frame UML
	*/
	public FrameUML getFrameUML()
	{
		return this.frameUML;
	}

	/**
	* Retourne la vue textuelle de l'application.
	* @return la vue
	*/
	public Vue getVue()
	{
		return this.vue;
	}

	/**
	* Retourne le nombre de classes chargées.
	* @return nombre de classes
	*/
	public int getNbClasses()
	{
		return this.lireDossier.getNbClasses();
	}

	/**
	* Retourne la liste des fichiers analysés.
	* @return liste des fichiers
	*/
	public ArrayList<LireFichier> getListeFichiers()
	{
		return this.lireDossier.getListeFichiers();
	}

	/**
	* Retourne la liste des associations entre classes.
	* @return liste des associations
	*/
	public ArrayList<Association> getListeAssociation()
	{
		return this.lireDossier.getListeAssociation();
	}

	/**
	* Retourne la map des attributs d'associations.
	* @return map des attributs d'associations
	*/
	public HashMap<Association, ArrayList<String>> gethMAttributsAssociations()
	{
		return this.lireDossier.gethMAttributsAssociations();
	}

	/*--------------------------------------------------------------*/
	/* Autres méthodes                                              */
	/*--------------------------------------------------------------*/

	/**
	* Lit et analyse un dossier contenant des fichiers Java.
	* @param chemin chemin du dossier à lire
	*/
	public void lireDossier( String chemin )
	{
		this.lireDossier = new LireDossier( this, chemin );
	}

	/**
	* Charge un fichier de données sérialisé.
	* @param chemin chemin du fichier à charger
	*/
	public void lireData( String chemin )
	{
		LireData data;
		
		data = new LireData( chemin, this );
	}

	/**
	* Formate l'affichage d'un attribut.
	* @param a l'attribut à afficher
	* @return chaîne formatée de l'attribut
	*/
	public String afficherAttribut( Attribut a )
	{
		return this.vue.afficherAttribut( a );
	}

	/**
	* Formate l'affichage d'une méthode.
	* @param m la méthode à afficher
	* @param complet si true, affiche tous les paramètres
	* @return chaîne formatée de la méthode
	*/
	public String afficherMethode( Methode m, boolean complet )
	{
		return this.vue.afficherMethode( m, complet );
	}

	/**
	* Sauvegarde l'état actuel du diagramme.
	*/
	public void sauvegarder()
	{
		this.sauvegarder.sauvegarder( this.lireDossier );
	}

	/**
	* Charge un fichier de sauvegarde.
	* @param path chemin du fichier à charger
	*/
	public void charger( String path )
	{
		this.charger.charger( this, path );
	}

	/**
	* Vérifie si un nom de classe existe dans le répertoire chargé.
	* @param nomClasse nom de la classe à vérifier
	* @return true si la classe existe, false sinon
	*/
	public boolean nomEstDansRepertoire( String nomClasse )
	{
		return this.lireDossier.nomEstDansRepertoire( nomClasse );
	}

	/*--------------------------------------------------------------*/
	/* Méthode principale                                           */
	/*--------------------------------------------------------------*/

	/**
	* Point d'entrée de l'application.
	* @param args arguments de ligne de commande (non utilisés)
	*/
	public static void main( String[] args )
	{
		new Controleur();
	}
}