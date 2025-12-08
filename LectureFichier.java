import java.io.FileInputStream;
import java.util.*;

public class LectureFichier
{
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

	private static void lireAttribut( String ligne )
	{
		
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
	}

	public static void main( String[] arg )
	{
		LectureFichier.lireFichier( arg[0] );
	}
}