import java.util.Scanner;
import java.io.FileInputStream;

public class LectureFichier
{
	public static void main(String[] arg)
	{
		try
		{
			Scanner sc = new Scanner ( new FileInputStream ( arg[0] ) );

			while ( sc.hasNextLine() )
			{
				System.out.println ( sc.nextLine() );
			}

			sc.close();
		}
		catch (Exception e){ e.printStackTrace(); }
	}
}