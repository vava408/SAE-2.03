public class NettoyerLigne
{
    //méthode pour retirer une ligne de tout ce qui n'est pas utile pour la rendre utilisable
    public String nettoyerLigne(String ligne)
    {
        //on retire les paranthèses
        ligne = ligne.replace("(", " ");
        ligne = ligne.replace(")", " ");

        //on retire les accolades
        ligne = ligne.replace("{", " ");
        ligne = ligne.replace("}", " ");

        //on retire les virgules
        ligne = ligne.replace(",", " ");


        //on supprime tout les espaces en doubles pour faciliter le split
        ligne = ligne.replaceAll("\s+", " ").trim();

        return ligne;
    }
}