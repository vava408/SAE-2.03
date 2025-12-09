import java.io.File;

public class LectureDossier
{
	private static void lireDossier( String cheminDossier )
	{
		File   dossier       = new File( cheminDossier );
		File[] listeFichiers = dossier.listFiles();
		
		for( File fichier : listeFichiers )
		{
			if( fichier.isFile() && fichier.getName().endsWith( ".java" ) )
			{
				new LectureFichier( fichier.getAbsolutePath() );
			}
		}
	}

	public static void main(String[] args) 
	{
		LectureDossier.lireDossier( args[0] );	
	}
}
