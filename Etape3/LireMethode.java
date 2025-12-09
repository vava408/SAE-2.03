import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class LireMethode 
{
	LireFichier                   lireFichier;

	private static final String[] TAB_VISIBILITE = { "public", "private", "protected" }; // this.lireFichier.TAB_VISIBILITE
	
	private ArrayList<Methode>    listeMethodes  = new ArrayList<Methode>();
	
	public LireMethode ( LireFichier lireFichier )
	{
		this.lireFichier = lireFichier;
	}

	private void lireMethode( String[] mots) // ex ["private", "void", "lireMethodes", "String[]", "mots"]
	{
		int    nbParametre = 0;
		String visibilite;
		
		String nom;
		String typeParametre;
		String nomParametre;
		String typeRetour;

        boolean isStatic = false;
        boolean isFinal = false;

		ArrayList<Parametre> tabParametre = new ArrayList<Parametre>();


		visibilite = mots[0];

        // 1. Analyse des mots
        for (int i = 0; i < mots.length; i++)
        {
			String m = mots[i];
			
            // static ?
            if (m.equals("static")) 
            {
                isStatic = true;
                continue;
            }

            // final ?
            if (m.equals("final")) 
            {
                isFinal = true;
                continue;
            }
        }







		int cpt = 0;		
		while ( cpt < mots.length )
		{
			String m = mots[ cpt ];

			for( String mod : this.lireFichier.TAB_MODIFIEURS )
			{
				if (mod.equals(m))
				{
					cpt++; 
					continue;
				}
			}

			nom = mots[cpt];


		
			typeParametre = mots[ cpt ];
    		nomParametre  = mots[ cpt + 1 ];

			nbParametre++;
			tabParametre.add(new Parametre( nbParametre, nomParametre, typeParametre));
		
		}
		Collections.reverse(tabParametre);
		
		///
		for (String m : mots) 
        {
            // visibilité
            if (Arrays.asList(TAB_VISIBILITE).contains(m)) 
            {
                visibilite = m;
            }

			// type
            if (this.lireFichier.getNomClasse().equals(m)) 
            {
                typeRetour = null;
            }

			//parametre







		}
	
		for (String m : this.lireFichier.TAB_MODIFIEURS) 
        {
			
		}


		if(typeRetour.equals("class"))
				return;


		if(ligneSplit.length > 3)
		for(int i = 3; i+1 < ligneSplit.length; i=i+2)
		{
			typeParametre = ligneSplit[i];
			nomParametre  = ligneSplit[i+1];
			nbParametre++;

			tabParametre.add(new Parametre( nbParametre, nomParametre, typeParametre));
		}


		Methode methode = new Methode(nom, visibilitee, typeRetour, tabParametre);

		this.listeMethodes.add(methode);
	}

	public ArrayList<Methode> getListeMethodes() 
	{
		return this.listeMethodes;
	}

	
}
