package src.metier;

import java.io.File;
import java.util.ArrayList;
import src.membres.Association;
import src.membres.Attribut;

/*-------------------------------------------------------------------*/
/*- Classe LireDossier : Lit un dossier de fichiers Java et crée les */
/*- associations entre les classes détectées                          */
/*- Etape 4                                                           */
/*- Groupe 6                                                          */
/*- Date de création : 10/12/2025 15:30                               */
/*-------------------------------------------------------------------*/

public class LireDossier
{
	/*--------------------------------------------------------------*/
	/* Déclaration des attributs                                    */
	/*--------------------------------------------------------------*/
	private ArrayList<Association> lstAssociations;
	private ArrayList<LireFichier> lstLireFichiers;

	/*--------------------------------------------------------------*/
	/* Constructeur : lit un dossier et crée les associations      */
	/*--------------------------------------------------------------*/
	public LireDossier(String cheminDossier)
	{
		this.lstAssociations = new ArrayList<>();
		this.lstLireFichiers = new ArrayList<>();

		this.lireDossier(cheminDossier);
		this.creerAssociation();
	}

	/*--------------------------------------------------------------*/
	/* Lit tous les fichiers Java dans le dossier                  */
	/*--------------------------------------------------------------*/
	private void lireDossier(String cheminDossier)
	{
		File   dossier       = new File(cheminDossier);
		File[] listeFichiers = dossier.listFiles();

		for (File fichier : listeFichiers)
		{
			if (fichier.isFile() && fichier.getName().endsWith(".java"))
			{
				LireFichier lireFichier = new LireFichier(this, fichier.getAbsolutePath());

				if (!this.lstLireFichiers.contains(lireFichier))
				{
					this.lstLireFichiers.add(lireFichier);
				}
			}
		}
	}

	/*--------------------------------------------------------------*/
	/* Crée les associations entre classes à partir des attributs  */
	/*--------------------------------------------------------------*/
	private void creerAssociation()
	{
		for (LireFichier lF1 : this.lstLireFichiers)
		{
			for (int cpt1 = 0; cpt1 < lF1.getListeAttributs().size(); cpt1++)
			{
				Attribut a1 = lF1.getListeAttributs().get(cpt1);

				for (LireFichier lF2 : this.lstLireFichiers)
				{
					if (a1.getType().contains(lF2.getNomClasse()))
					{
						String multipliciteA    = this.calculMultiplicite(a1.getType(), lF1.getNomClasse());
						String multipliciteB    = "0..*";
						boolean unidirectionnel = true;

						for (int cpt2 = 0; cpt2 < lF2.getListeAttributs().size(); cpt2++)
						{
							Attribut a2 = lF2.getListeAttributs().get(cpt2);

							if (a2.getType().contains(lF1.getNomClasse()))
							{
								unidirectionnel = false;
								multipliciteB   = this.calculMultiplicite(a2.getType(), lF2.getNomClasse());

								this.ajoutAssociation(lF1, a1.getType(), multipliciteA, multipliciteB);

								lF2.getListeAttributs().remove(cpt2);
								cpt2--;
							}
						}

						if (unidirectionnel)
						{
							this.ajoutAssociation(lF1, a1.getType(), multipliciteA, multipliciteB);
						}

						lF1.getListeAttributs().remove(cpt1);
						cpt1--;
					}
				}
			}
		}
	}

	/*--------------------------------------------------------------*/
	/* Crée et ajoute une association à la liste                   */
	/*--------------------------------------------------------------*/
	public void ajoutAssociation(LireFichier lF, String nomClasseB,
								 String multipliciteA, String multipliciteB)
	{
		Association a = new Association(lF.getNomClasse(), nomClasseB, multipliciteB, multipliciteA);
		this.lstAssociations.add(a);
	}

	/*--------------------------------------------------------------*/
	/* Calcule la multiplicité d'un attribut                       */
	/*--------------------------------------------------------------*/
	private String calculMultiplicite(String type, String nomClasse)
	{
		String sRet = "1..1";

		if ((type.contains("<") && type.contains(">")) || type.contains("[]"))
		{
			sRet = "0..*";
		}

		return sRet;
	}

	/*--------------------------------------------------------------*/
	/* Vérifie si une classe existe dans le répertoire             */
	/*--------------------------------------------------------------*/
	public boolean nomEstDansRepertoire(String nomClasse)
	{
		for (LireFichier lF : this.lstLireFichiers)
		{
			if (lF.getNomClasse().equals(nomClasse))
			{
				return true;
			}
		}
		return false;
	}

	/*--------------------------------------------------------------*/
	/* Affiche toutes les classes et associations                  */
	/*--------------------------------------------------------------*/
	public void afficherClasses()
	{
		for (LireFichier lF : this.lstLireFichiers)
		{
			System.out.println(lF.toString());
		}


		for (Association a : this.lstAssociations)
		{
			System.out.println(a);
		}

		for (LireFichier lF : this.lstLireFichiers)
		{
			System.out.println(lF.afficherHeritage());
			System.out.print(lF.afficherInterface());
		}

	}

	/*--------------------------------------------------------------*/
	/* Méthode main pour tester la lecture d'un dossier            */
	/*--------------------------------------------------------------*/
	public static void main(String[] args)
	{
		LireDossier lectureDossier = new LireDossier(args[0]);
		lectureDossier.afficherClasses();
	}
}