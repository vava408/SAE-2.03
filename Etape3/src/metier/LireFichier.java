package src.metier;

import java.io.FileInputStream;
import java.util.*;

import src.ihm.Vue;
import src.membres.Attribut;
import src.membres.Methode;

public class LireFichier
{
	public final String[]             TAB_VISIBILITE = { "public", "private", "protected"                   };
	public final String[]             TAB_MOTCLE     = { "class", "interface", "enum", "record", "abstract" };
	public final String[]             TAB_MODIFIEURS = { "static", "final", "abstract", 
	                                                     "native", "strictfp", "synchronized" };

	private LireDossier               lectureDossier ;
	
	private DecomposerLigne           decomposerLigne;
	private LireMethode 		      lireMethode    ;
	private LireAttribut              lireAttribut   ;
	private Vue                       vue            ;
	
	
	private String                    motCle         ;
	private String                    nomClasse      ;

	// constructeur  prend en paramètre la classe LireDossier et le nom du fichier à lire
	public LireFichier( LireDossier lectureDossier, String fileName) 
	{
		this.lectureDossier  = lectureDossier;

		this.decomposerLigne = new DecomposerLigne();
		this.lireMethode     = new LireMethode    ( this );
		this.lireAttribut    = new LireAttribut   ( this );
		this.vue             = new Vue            ( this );

        lireFichier( fileName );
    }

	//retourne le nom de la classe lue
	public String getNomClasse()
	{
		return this.nomClasse;
	}
	
	//retourne le mot clé de la classe lue (class, interface, enum, record, abstract)
	public String getMotCle()
	{
		return this.motCle;
	}

	//retourne la liste des attributs lus
	public ArrayList<Attribut> getListeAttributs()
	{
		return this.lireAttribut.getListeAttributs();
	}

	public ArrayList<Methode> getListeMethodes()
	{
		return this.lireMethode.getListeMethodes();
	}

	//lit le fichier passé en paramètre 
	private void lireFichier( String fileName )
	{
		Scanner  sc     ;
		String   ligne  ;
		String[] tabMots;
		
		try
		{
			sc = new Scanner ( new FileInputStream ( fileName ), "UTF8" );
	
			while ( sc.hasNextLine() )
			{
				ligne = sc.nextLine();
				ligne = ligne.trim();

				tabMots = this.decomposerLigne.decomposerLigne( ligne );
				
				if ( ! ligne.startsWith( "import" ) && !ligne.isBlank() && ligne.startsWith( "private"    ) ||
				                                                                   ligne.startsWith( "public"    )  ||
																				   ligne.startsWith( "protected" ) )
				{
					if ( this.estLaPremiereLigne( tabMots[1] ) )
					{
						this.motCle    = tabMots[ 1 ];
						this.nomClasse = tabMots[ 2 ];
					}
					else
					{
						if ( ligne.endsWith( ";" ) )
						{
							this.lireAttribut.lireAttribut( tabMots );
						}
						else
						{
							this.lireMethode.lireMethode( tabMots );
						}
					}

				}
			}

			sc.close();
		}
		catch (Exception e){ e.printStackTrace(); }
	}


	//vérifie si le mot passé en paramètre est un mot clé de déclaration de classe
	private boolean estLaPremiereLigne( String mot )
	{
		for ( String motCle : TAB_MOTCLE )
		{
			if ( mot.equals( motCle ) )
			{
				return true;
			}
		}
		return false;
	}

	//affichage de la classe lue sous forme textuelle
	public String toString()
	{
		return this.vue.afficher();
	}
}