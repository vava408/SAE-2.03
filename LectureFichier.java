import java.io.FileInputStream;
import java.util.*;

public class LectureFichier
{

	private static final String[] TAB_VISIBILITE = { "public", "private", "protected" };
	private static final String[] TAB_VISIBILITE_FR = { "public", "privée", "protégée" };

	private static int nbAttributs = 0;

	private ArrayList<Attribut>   listeAttributs = new ArrayList<Attribut>();
	private ArrayList<Methode>    listeMethodes  = new ArrayList<Methode>();

	private String                nomClasse;

	private void lireFichier( String fileName )
	{
		try
		{
			Scanner sc = new Scanner ( new FileInputStream ( fileName ), "UTF8" );
	
			while ( sc.hasNextLine() )
			{
				String ligne = sc.nextLine();

				ligne = ligne.replace("(", " ");
				ligne = ligne.replace(")", " ");
				ligne = ligne.replace("{", " ");
				ligne = ligne.replace("}", " ");
				ligne = ligne.replaceAll("\\s+", " ").trim();


				if ( ! ligne.startsWith( "import" ) && !ligne.isBlank() && ligne.contains("private") || ligne.contains("public"))
				{
					if ( ligne.endsWith( ";" ) )
					{
						this.lireAttribut( ligne );
					}
					else
					{
						this.lireMethode( ligne );
					}
				}
			}

			sc.close();
		}
		catch (Exception e){ e.printStackTrace(); }
	}

	private void lireAttribut(String ligne)
	{
		String nom;
		String type;
		String visibilitee = LectureFichier.TAB_VISIBILITE_FR[ 0 ];
		String portee      = "classe";

		ligne = ligne.replace(";", "").trim();
		String[] mots = ligne.split(" ");

		for (int cpt = 0; cpt < LectureFichier.TAB_VISIBILITE.length; cpt++)
		{
			if ( mots[0].equals(TAB_VISIBILITE[cpt] ) )
			{
				visibilitee = TAB_VISIBILITE_FR[cpt];
			}
		}

		if ( ! mots[1].equals( "static" ) )
		{
			portee = "instance";

			type = mots[1];
			nom = mots[2];
		}
		else
		{
			type = mots[2];
			nom = mots[3];
		}

		Attribut attribut = new Attribut(LectureFichier.nbAttributs, nom, type, visibilitee, portee);
		this.listeAttributs.add( attribut );
	}

	private void lireMethode( String ligne )
	{
		String visibilite;
		String nom;
		String typeParametre;
		String nomParametre;
		String typeRetour;

		ArrayList<Parametre> tabParametre = new ArrayList<Parametre>();

		ligne = ligne.replace("(", " ");
		ligne = ligne.replace(")", " ");

		String[] ligneSplit = ligne.split(" ");



		if(ligneSplit.length %2 == 0)
		{
			lireConstructeur(ligneSplit);
			return;
		}


		visibilite = ligneSplit[0];
		typeRetour = ligneSplit[1];
		nom        = ligneSplit[2];


		if(typeRetour.equals("class"))
				return;


		if(ligneSplit.length > 3)
		for(int i = 3; i+1 < ligneSplit.length; i=i+2)
		{
			typeParametre = ligneSplit[i];
			nomParametre  = ligneSplit[i+1];

			tabParametre.add(new Parametre(nomParametre, typeParametre));
		}


		Methode methode = new Methode(nom, visibilite, typeRetour, tabParametre);

		this.listeMethodes.add(methode);
	}


	private void lireConstructeur( String[] ligneSplit )
	{
		String visibilite;
		String nom;
		String typeParametre;
		String nomParametre;

		ArrayList<Parametre> tabParametre = new ArrayList<Parametre>();


		visibilite     = ligneSplit[0];
		this.nomClasse = ligneSplit[1];
		nom            = ligneSplit[1];


		if(ligneSplit.length > 2)
		for(int i = 2; i+1 < ligneSplit.length; i=i+2)
		{
			typeParametre = ligneSplit[i];
			nomParametre  = ligneSplit[i+1];

			tabParametre.add(new Parametre(nomParametre, typeParametre));
		}

		Methode methode = new Methode(nom, visibilite, null, tabParametre);

		this.listeMethodes.add(methode);
	}


	public String toString()
	{
		String sRet        = "";
		String sVisibilite = "";
		String ligne       = "------------------------------------------------";

		sRet += ligne + "\n";

		sRet += String.format( "%24s", this.nomClasse ) + "\n";

		sRet += ligne + "\n";

		for ( Attribut attribut : listeAttributs )
		{
			if ( attribut.getVisibilite() == "privée" )
			{
				sVisibilite = "- ";
			}
			else
			{
				sVisibilite = "+ ";
			}

			sRet += sVisibilite + attribut.getNom() + "\t" + ": " + attribut.getType() + "\n";
		}

		sRet += ligne + "\n";

		for ( Methode methode : listeMethodes )
		{
			if ( methode.getVisibilite() == "privée" )
			{
				sVisibilite = "- ";
			}
			else
			{
				sVisibilite = "+ ";
			}

			sRet += sVisibilite + methode.getNom() + " (";

			if ( methode.getParametre().size() == 0 )
			{
				sRet += ")";
			}

			for ( int cpt = 0; cpt < methode.getParametre().size(); cpt++ )
			{
				Parametre parametre = methode.getParametre().get( cpt );

				sRet += " " + parametre.getNom() + " : " + parametre.getType();

				if ( cpt < methode.getParametre().size() - 1 )
				{
					sRet += ",";
				}
				else
				{
					sRet += " )";
				}

			}

			if ( methode.getRetour() != null && ! methode.getRetour().equals( "void" ) )
			{
				sRet += String.format( "%20s", ": " + methode.getRetour() );
			}

			sRet += "\n";
		}

		sRet += ligne + "\n";

		return sRet;
	}


	public static void main( String[] arg )
	{
		LectureFichier lectureFichier = new LectureFichier();
		lectureFichier.lireFichier( arg[0] );
		System.out.println(lectureFichier.toString());
	}
}