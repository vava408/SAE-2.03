import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class LireDossier
{
	private HashSet<String>		                         hSClasses;
	private HashMap<LireFichier, ArrayList<Association>> hMAssociations;

	public LireDossier( String cheminDossier )
	{
		hSClasses	   = new HashSet<String>();
		hMAssociations = new HashMap<LireFichier, ArrayList<Association>>();

		this.lireDossier( cheminDossier );

		this.creerAssociation();
	}

	private void creerAssociation()
	{
		for ( LireFichier lF1 : this.hMAssociations.keySet() )
		{
			for ( Attribut a : lF1.getListeAttributs() )
			{
				for ( LireFichier lF2 : this.hMAssociations.keySet() )
				{
					if ( a.getType().contains( lF2.getNomClasse() ) )
					{
						if ( this.hSClasses.contains( a.getType() ) )
						{
							this.ajoutAssociation( lF1 , a.getType(), 
												   this.calculMultiplicite( a.getType(), lF1.getNomClasse() ) );

							lF1.getListeAttributs().remove( a );
						}
					}
				}
			}
		}
	}

	private String calculMultiplicite( String type, String nomClasse )
	{
		String sRet = "1..1";
		
		if ( type.contains( "<" ) && type.contains( ">" ) || type.contains( "[]" ) )
		{
			sRet = "0..*";
		}

		return sRet;
	}
	
	public void ajoutAssociation( LireFichier lF, String nomAutreClasse, String multipliciteSource )
	{
		Association a = new Association ( nomAutreClasse, multipliciteSource );
		
		this.hMAssociations.get( lF ).add( a );
	}

	private void lireDossier( String cheminDossier )
	{
		File   dossier       = new File( cheminDossier );
		File[] listeFichiers = dossier.listFiles();
		
		for( File fichier : listeFichiers )
		{
			if ( fichier.isFile() && fichier.getName().endsWith( ".java" ) )
			{
				LireFichier lireFichier = new LireFichier( this, fichier.getAbsolutePath() ); 

				this.hSClasses.add( lireFichier.getNomClasse() );

				if( ! this.hMAssociations.containsKey( lireFichier ) )
				{
					this.hMAssociations.put( lireFichier, new ArrayList<Association>() );
				}
			}
		}
	}


	public boolean nomEstDansRepertoire( String nomClasse )
	{
		for ( LireFichier lF : this.hMAssociations.keySet() )
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

			System.out.println( lF. );
		}
	}

	public static void main( String[] args ) 
	{
		LireDossier lectureDossier = new LireDossier( args[0] );

		lectureDossier.afficherClasses();
	}
}
