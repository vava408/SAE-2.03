package src.membres;

/*-------------------------------------------------------------------*/
/*- Classe Parametre : Représente un paramètre d'une méthode UML     */
/*- Etape 1                                                          */
/*- Groupe 6                                                         */
/*- Date de création : 08/12/2025 10:30                              */
/*-------------------------------------------------------------------*/

public class Parametre 
{
    /*--------------------------------------------------------------*/
    /* Déclaration des attributs                                    */
    /*--------------------------------------------------------------*/
    private int    id;
    private String nom;
    private String type;

    /*--------------------------------------------------------------*/
    /* Constructeur : initialise un paramètre                       */
    /*--------------------------------------------------------------*/
    public Parametre(int numParametre, String nom, String type) 
    {
        this.id   = numParametre;
        this.nom  = nom;
        this.type = type;
    }

    /*--------------------------------------------------------------*/
    /* Accesseurs : retourne les attributs de la classe            */
    /*--------------------------------------------------------------*/
    public int    getId  () { return this.id   ;}
    public String getNom () { return this.nom  ;}
    public String getType() { return this.type ;}

    /*--------------------------------------------------------------*/
    /* Modificateurs : modifie les attributs de la classe          */
    /*--------------------------------------------------------------*/
    public void setId  (int id)      { this.id   = id   ;}
    public void setNom (String nom)  { this.nom  = nom  ;}
    public void setType(String type) { this.type = type ;}

    /*--------------------------------------------------------------*/
    /* Retourne une chaîne décrivant le paramètre                  */
    /*--------------------------------------------------------------*/
    public String toString() 
    {
        String sRet = "";
        sRet += "p" + this.getId() + " :" + this.getNom();
        sRet += " type : " + this.getType();
        return sRet;
    }
}
