import java.io.FileInputStream;
import java.util.*;

public class LireFichier
{
	private static final String[]     TAB_MOTCLE = { "class", "interface", "enum", "record", "abstract" };
	
	private static int                nbAttributs = 0;

	private LectureDossier            lectureDossier ;
	
	private DecomposerLigne           decomposerLigne;
	private LireMethode 		      lireMethode    ;
	private LireAttribut              lireAttribut   ;
	private Vue                       vue            ;
	
	
	private String                    motCle         ;
	private String                    nomClasse      ;

	public LireFichier( LectureDossier lectureDossier, String fileName) 
	{
		this.lectureDossier  = lectureDossier;

		this.decomposerLigne = new DecomposerLigne( this );
		this.lireMethode     = new LireMethode    ( this );
		this.lireAttribut    = new LireAttribut   ( this );
		this.vue             = new Vue            ( this );

        lireFichier( fileName );
    }

	public String getNomClasse()
	{
		return this.nomClasse;
	}
	
	public String getMotCle()
	{
		return this.motCle;
	}

	public ArrayList<Attribut> getListeAttributs()
	{
		return this.lireAttribut.getListeAttributs();
	}

	public ArrayList<Methode> getListeMethodes()
	{
		return this.lireMethode.getListeMethodes();
	}

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

				tabMots = this.decomposerLigne.decomposerLigne( ligne );
				
				if ( ! ligne.startsWith( "import" ) && !ligne.isBlank() && ligne.startsWith("private") || ligne.startsWith("public"))
				{
					if ( this.estLaPremiereLigne( ligne ) )
					{
						this.nomClasse = tabMots[ 2 ];
						this.motCle    = tabMots[ 1 ];
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

	public String toString()
	{
		this.vue.afficher();
	}
}