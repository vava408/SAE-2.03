package src.metier;
import java.io.Serializable;
import java.util.ArrayList;

import src.membres.Methode;
import src.membres.Parametre;

public class LireMethode implements Serializable
{
	LireFichier                   lireFichier;

	private  ArrayList<Methode>    listeMethodes  = new ArrayList<Methode>();
	
	public LireMethode ( LireFichier lireFichier )
	{
		this.lireFichier = lireFichier;
	}

	public ArrayList<Methode> getListeMethodes() 
	{
		return this.listeMethodes;
	}

	public void lireMethode( String[] mots) 
	{
		int    nbParametre = 0;
		
		String visibilite;
		String nom = "";
		String typeParametre;
		String nomParametre;
		String typeRetour = "";

        boolean estStatic = false;
        boolean estFinal = false;
		boolean constructeur = false;

		ArrayList<Parametre> tabParametre = new ArrayList<Parametre>();


		visibilite = mots[0];

		int cpt = 1;	

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
				constructeur = true;
			}

			for( String mot : this.lireFichier.TAB_MOTCLE )
			{
				if(m.equals(mot))
				return;
			}
        }

		if( cpt <= mots.length )
		{

			if (constructeur) 
			{
				typeRetour = null;
				nom = mots[cpt];      // nom = Point
				cpt += 1;             // avancer vers paramètres
			} 
			else 
			{
				typeRetour = mots[cpt];
				nom = mots[cpt + 1];
				cpt += 2;             // avancer vers paramètres
			}
			
			while ( cpt + 1  < mots.length )
			{
				String m = mots[ cpt ];
			
				typeParametre = mots[ cpt ];
				nomParametre  = mots[ cpt + 1 ];

				nbParametre++;
				tabParametre.add(new Parametre( nbParametre, nomParametre, typeParametre));
				cpt += 2;
			}
		}

		Methode methode = new Methode(nom, visibilite, typeRetour, tabParametre, estStatic, estFinal);

		this.listeMethodes.add(methode);
	}
}