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

	private LireDossier               lectureDossier     ;
	private LireHeritImple            lireHeritImplements;
	
	private DecomposerLigne           decomposerLigne;
	private LireMethode               lireMethode    ;
	private LireAttribut              lireAttribut   ;
	
	
	private String                    motCle         ;
	private String                    nomClasse      ;

	// constructeur  prend en paramètre la classe LireDossier et le nom du fichier à lire
	public LireFichier( LireDossier lectureDossier, String fileName) 
	{
		this.lectureDossier  = lectureDossier;

		this.decomposerLigne = new DecomposerLigne();
		this.lireHeritImplements = new LireHeritImple( this);
		this.lireMethode     = new LireMethode    ( this );
		this.lireAttribut    = new LireAttribut   ( this );

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

	public boolean nomEstDansRepertoire (String nomClasse)
	{
		return this.lectureDossier.nomEstDansRepertoire(nomClasse);
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

	public HashMap<String, String> getMapHerit()
	{
		return this.lireHeritImplements.getMapExtends();
	}


	public HashMap<String, ArrayList<String> > getMapImple()
	{
		return this.lireHeritImplements.getMapImplements();
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
				
				if ( ! ligne.startsWith( "import"    )   && !ligne.isBlank() && 
				       ligne.startsWith( "private"   )   ||
				       ligne.startsWith( "public"    )   ||
					   ligne.startsWith( "protected" ) )
				{
					if (ligne.contains("implements") || ligne.contains("extends"))
					{
						this.lireHeritImplements.lireHeritImple(ligne);
					}
					
                    //rajout d'un appel vers la méthode pour traiter les records
                    if(ligne.contains("record"))
                    {
                        this.nomClasse = tabMots[2];
                        this.motCle    = tabMots[1];
                        traiterRecord(tabMots);
                    }

					//rajout d'un appel vers la méthode pour traiter les records
                    if(ligne.contains("record"))
                    {
                        this.nomClasse = tabMots[2];
                        this.motCle    = tabMots[1];
                        traiterRecord(tabMots);
                    }

					if ( this.estLaPremiereLigne( tabMots[1] ) )
					{
						this.motCle = tabMots[ 1 ];

						if   ( this.motCle.equals( "abstract" ) ) { this.nomClasse = tabMots[3]; }
                        else                                      { this.nomClasse = tabMots[2]; }
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
	
    //méthode pour traiter les records
    public void traiterRecord(String[] tabMots)
    {
        //récupération de chaque valeur de la création du record pour créer des attributs
        String[] tabAttributs = new String[tabMots.length-3];

        //on créé tout les nouveaux attributs en final static
        for(int i = 3; i <tabMots.length;i+=2)
        {
            tabAttributs[0] = "static";
            tabAttributs[1] = "final";
            tabAttributs[2] = tabMots[i];
            tabAttributs[3] = tabMots[i+1];

            //on créé un nouveau attribut
            this.lireAttribut.lireAttribut(tabAttributs);


            //pour chaque attributs, on créé un get et un set

            String[] tabTemporaireGet = new String[3];

            //création des getter pour le records
            tabTemporaireGet[0] = "public";
            tabTemporaireGet[1] = tabAttributs[2];                        //type de retour
            tabTemporaireGet[2] = "get" + tabAttributs[3];                //nom du get
            this.lireMethode.lireMethode(tabTemporaireGet);


            String[] tabTemporaireSet = new String[5];

            //création des setter pour le record
            tabTemporaireSet[0] = "public";
            tabTemporaireSet[1] = tabAttributs[2];                        //type de retour
            tabTemporaireSet[2] = "set" + tabAttributs[3];                //nom du set
            tabTemporaireSet[3] = tabAttributs[2];                        //type du paramètre
            tabTemporaireSet[4] = tabAttributs[2].substring(0,3);         //nom  du paramètre
            this.lireMethode.lireMethode(tabTemporaireSet);
        }

        String[] tabTemporaireToString = new String[3];

        //création du toString pour le record
        tabTemporaireToString[0] = "public";
        tabTemporaireToString[1] = "String";
        tabTemporaireToString[2] = "toString";
        this.lireMethode.lireMethode(tabTemporaireToString);


        String[] tabTemporaireConstructeur = new String[tabMots.length];

        //création du constructeur
        tabTemporaireConstructeur[0] = "public";
        tabTemporaireConstructeur[1] = this.nomClasse;

        //on récupère chaque attributs dans le tableau d'attributs
        for(int i = 2; i < tabAttributs.length+2; i++)
            tabTemporaireConstructeur[i] = tabAttributs[i-2];

        this.lireMethode.lireMethode(tabTemporaireConstructeur);
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
}