import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class LireMethode 
{
	LireFichier                   lireFichier;

	private ArrayList<Methode>    listeMethodes  = new ArrayList<Methode>();
	
	public LireMethode ( LireFichier lireFichier )
	{
		this.lireFichier = lireFichier;
	}

	private void lireMethode( String[] mots) 
	{
		int    nbParametre = 0;
		String visibilite;
		
		String nom;
		String typeParametre;
		String nomParametre;
		String typeRetour;

        boolean estStatic = false;
        boolean estFinal = false;

		ArrayList<Parametre> tabParametre = new ArrayList<Parametre>();


		visibilite = mots[0];

		int cpt = 0;	

        // 1. Analyse des mots
        for (int i = 0; i < mots.length; i++)
        {
			String m = mots[i];
			
            // static ?
            if (m.equals("static")) 
            {
                estStatic = true;
                continue;
            }

            // final ?
            if (m.equals("final")) 
            {
                estFinal = true;
                continue;
            }

			for( String mod : this.lireFichier.TAB_MODIFIEURS )
			{
				if (mod.equals(m))
				{
					cpt++; 
					continue;
				}
			}

			if (this.lireFichier.getNomClasse().equals(m)) 
			{
				typeRetour = null;
			}

			for( String mot : this.lireFichier.TAB_MOTCLE )
			{
				if(m.equals(mot))
				return;
			}
        }

		typeRetour = mots[cpt];
		nom = mots[cpt+1];


		cpt++;
		if( cpt < mots.length )
		{
			while ( cpt < mots.length )
			{
				String m = mots[ cpt ];
			
				typeParametre = mots[ cpt + 1 ];
				nomParametre  = mots[ cpt + 2 ];

				nbParametre++;
				tabParametre.add(new Parametre( nbParametre, nomParametre, typeParametre));
				cpt = cpt + 2;
			}
		}


		Methode methode = new Methode(nom, visibilite, typeRetour, tabParametre, estStatic, estFinal);

		this.listeMethodes.add(methode);
	}

	public ArrayList<Methode> getListeMethodes() 
	{
		return this.listeMethodes;
	}

	
}
