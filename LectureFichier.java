import java.io.FileInputStream;
import java.util.*;

public class LectureFichier
{

	private static final String[] TAB_VISIBILITE = { "public", "private", "protected" };
	private static final String[] TAB_VISIBILITE_FR = { "public", "privée", "protégée" };

	private static int nbAttributs = 0;

	private ArrayList<Attribut>   listeAttributs = new ArrayList<Attribut>();
	private ArrayList<Methode>    listeMethodes  = new ArrayList<Methode>();

	private static void lireFichier( String fileName )
	{
		try
		{
			Scanner sc = new Scanner ( new FileInputStream ( fileName ), "UTF8" );
	
			while ( sc.hasNextLine() )
			{
				String ligne = sc.nextLine();
				if ( ! ligne.startsWith( "import" ) )
				{
					if ( ligne.endsWith( ";" ) )
					{
						LectureFichier.lireAttribut( ligne );
					}
					else
					{
						LectureFichier.lireMethode( ligne );
					}
				}
			}
	
			sc.close();
		}
		catch (Exception e){ e.printStackTrace(); }
	}

	private static void lireAttribut(String ligne)
	{
		String nom;
		String type;
		String visibilitee;
		String portee = "classe";

		ligne = ligne.replace(";", "").trim();
		String[] mots = ligne.split(" ");

		for (int cpt = 0; cpt < LectureFichier.TAB_VISIBILITE.length; cpt++)
		{
			if (mots[0].equals(TAB_VISIBILITE[cpt]))
			{
				visibilitee = TAB_VISIBILITE_FR[cpt];
			}
		}

		if (!mots[1].equals("static"))
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

		new Attribut(++LectureFichier.nbAttributs, nom, type, visibilitee, portee);
	}

	private static void lireMethode( String ligne )
	{
		String visibilite;
		String nom;
		ArrayList<String> parametre = new ArrayList<String>();
		String typeRetour;

		ligne.replace("(", " ");
		ligne.replace(")", " ");

		String[] ligneSplit = ligne.split(" ");

		if(ligne.contains("class"))
		{
			System.out.println("classe : " + ligneSplit[2] + " visibilité : " + ligneSplit[1]);
			return;
		}

		visibilite = ligneSplit[0];
		typeRetour = ligneSplit[1];
		nom = ligneSplit[2];

		for(int i = 2; i < ligneSplit.length; i++)
		{
			parametre.add(ligneSplit[i+1]);
		}

		Methode methode = new Methode(nom, visibilite, typeRetour, null);
		listeMethodes.add(methode);
	}



	public String toString()
	{
		String result = "";
		for (Methode methode : listeMethodes)
		{
			result = methode.toString();
		}
		return result;
	}


	public static void main( String[] arg )
	{
		LectureFichier.lireFichier( arg[0] );
	}
}