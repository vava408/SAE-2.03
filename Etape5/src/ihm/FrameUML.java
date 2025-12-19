package src.ihm;

import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import src.Controleur;
import src.membres.Association;
import src.membres.Attribut;
import src.membres.Methode;
import src.metier.LireFichier;

/**
 * Fenêtre principale de l'application de génération de diagrammes UML.
 * Gère l'affichage du diagramme, les interactions utilisateur et les
 * opérations d'ouverture/sauvegarde de fichiers.
 * 
 * Exercice : Générateur de diagrammes UML
 * Groupe : 6
 * Auteurs : Groupe 6
 * Date de création : 10/12/2025 15:15
 */
public class FrameUML extends JFrame
{
   /*--------------------------------------------------------------*/
   /* Déclaration des attributs                                    */
   /*--------------------------------------------------------------*/
   
   private Controleur      ctrl;
   private Menu            menuBar;
   private PanelPrincipal  panelPrincipal;
   private JScrollPane     scrollPanelPrincipal;

   /*--------------------------------------------------------------*/
   /* Constructeur                                                 */
   /*--------------------------------------------------------------*/
   
   /**
	* Construit la fenêtre principale de l'application UML.
	* @param ctrl le contrôleur de l'application
	*/
   public FrameUML( Controleur ctrl )
   {
	  this.ctrl = ctrl;

	  this.setTitle   ( "Schéma UML"        );
	  this.setLocation( 50, 25              );
	  this.setSize    ( 1400, 800           );
	  this.setLayout  ( new BorderLayout()  );

	  this.menuBar        = new Menu           ( this );
	  this.panelPrincipal = new PanelPrincipal ( this );

	  /* Création du ScrollPane */
	  this.scrollPanelPrincipal = new JScrollPane( this.panelPrincipal );
	  this.scrollPanelPrincipal.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
	  this.scrollPanelPrincipal.setVerticalScrollBarPolicy  ( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED   );

	  /*------------------------------------------*/
	  /* Positionnement des composants            */
	  /*------------------------------------------*/
	  
	  this.add( this.menuBar              , BorderLayout.NORTH  );
	  this.add( this.scrollPanelPrincipal , BorderLayout.CENTER );

	  /*------------------------------------------*/
	  /* Finalisation                             */
	  /*------------------------------------------*/
	  
	  this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	  this.setVisible( true );

	  this.afficherChoixOuverture();
   }

   /*--------------------------------------------------------------*/
   /* Accesseurs                                                   */
   /*--------------------------------------------------------------*/
   
   /**
	* Retourne le nombre de classes chargées.
	* @return nombre de classes
	*/
   public int getNbClasses()
   {
	  return this.ctrl.getNbClasses();
   }

   /**
	* Retourne la liste des fichiers analysés.
	* @return liste des fichiers
	*/
   public ArrayList<LireFichier> getListeFichiers()
   {
	  return this.ctrl.getListeFichiers();
   }

   /**
	* Retourne la liste des associations entre classes.
	* @return liste des associations
	*/
   public ArrayList<Association> getListeAssociation()
   {
	  return this.ctrl.getListeAssociation();
   }

   /**
	* Retourne la map des attributs d'associations.
	* @return map associant chaque association à ses attributs
	*/
   public HashMap<Association, ArrayList<String>> gethMAttributsAssociations()
   {
	  return this.ctrl.gethMAttributsAssociations();
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
	  this.ctrl.setPosition( lF, x, y );
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
	  this.ctrl.lireDossier( chemin );
	  this.panelPrincipal.instancierPanel();
   }

   /**
	* Charge un fichier de sauvegarde .data.
	* @param chemin chemin du fichier .data à charger
	*/
   public void lireData( String chemin )
   {
	  this.ctrl.lireData( chemin );
	  this.panelPrincipal.instancierPanel();
   }

   /**
	* Affiche une boîte de dialogue pour choisir entre fichier .data ou dossier.
	*/
   private void afficherChoixOuverture()
   {
	  String[] options;
	  int      choix;

	  options = new String[] { "Ouvrir un fichier .data", "Ouvrir un dossier", "Annuler" };

	  choix = JOptionPane.showOptionDialog(
											this,
											"Que voulez-vous ouvrir ?",
											"Ouverture",
											JOptionPane.DEFAULT_OPTION,
											JOptionPane.QUESTION_MESSAGE,
											null,
											options,
											options[0]);

	  if ( choix == 0 )
	  {
		 this.ouvrirFichierData();
	  }
	  else if ( choix == 1 )
	  {
		 this.ouvrirDossier();
	  }
	  else
	  {
		 System.exit( 0 );
	  }
   }

   /**
	* Ouvre un sélecteur de dossier et charge les fichiers Java.
	*/
   private void ouvrirDossier()
   {
	  JFileChooser file;
	  int          retour;
	  File         dossier;

	  file = new JFileChooser();
	  file.setCurrentDirectory( new File( "./src/data" ) );
	  file.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );

	  retour = file.showOpenDialog( this );

	  if ( retour == JFileChooser.APPROVE_OPTION )
	  {
		 dossier = file.getSelectedFile();
		 this.lireDossier( dossier.getAbsolutePath() );
		 this.maj();
	  }
   }

   /**
	* Ouvre un sélecteur de fichier .data et charge la sauvegarde.
	*/
   private void ouvrirFichierData()
   {
	  JFileChooser fileChooser;
	  int          retour;
	  String       fichier;

	  fileChooser = new JFileChooser();
	  fileChooser.setCurrentDirectory( new File( "./src/sauvegarde" ) );
	  fileChooser.setFileFilter( 
		 new javax.swing.filechooser.FileNameExtensionFilter( "Fichier .data", "data" ) );

	  retour = fileChooser.showOpenDialog( this );

	  if ( retour == JFileChooser.APPROVE_OPTION )
	  {
		 fichier = fileChooser.getSelectedFile().getAbsolutePath();
		 this.lireData( fichier );
	  }
   }

   /**
	* Formate l'affichage d'un attribut.
	* @param a l'attribut à afficher
	* @return chaîne formatée de l'attribut
	*/
   public String afficherAttribut( Attribut a )
   {
	  return this.ctrl.afficherAttribut( a );
   }

   /**
	* Formate l'affichage d'une méthode.
	* @param m la méthode à afficher
	* @param complet si true, affiche tous les paramètres
	* @return chaîne formatée de la méthode
	*/
   public String afficherMethode( Methode m, boolean complet )
   {
	  return this.ctrl.afficherMethode( m, complet );
   }

   /**
	* Vérifie si un nom de classe existe dans le répertoire chargé.
	* @param nomClasse nom de la classe à vérifier
	* @return true si la classe existe, false sinon
	*/
   public boolean nomEstDansRepertoire( String nomClasse )
   {
	  return this.ctrl.nomEstDansRepertoire( nomClasse );
   }

   /**
	* Exporte le diagramme UML en image.
	* @param path chemin de sauvegarde de l'image
	*/
   public void exportToImage( String path )
   {
	  this.panelPrincipal.exportToImage( path );
   }

   /**
	* Met à jour l'affichage du diagramme.
	*/
   public void maj()
   {
	  this.panelPrincipal.repaint();
   }

   /**
	* Sauvegarde l'état actuel du diagramme.
	*/
   public void sauvegarder()
   {
	  this.ctrl.sauvegarder();
   }

   /**
	* Charge un fichier de sauvegarde et affiche le résultat.
	* @param path chemin du fichier à charger
	*/
   public void charger( String path )
   {
	  String result;

	  result = "";
	  
	  this.ctrl.charger( path );
	  
	  if ( this.ctrl.getLireDossier() != null )
	  {
		 result = "Chargement terminé.";
		 this.panelPrincipal.instancierPanel();
	  }
	  else
	  {
		 result = "Erreur lors du chargement.";
	  }

	  JOptionPane.showOptionDialog(
		 this,
		 result,
		 "Ouverture",
		 JOptionPane.DEFAULT_OPTION,
		 JOptionPane.QUESTION_MESSAGE,
		 null,
		 new String[] { "OK" },
		 "OK"
	  );
   }
}