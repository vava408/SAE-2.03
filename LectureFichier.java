import java.util.Scanner;
import java.io.FileInputStream;

public class LectureFichier
{

	private static void lectureFichier( String fileName )
	{
		try
		{
			Scanner sc = new Scanner ( new FileInputStream ( fileName ), "UTF8" );
	
			while ( sc.hasNextLine() )
			{
				System.out.println ( sc.nextLine() );
			}
	
			sc.close();
		}
		catch (Exception e){ e.printStackTrace(); }
	}

	public static void main( String[] arg )
	{
		LectureFichier.lectureFichier( arg[0] );
	}
}