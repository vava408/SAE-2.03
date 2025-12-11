package src.metier;

import java.io.FileInputStream;
import java.util.*;
import src.ihm.Vue;
import src.membres.Attribut;
import src.membres.Methode;


/*-------------------------------------------------------------------*/
/*- Classe LireFichier : Lit un fichier Java et extrait les classes, */
/*- méthodes, attributs, héritages et interfaces                     */
/*- Etape 4                                                          */
/*- Groupe 6                                                         */
/*- Date de création : 08/12/2025 9:30                               */
/*-------------------------------------------------------------------*/

public class LireFichier
{
    /*--------------------------------------------------------------*/
    /* Tableaux de référence pour lecture des mots clés            */
    /*--------------------------------------------------------------*/
    public final String[] TAB_VISIBILITE   = { "public"  , "private"  , "protected"                       };
    public final String[] TAB_MOTCLE       = { "class"   , "interface", "enum"     , "record", "abstract" };
    public final String[] TAB_MODIFIEURS   = { "static"  , "final"    , "abstract" , "native", 
	                                           "strictfp", "synchronized" };

    /*--------------------------------------------------------------*/
    /* Attributs de la classe                                      */
    /*--------------------------------------------------------------*/
    private LireDossier     lectureDossier     ;
    private LireHeritImple  lireHeritImplements;
	private LireMethode     lireMethode        ;
    private LireAttribut    lireAttribut       ;

    private DecomposerLigne decomposerLigne;

    private Vue             vue            ;

    private String          motCle         ;
    private String          nomClasse      ;

    /*--------------------------------------------------------------*/
    /* Constructeur : initialise la lecture d'un fichier           */
    /*--------------------------------------------------------------*/
    public LireFichier(LireDossier lectureDossier, String fileName)
    {
        this.lectureDossier      = lectureDossier           ;
        this.decomposerLigne     = new DecomposerLigne()    ;
        this.lireHeritImplements = new LireHeritImple (this);
        this.lireMethode         = new LireMethode    (this);
        this.lireAttribut        = new LireAttribut   (this);
        this.vue                 = new Vue            (this);

        lireFichier(fileName);
    }

    /*--------------------------------------------------------------*/
    /* Retourne le nom de la classe lue                             */
    /*--------------------------------------------------------------*/
    public String getNomClasse()
    {
        return this.nomClasse;
    }

    /*--------------------------------------------------------------*/
    /* Retourne le mot clé de la classe lue (class, interface, etc)*/
    /*--------------------------------------------------------------*/
    public String getMotCle()
    {
        return this.motCle;
    }

    /*--------------------------------------------------------------*/
    /* Vérifie si une classe existe dans le répertoire              */
    /*--------------------------------------------------------------*/
    public boolean nomEstDansRepertoire(String nomClasse)
    {
        return this.lectureDossier.nomEstDansRepertoire(nomClasse);
    }

    /*---------------------------------------------------------------*/
    /*  Accesseur : retourne les attributs de la classe              */
    /*---------------------------------------------------------------*/
    public ArrayList<Attribut>     getListeAttributs() { return this.lireAttribut.getListeAttributs();}

    public ArrayList<Methode>      getListeMethodes () { return this.lireMethode .getListeMethodes ();}

    public HashMap<String, String> getMapHerit      () { return this.lireHeritImplements.getMapExtends   ();}

    public HashMap<String, String> getMapImple      () { return this.lireHeritImplements.getMapImplements();}

    /*--------------------------------------------------------------*/
    /* Lit le fichier Java et analyse son contenu                   */
    /*--------------------------------------------------------------*/
    private void lireFichier(String fileName)
    {
        Scanner  sc;
        String   ligne;
        String[] tabMots;

        try
        {
            sc = new Scanner( new FileInputStream(fileName), "UTF8" );

            while (sc.hasNextLine())
            {
                ligne   = sc.nextLine().trim();
                tabMots = this.decomposerLigne.decomposerLigne(ligne);

                if (!ligne.startsWith("import") && !ligne.isBlank() &&
                    (ligne.startsWith("private") || ligne.startsWith("public") || ligne.startsWith("protected")))
                {
                    if (ligne.contains("implements") || ligne.contains("extends"))
                    {
                        this.lireHeritImplements.lireHeritImple(ligne);
                    }

                    if (this.estLaPremiereLigne(tabMots[1]))
                    {
                        this.motCle    = tabMots[1];
                        this.nomClasse = tabMots[2];
                    }
                    else
                    {
                        if (ligne.endsWith(";"))
                        {
                            this.lireAttribut.lireAttribut(tabMots);
                        }
                        else
                        {
                            this.lireMethode .lireMethode (tabMots);
                        }
                    }
                }
            }

            sc.close();
        }
        catch (Exception e) { e.printStackTrace();}
    }

    /*--------------------------------------------------------------*/
    /* Vérifie si le mot passé est un mot clé de déclaration       */
    /*--------------------------------------------------------------*/
    private boolean estLaPremiereLigne(String mot)
    {
        for (String motCle : TAB_MOTCLE)
        {
            if (mot.equals(motCle))
            {
                return true;
            }
        }
        return false;
    }

    /*--------------------------------------------------------------*/
    /* Affiche l'héritage de la classe                              */
    /*--------------------------------------------------------------*/
    public String afficherHeritage()
    {
        return this.vue.afficherHeritage();
    }

    /*--------------------------------------------------------------*/
    /* Affiche les interfaces implémentées par la classe            */
    /*--------------------------------------------------------------*/
    public String afficherInterface()
    {
        return this.vue.afficherInterface();
    }

    /*--------------------------------------------------------------*/
    /* Affichage textuel complet de la classe                       */
    /*--------------------------------------------------------------*/
    public String toString()
    {
        return this.vue.afficher();
    }
}
