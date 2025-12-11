package src.membres;

/*-------------------------------------------------------------------*/
/*- Classe Attribut : Représente un attribut d'une classe UML       */
/*- Etape 1                                                           */
/*- Groupe 6                                                          */
/*- Date de création : 08/12/2025 10:30                               */
/*-------------------------------------------------------------------*/

public class Attribut 
{
    /*--------------------------------------------------------------*/
    /* Déclaration des attributs                                    */
    /*--------------------------------------------------------------*/
    private int     attributId;
    private String  nom;
    private String  type;
    private String  visibilite;
    private boolean estStatic;
    private boolean estFinal;

    /*--------------------------------------------------------------*/
    /* Constructeur : initialise un attribut UML                   */
    /*--------------------------------------------------------------*/
    public Attribut(int attributId, String nom, String type, String visibilite, boolean estStatic, boolean estFinal) 
    {
        this.attributId = attributId;
        this.nom        = nom;
        this.type       = type;
        this.visibilite = visibilite;
        this.estStatic  = estStatic;
        this.estFinal   = estFinal;
    }

    /*--------------------------------------------------------------*/
    /* Accesseurs : retourne les attributs de la classe            */
    /*--------------------------------------------------------------*/
    public int     getAttributId()    { return this.attributId ;}
    public String  getNom()           { return this.nom        ;}
    public String  getType()          { return this.type       ;}
    public String  getVisibilite()    { return this.visibilite ;}
    public boolean isStatic()         { return this.estStatic  ;}
    public boolean isFinal()          { return this.estFinal   ;}

    /*--------------------------------------------------------------*/
    /* Retourne une chaîne décrivant l'objet Attribut              */
    /*--------------------------------------------------------------*/
    public String toString() 
    {
        String sRet = "";

        sRet += "Attribut ID   : " + this.attributId + "\n";
        sRet += "Nom           : " + this.nom        + "\n";
        sRet += "Type          : " + this.type       + "\n";
        sRet += "Visibilité    : " + this.visibilite + "\n";
        sRet += "Est static    : " + this.estStatic  + "\n";
        sRet += "Est final     : " + this.estFinal   + "\n";

        return sRet;
    }
}