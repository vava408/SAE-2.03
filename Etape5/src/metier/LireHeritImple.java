package src.metier;

import java.util.ArrayList;
import java.util.HashMap;

public class LireHeritImple
{
	private LireFichier lireFichier;
	private DecomposerLigne decomposerLigne;
	private ArrayList<String> listeImplements;
	private HashMap<String, ArrayList<String> > mapImplements;
	private HashMap<String, String> mapExtends;
	private String[] mot;


	public LireHeritImple(LireFichier lireFichier)
	{
		this.lireFichier = lireFichier;
		this.decomposerLigne = new DecomposerLigne();
		this.mapImplements = new HashMap<>();
		this.mapExtends = new HashMap<>();
		this.listeImplements = new ArrayList<>();
	}

	public void lireHeritImple(String ligne)
	{
		String[] mots = this.decomposerLigne.decomposerLigne(ligne);


		String nomClasse = mots[2];
		String motCle = mots[4];

		if (mots[3].equals("implements"))
		{
			int index = 0;
			for (String stringMot : mots)
			{
				index++;
				if (this.lireFichier.nomEstDansRepertoire(stringMot) || index > 4)
				{
					this.listeImplements.add(stringMot);
				}
			}
			this.mapImplements.put(nomClasse, this.listeImplements);
		}

		if (mots.length > 6 && mots[5].equals("implements"))
		{
			int index = 0;
			for (String stringMot : mots)
			{
				index++;
				if (this.lireFichier.nomEstDansRepertoire(stringMot) || index > 6)
				{
					this.listeImplements.add(stringMot);
				}
			}
			this.mapImplements.put(nomClasse, this.listeImplements);

		}

		if (mots[3].equals("extends"))
		{
			//System.out.println(this.lireFichier.getNomClasse());
			this.mapExtends.put(nomClasse, motCle);
		}
	}

	public HashMap<String, ArrayList<String> > getMapImplements()
	{
		return this.mapImplements;
	}

	public HashMap<String, String> getMapExtends()
	{
		return this.mapExtends;
	}
}
