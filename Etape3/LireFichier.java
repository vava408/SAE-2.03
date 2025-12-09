import java.io.FileInputStream;
import java.util.*;

public class LireFichier
{
	public static final String[]      TAB_VISIBILITE = { "public", "private", "protected" };
	public static final String[]      TAB_MOTCLE     = { "class", "interface", "enum", "record", "abstract" };

	private LireDossier               lectureDossier ;
	
	private DecomposerLigne           decomposerLigne;
	private LireMethode 		      lireMethode    ;
	private LireAttribut              lireAttribut   ;
	private Vue                       vue            ;
	
	
	private String                    motCle         ;
	private String                    nomClasse      ;

	public LireFichier( LireDossier lectureDossier, String fileName) 
	{
		this.lectureDossier  = lectureDossier;

		this.decomposerLigne = new DecomposerLigne();
		this.lireMethode     = new LireMethode    ();
		this.lireAttribut    = new LireAttribut   ();
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

	/*public ArrayList<Methode> getListeMethodes()
	{
		return this.lireMethode.getListeMethodes();
	}*/

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
							//this.lireMethode.lireMethode( tabMots );
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
		String sRet = "";
		String sVisibilite = "";
		String ligne = "------------------------------------------------";

		sRet += ligne + "\n";

		sRet += String.format("%24s", this.getNomClasse()) + "\n";

		sRet += ligne + "\n";

		for (Attribut attribut : this.getListeAttributs() )
		{

			if (attribut.getVisibilite().equals("privée"))
			{

				sVisibilite = "- ";
			}
			else
			{
				sVisibilite = "+ ";
			}

			sRet += sVisibilite + attribut.getNom() + "\t" + ": " + attribut.getType() + "\n";
		}
		
		return sRet;
	}
}