import java.util.Scanner;
import java.util.ArrayList;

public class DecomposerLigne
{
    //méthode pour retirer une ligne de tout ce qui n'est pas utile pour la rendre utilisable
    //puis de la décomposer à l'aide d'un scanner pour rentrer chaque valeurs dans un tableau
    public String[] decomposerLigne(String ligne)
    {
        //création d'une liste pour stocker chaque mots et un string temporaire
        ArrayList<String> lstMots = new ArrayList<String>();

        String s;


        //on retire les paranthèses
        ligne = ligne.replace("(", " ");
        ligne = ligne.replace(")", " ");

        //on retire les accolades
        ligne = ligne.replace("{", " ");
        ligne = ligne.replace("}", " ");

        //on retire les virgules
        ligne = ligne.replace(",", " ");

        //on retire les égaux
        ligne = ligne.replace("=", " ");


        //on supprime tout les espaces en doubles pour faciliter le traitement
        ligne = ligne.replaceAll("\s+", " ").trim();



        //création d'un scanner pour traiter la ligne
        Scanner sc = new Scanner(ligne).useDelimiter("\\s");
        {
            //parcours chaque mots délimités par un espace
            while(sc.hasNext())
            {
                s = sc.next();

                //on supprime les espaces en trop
                s.trim();

                //on supprime la ligne si c'est un commentaire
                if(s.startsWith("//"))
                    s = null;

                //on supprime la ligne si c'est une chaine de caractère
                if(s.startsWith("\""))
                    s = null;

                //quitte la boucle si on rencontre throws qui est toujours à la fin d'une méthode
                if(s.startsWith("throws"))
                    break;


                //on rajoute le mot de la string dans la liste si elle ne fait pas partie des exceptions
                if(s != null)
                    lstMots.add(s);
            }
        }
            sc.close();


            //création d'un tableau de la taille de la liste
            //le tableau est nécessaire pour les prochains traitements
            String[] tabRet = new String[lstMots.size()];

            //transfert chaque mot de la liste dans le tableau
            for(int i = 0; i < lstMots.size(); i++)
                tabRet[i] = lstMots.get(i);

            return tabRet;
    }
}
    //enlever les commentaires
    //enlever les exceptions
    //enlever les "strings"
    //vérifier si on me donne les imports