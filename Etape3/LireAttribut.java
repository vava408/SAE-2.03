import java.util.ArrayList;
import java.util.Arrays;

public class LireAttribut 
{

    private static final String[] TAB_VISIBILITE = {"public", "private", "protected"};

    private ArrayList<Attribut> listeAttributs;
    private int compteurId = 0;
    
    public LireAttribut() 
    {
        this.listeAttributs = new ArrayList<>();
    }
    
    public ArrayList<Attribut> getListeAttributs() 
    {
        return this.listeAttributs;
    }

    public void lireAttribut(String[] mots) 
    {

        String visibilite = "default";
        String type = "";
        String nom = "";
        boolean isStatic = false;
        boolean isFinal = false;

        //Retrait du ;
        for ( int cpt = 0; cpt < mots.length; cpt++ )
            {
                mots[ cpt ] = mots[ cpt ].replace( ";", "" );
            }
            
        // 1. Analyse des mots
        for (String m : mots) 
        {


            // visibilité
            if (Arrays.asList(TAB_VISIBILITE).contains(m)) 
            {
                visibilite = m;
                continue;
            }

            // static ?
            if (m.equals("static")) 
            {
                isStatic = true;
                continue;
            }

            // final ?
            if (m.equals("final")) 
            {
                isFinal = true;
                continue;
            }
        }

        // 2. Récupérer type et nom
        if (mots.length >= 2) 
        {
            nom = mots[mots.length - 1];
            type = mots[mots.length - 2];
        } 
        else 
        {
            System.out.println("Impossible de lire type/nom dans : " + Arrays.toString(mots));
            return;
        }

        // 3. Créer l'objet attribut
        Attribut a = new Attribut(compteurId++, nom, type, visibilite, isStatic, isFinal);

        // 4. Ajouter à la liste
        this.listeAttributs.add(a);
    }

}
