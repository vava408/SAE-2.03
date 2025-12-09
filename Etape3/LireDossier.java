import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class LireDossier
{
	private HashSet<LireFichier>					         hSClasses;
	private HashMap<LireFichier, ArrayList<Association>>  hMAssociations;

	public LireDossier( String cheminDossier )
	{
		hSClasses	   = new HashSet<LireFichier>();
		hMAssociations = new HashMap<LireFichier, ArrayList<Association>>();

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
				LireFichier lireFichier = new LireFichier( this, fichier.getAbsolutePath() ); 

				this.hSClasses.add( lireFichier );

				if( ! this.hMAssociations.containsKey( lireFichier ) )
				{
					this.hMAssociations.put( lireFichier, new ArrayList<Association>() );
				}
			}
		}
	}

	public void ajoutAssociation( LireFichier lireFichier, String nomAutreClasse, String multipliciteSource )
	{
		this.hMAssociations.get( lireFichier ).add( new Association ( nomAutreClasse, multipliciteSource) );
	}

	public boolean nomEstDansRepertoire( String nomClasse )
	{
		for ( LireFichier lF : this.hSClasses )
		{
			if ( lF.getNomClasse().equals( nomClasse ) )
			{
				return true;
			}
		}

		return false;
	}

	public void afficherClasses()
	{
		for( LireFichier lF : this.hMAssociations.keySet() )
		{
			System.out.println( lF.toString() );
		}
	}

	public static void main(String[] args) 
	{
		LireDossier lectureDossier = new LireDossier( args[0] );

		lectureDossier.afficherClasses();
	}
}
