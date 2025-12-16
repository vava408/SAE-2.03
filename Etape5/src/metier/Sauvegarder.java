package src.metier;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Sauvegarder
{
    public void sauvegarder(LireDossier dossier)
    {
        try
        {
            ObjectOutputStream out =
                new ObjectOutputStream(
                    new FileOutputStream("src/sauvegarde/save.ser"));

            out.writeObject(dossier);
            out.close();

            // 🔹 Création du .data
            CreeData data = new CreeData();
            data.creerData(dossier);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}