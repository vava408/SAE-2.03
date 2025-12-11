package src.metier;

import java.util.HashMap;

public class LireHeritImple
{
	private LireFichier lireFichier;
	private DecomposerLigne decomposerLigne;;
	private HashMap<String, String> mapImplements;
	private HashMap<String, String> mapExtends;
	private String[] mot;


	public LireHeritImple(LireFichier lireFichier)
	{
		this.lireFichier = lireFichier;
		this.decomposerLigne = new DecomposerLigne();
		this.mapImplements = new HashMap<>();
		this.mapExtends = new HashMap<>();
	}

	public void lireHeritImple(String ligne)
	{
		String[] mots = this.decomposerLigne.decomposerLigne(ligne);


		String nomClasse = mots[2];
		String motCle = mots[4];

		//vérification s'il y a implements
		if (mots[3].equals("implements"))
		{
			if (this.lireFichier.nomEstDansRepertoire(motCle) )
			{
				this.mapImplements.put(nomClasse, motCle);
			}
		}

		//vérification s'il y a extends
		if (mots[3].equals("extends"))
		{
			//System.out.println(this.lireFichier.getNomClasse());
			this.mapExtends.put(nomClasse, motCle);
		}

		//vérification s'il y a implements après extends
		if (mots.length > 4 && mots[5].equals("implements"))
		{
			motCle = mots[6];
			if (this.lireFichier.nomEstDansRepertoire(motCle) )
			{
				this.mapImplements.put(nomClasse, motCle);
			}
			
		}
	}

	public HashMap<String,String> getMapImplements()
	{
		return this.mapImplements;
	}

	public HashMap<String, String> getMapExtends()
	{
		return this.mapExtends;
	}
}
