package src.metier;

import java.io.File;
import java.util.ArrayList;

import src.Controleur;

import src.membres.Association;
import src.membres.Attribut;

public class LireDossier
{
	private ArrayList<Association> lstAssociations;
	private ArrayList<LireFichier> lstLireFichiers;
	private Controleur             ctrl;

	public LireDossier( Controleur ctrl, String cheminDossier )
	{
		this.ctrl = ctrl;

		this.lstAssociations = new ArrayList<Association>();
		this.lstLireFichiers = new ArrayList<LireFichier>();
		
		this.lireDossier( cheminDossier );
		
		this.creerAssociation();
	}

	private void lireDossier( String cheminDossier )
	{
		File   dossier       = new File ( cheminDossier );
		File[] listeFichiers = dossier.listFiles();
		
		for( File fichier : listeFichiers )
		{
			if ( fichier.isFile() && fichier.getName().endsWith( ".java" ) )
			{
				LireFichier lireFichier = new LireFichier( this, fichier.getAbsolutePath() ); 

				if ( ! this.lstLireFichiers.contains( lireFichier ) )
				{
					this.lstLireFichiers.add( lireFichier );
				}
			}
		}
	}

	public int getNbClasses()
	{
		return this.lstLireFichiers.size();
	}

	public ArrayList<LireFichier> getListeFichiers()
	{
		return this.lstLireFichiers;
	}
	
	private void creerAssociation()
	{
		for ( LireFichier lF1 : this.lstLireFichiers )
		{
			for ( int cpt1 = 0; cpt1 < lF1.getListeAttributs().size(); cpt1++ )
			{
				Attribut a1 = lF1.getListeAttributs().get( cpt1 );

				for ( LireFichier lF2 : this.lstLireFichiers )
				{
					if ( a1.getType().contains( lF2.getNomClasse() ) )
					{
						String  multipliciteA   = this.calculMultiplicite( a1.getType(), lF1.getNomClasse() );
						String  multipliciteB   = "0..*"; //on part du principe que c'est unidirectionnel au début
						boolean unidirectionnel = true;

						for ( int cpt2 = 0; cpt2 < lF2.getListeAttributs().size(); cpt2++ )
						{
							Attribut a2 = lF2.getListeAttributs().get( cpt2 );

							if ( a2.getType().contains( lF1.getNomClasse() ) )
							{
								unidirectionnel = false;

								multipliciteB = this.calculMultiplicite( a2.getType(), lF2.getNomClasse() );
								
								this.ajoutAssociation( lF1 , a1.getType(), multipliciteA, multipliciteB );
								
								lF2.getListeAttributs().remove( cpt2 );
								cpt2--;
							}
						}

						if ( unidirectionnel )
						{
							this.ajoutAssociation( lF1, a1.getType(), multipliciteA, multipliciteB);
						}
						
						lF1.getListeAttributs().remove( cpt1 );
						cpt1--;
					}
				}
			}
		}
	}
	
	public void ajoutAssociation( LireFichier lF, String nomClasseB,
								  String multipliciteA, String multipliciteB )
	{
		Association a = new Association ( lF.getNomClasse(), nomClasseB, multipliciteB, multipliciteA );
		
		this.lstAssociations.add( a );
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
	
	public boolean nomEstDansRepertoire( String nomClasse )
	{
		for ( LireFichier lF : this.lstLireFichiers )
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
		System.out.println( this.ctrl.getVue().afficher() );

		System.out.println();
		
		for ( Association a : this.lstAssociations )
		{
			System.out.println( a );
		}

		for ( LireFichier lF : this.lstLireFichiers )
		{
			System.out.println( this.ctrl.getVue().afficherHeritage ( lF ) );
			System.out.println( this.ctrl.getVue().afficherInterface( lF ) );
		}
	}
}
