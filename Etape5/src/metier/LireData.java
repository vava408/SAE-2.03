package src.metier;

import java.io.FileInputStream;
import java.io.Serializable;
import java.util.*;

import src.membres.Attribut;
import src.membres.Methode;
import src.membres.Parametre;
import src.membres.Association;

import src.Controleur;

public class LireData implements Serializable
{
	public final String[]             TAB_VISIBILITE = { "public", "private", "protected"                   };
	public final String[]             TAB_MOTCLE     = { "class", "interface", "enum", "record", "abstract" };
	public final String[]             TAB_MODIFIEURS = { "static", "final", "abstract", 
	                                                     "native", "strictfp", "synchronized" };

	private transient DecomposerLigne decomposerLigne;

	private Controleur ctrl;

	public LireData(String chemin, Controleur ctrl)
	{
		this.decomposerLigne = new DecomposerLigne();
		this.ctrl = ctrl;

		lireFichierData(chemin);
	}

	// constructeur  prend en paramètre la classe LireDossier et le nom du fichier à lire
	public void lireFichierData(String chemin)
	{
		Scanner  sc;
		String   ligne;
		String[] tabMots;

		String nomClasse  = "";
		String typeClasse = "";

		int posX = 0;
		int posY = 0;

		int     nbAttribut;
		String  nomAttribut;
		String  typeAttribut;
		String  visibiliteAttribut;

		boolean estStatic = false;
		boolean estFinal  = false;


		String nomMethode;
		String VisibiliteMethode;
		String typeRetour;

		String typeParam;
		String nomParam;

		int nbParam;


		boolean estpremiere = false;


		ArrayList<Attribut>    lstAttribut    = new ArrayList<Attribut   >();
		ArrayList<Methode>     lstMethode     = new ArrayList<Methode    >();
		ArrayList<Parametre>   lstParametre   = new ArrayList<Parametre  >();
		ArrayList<Association> lstAssociation = new ArrayList<Association>();

		ArrayList<LireFichier> lstLireFichiers = new ArrayList<LireFichier>();

		int     cptTemporaire;
		boolean ligneEstTraite = false;

		try
		{
			sc = new Scanner ( new FileInputStream ( chemin ), "UTF8" );


			//boucle pour parcourir tout le fichier de sauvegarde
			while ( sc.hasNextLine() )
			{
				ligne = sc.nextLine();
				ligne = ligne.trim();

				//on récupère les données de la classe
				if(    !ligne.startsWith("-") && !ligne.startsWith("+") && !ligne.isBlank()
					&& !ligne.contains("Association") && !ligne.contains("=") && !ligne.contains("étend")
				    && !ligne.contains("implémente"))
				{
					tabMots = this.decomposerLigne.decomposerLigne( ligne );
					for(String s : TAB_MOTCLE)
					{
						if(s.equals(tabMots[0]))
							typeClasse = s;
					}

					nomClasse = tabMots[1];

					posX = Integer.parseInt(tabMots[2]);
					posY = Integer.parseInt(tabMots[3]);


					//on supprime le contenu des listes pour les prochaines classes
					lstMethode   = new ArrayList<Methode    >();
					lstAttribut    = new ArrayList<Attribut   >();

					lstMethode .clear();
					lstAttribut.clear();

					ligneEstTraite = false;

					continue;
				}


				//on traite les attributs
				if(ligne.startsWith("+"))
				{
					nbAttribut = 0;

					ligneEstTraite = true;

					while(sc.hasNextLine())
					{
						estStatic = false;
						estFinal  = false;

						ligne = sc.nextLine();

						if(ligne.isBlank())
							continue;

						if(ligne.startsWith("-") || ligne.startsWith("="))
						{
							estpremiere = true;
							break;
						}

						tabMots = this.decomposerLigne.decomposerLigne( ligne );


						if(tabMots.length == 1)
						{
							nomAttribut = tabMots[0];
							typeAttribut = null;
							visibiliteAttribut = "protected";

							continue;
						}


						visibiliteAttribut = tabMots[0];

						cptTemporaire = 1;

						//on regarde si l'attribut est static ou final
						for(int i = 1; i < tabMots.length; i++)
						{
							if(tabMots[i].equals("final"))
							{
								estFinal = true;
								cptTemporaire++;
							}

							if(tabMots[i].equals("static"))
							{
								estStatic = true;
								cptTemporaire++;
							}
						}

						typeAttribut = tabMots[cptTemporaire  ];
						nomAttribut  = tabMots[cptTemporaire+1];

						nbAttribut++;

						lstAttribut.add(new Attribut(nbAttribut, nomAttribut, typeAttribut, visibiliteAttribut, estStatic, estFinal, false));
					}
				}


				//on traite les méthodes
				if(ligne.startsWith("-"))
				{
					if(!ligneEstTraite)
						estpremiere = true;

					while(sc.hasNextLine())
					{
						if( !estpremiere )
						{
							ligne = sc.nextLine();
						}
						else
						{
							estpremiere = false;
						}

						estStatic = false;
						estFinal  = false;

						if(ligne.isBlank())
							continue;

						if(ligne.startsWith("="))
							break;

						nbParam       = 0;
						cptTemporaire = 1;

						tabMots = this.decomposerLigne.decomposerLigne( ligne );

						VisibiliteMethode = tabMots[0];


						//on regarde si c'est un final ou static
						for(int i = 1; i < tabMots.length; i++)
						{
							if(tabMots[i].equals("final"))
							{
								estFinal = true;
								cptTemporaire++;
							}

							if(tabMots[i].equals("static"))
							{
								estStatic = true;
								cptTemporaire++;
							}
						}



						//on regarde si c'est un constructeur
						if(tabMots[1].equals(nomClasse))
						{
							nomMethode = tabMots[1];
							typeRetour = null;

							for(String s : tabMots)
								System.out.print(s);

							System.out.println();

							//on récupère d'éventuels paramètres pour le constructeur
							if(tabMots.length > 2)
							for(int i = 2; i < tabMots.length; i+=2)
							{
								typeParam = tabMots[i  ];
								nomParam  = tabMots[i+1];

								nbParam++;

								lstParametre.add(new Parametre(nbParam, nomParam, typeParam));
							}

							lstMethode.add(new Methode(nomMethode, VisibiliteMethode, typeRetour, lstParametre, estStatic, estFinal));

							lstParametre = new ArrayList<Parametre>();
							lstParametre.clear();

							continue;
						}

						typeRetour = tabMots[1];
						nomMethode = tabMots[2];

						cptTemporaire--;


						//on créé les paramètres de la méthode
						if(tabMots.length > 2)
						for(int i = 1 +cptTemporaire; i < tabMots.length; i+=2)
						{
							typeParam = tabMots[i  ];
							nomParam  = tabMots[i+1];

							nbParam++;

							lstParametre.add(new Parametre(nbParam, nomParam, typeParam));
						}


						//on rajoute la nouvelle méthode dans la liste de méthode
						lstMethode.add(new Methode(nomMethode, VisibiliteMethode, typeRetour, lstParametre, estStatic, estFinal));

						lstParametre = new ArrayList<Parametre>();
						lstParametre.clear();
					}

				}


				//on créé une nouvelle classe à la fin de la lecture de ses attributs
				if(ligne.startsWith("=") && !ligne.contains("ASSOCIATIONS") && !ligne.contains("extends") && !ligne.contains("implements"))
				{
					//on créé les objets nécessaires pour la création d'une classe
					LireAttribut lireAttribut = new LireAttribut(lstAttribut);
					LireMethode  lireMethode  = new LireMethode(lstMethode);

					LireFichier lF = new LireFichier(nomClasse, typeClasse, lireMethode, lireAttribut, posX, posY);
					lstLireFichiers.add(lF);

					lF.setLireHeritImplement(new LireHeritImple(lF));
				}


				//création et traitement des associations
				if(ligne.contains("Association"))
				{
					while(sc.hasNextLine())
					{
						String nomClasseA    = "";
						String nomClasseB    = "";
						String multipliciteA = "";
						String multipliciteB = "";

						int emplacementA = 0;
						int emplacementB = 0;

						ligne = sc.nextLine();

						if(ligne.isBlank())
							continue;

						//on passe au prochain traitement si il y a un égal
						if(ligne.startsWith("="))
						{
							estpremiere = true;
							break;
						}

						tabMots = this.decomposerLigne.decomposerLigne(ligne);
						for(int cpt = 0; cpt < tabMots.length; cpt++)
						{
							for(Methode m : lstMethode)
							{
								if(m.getNom().equals(tabMots[cpt]))
								{
									if(nomClasseA.isBlank())
									{
										nomClasseA   = m.getNom();
										emplacementA = cpt;
									}
									else
									{
										nomClasseB = m.getNom();
										emplacementB = cpt;
									}
								}
							}
						}

						lstAssociation.add(new Association(nomClasseA, nomClasseB, multipliciteA, multipliciteB));
					}
				}

				//création et traitement des extends
				if(ligne.contains("extends"))
				{
					while(sc.hasNextLine())
					{
						LireFichier lF1 = null;
						LireFichier lF2 = null;

						if(!estpremiere)
							ligne = sc.nextLine();
						else
							estpremiere = false;

						if(ligne.isBlank())
							continue;

						//on passe au prochain traitement si il y a un égal
						if(ligne.startsWith("="))
						{
							estpremiere = true;
							break;
						}

						//on décompose la ligne
						tabMots = this.decomposerLigne.decomposerLigne(ligne);

						for(LireFichier lF : lstLireFichiers)
						{
							if(lF.getNomClasse().equals(tabMots[1]))
								lF1 = lF;

							if(lF.getNomClasse().equals(tabMots[3]))
								lF2 = lF;
						}

						lF1.getLireHeritImplement().setHerit(lF1.getNomClasse(), lF2.getNomClasse());
					}
				}


				//création et traitement des impelements
				if(ligne.contains("implements"))
				{
					while(sc.hasNextLine())
					{
						LireFichier lF1 = null;
						LireFichier lF2 = null;

						if(!estpremiere)
							ligne = sc.nextLine();
						else
							estpremiere = false;

						if(ligne.isBlank())
							continue;

						//on passe au prochain traitement si il y a un égal
						if(ligne.startsWith("="))
						{
							estpremiere = true;
							break;
						}

						//on décompose la ligne
						tabMots = this.decomposerLigne.decomposerLigne(ligne);

						for(LireFichier lF : lstLireFichiers)
						{
							if(lF.getNomClasse().equals(tabMots[1]))
								lF1 = lF;

							if(lF.getNomClasse().equals(tabMots[3]))
								lF2 = lF;
						}

						ArrayList<String> lstImplement = new ArrayList<String>();
						lstImplement.add(lF2.getNomClasse());

						lF1.getLireHeritImplement().setImplement(lF1.getNomClasse(), lstImplement);
					}
				}
			}

			LireDossier lireDossier = new LireDossier(lstLireFichiers, lstAssociation, ctrl);

			this.ctrl.setLireDossier(lireDossier);

			//rajouter une liaison entre le LireDossier et les LireFichiers
			for(LireFichier lF : lireDossier.getListeFichiers())
			{
				lF.setLireDossier(lireDossier);
			}

			sc.close();
		}
		catch (Exception e){ e.printStackTrace(); }
    }
}