package src.membres;

/**
 * Représente un attribut (champ) d'une classe UML.
 *
 * Encapsule les propriétés d'un attribut : identifiant, nom, type,
 * visibilité et modificateurs (static, final) pour la restitution
 * d'une représentation UML texuelle.
 *
 * @author Groupe 6
 * @version Etape 4 - 08/12/2025
 */
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
    /**
     * Construit un attribut avec toutes ses propriétés UML.
     *
     * @param attributId identifiant unique de l'attribut
     * @param nom nom de l'attribut
     * @param type type de l'attribut (ex: `int`, `String`, etc.)
     * @param visibilite visibilité (`public`, `private`, `protected`, `default`)
     * @param estStatic `true` si l'attribut est statique
     * @param estFinal `true` si l'attribut est final
     */
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
    /**
     * Retourne l'identifiant unique de cet attribut.
     * @return l'ID de l'attribut
     */
    public int     getAttributId()    { return this.attributId ;}
    /**
     * Retourne le nom de cet attribut.
     * @return le nom
     */
    public String  getNom()           { return this.nom        ;}
    /**
     * Retourne le type de cet attribut.
     * @return le type (ex: `int`, `String`)
     */
    public String  getType()          { return this.type       ;}
    /**
     * Retourne la visibilité de cet attribut.
     * @return la visibilité (`public`, `private`, `protected`, `default`)
     */
    public String  getVisibilite()    { return this.visibilite ;}
    /**
     * Indique si cet attribut est statique.
     * @return `true` si statique, `false` sinon
     */
    public boolean isStatic()         { return this.estStatic  ;}
    /**
     * Indique si cet attribut est final.
     * @return `true` si final, `false` sinon
     */
    public boolean isFinal()          { return this.estFinal   ;}

    /*--------------------------------------------------------------*/
    /* Retourne une chaîne décrivant l'objet Attribut              */
    /*--------------------------------------------------------------*/
    /**
     * Retourne la représentation texuelle de cet attribut.
     *
     * @return chaîne formatée avec tous les détails de l'attribut
     */
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