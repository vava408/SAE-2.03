import java.util.Scanner;
import java.io.FileInputStream;

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
		
	}

	public static void main( String[] arg )
	{
		LectureFichier.lireFichier( arg[0] );
	}
}