import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class LireDossier
{
	private HashSet<LectureFichier>					         hSClasses;
	private HashMap<LectureFichier, ArrayList<Association>>  hMAssociations;

	public LireDossier( String cheminDossier )
	{
		hSClasses	   = new HashSet<LectureFichier>();
		hMAssociations = new HashMap<LectureFichier, ArrayList<Association>>();

		this.lireDossier( cheminDossier );
	}

	private void lireDossier( String cheminDossier )
	{
		File   dossier       = new File( cheminDossier );
		File[] listeFichiers = dossier.listFiles();
		
		for( File fichier : listeFichiers )
		{
			if( fichier.isFile() && fichier.getName().endsWith( ".java" ) )
			{
				LectureFichier lectureFichier = new LectureFichier( this, fichier.getAbsolutePath() ); 

				this.hSClasses.add( lectureFichier );

				if( ! this.hMAssociations.containsKey( lectureFichier ) )
				{
					this.hMAssociations.put( lectureFichier, new ArrayList<Association>() );
				}
			}
		}
	}

	public void ajoutAssociation( LectureFichier lectureFichier, String nomAutreClasse, String multipliciteSource )
	{
		this.hMAssociations.get( lectureFichier ).add( new Association ( nomAutreClasse, multipliciteSource) );
	}

	public boolean nomEstDansRepertoire( String nomClasse )
	{
		for ( LectureFichier lF : this.hSClasses )
		{
			if ( lF.getClassName().equals( nomClasse ) )
			{
				return true;
			}
		}

		return false;
	}

	public void afficherClasses()
	{
		for( LectureFichier lF : this.hMAssociations.keySet() )
		{
			System.out.println( this.hMAssociations.get( lF ).size() );
		}
	}

	public static void main(String[] args) 
	{
		LireDossier lectureDossier = new LireDossier( args[0] );

		lectureDossier.afficherClasses();
	}
}
