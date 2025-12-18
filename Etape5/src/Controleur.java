package src;

import java.util.ArrayList;
import java.util.HashMap;
import src.ihm.FrameUML;
import src.ihm.Vue;
import src.membres.Association;
import src.membres.Attribut;
import src.membres.Methode;
import src.metier.LireDossier;
import src.metier.LireFichier;
import src.metier.LireSauvegarde;
import src.metier.Sauvegarder;

public class Controleur
{
    LireDossier lireDossier;
    FrameUML    frameUML   ;
    Vue         vue        ;
    Sauvegarder sauvegarder;
    LireSauvegarde     charger    ;


    public Controleur()
    {
        this.vue         = new Vue     ( this );
        this.frameUML    = new FrameUML( this );
        this.sauvegarder = new Sauvegarder();
        this.charger     = new LireSauvegarde();

    }

    public void lireDossier( String chemin )
    {
        this.lireDossier = new LireDossier( this, chemin );
    }

    public LireDossier getLireDossier()
    {
        return this.lireDossier;
    }

    public FrameUML getFrameUML()
    {
        return this.frameUML;
    }

    public Vue getVue()
    {
        return this.vue;
    }

    public int getNbClasses()
    {
        return this.lireDossier.getNbClasses();
    }

    public ArrayList<LireFichier> getListeFichiers()
    {
        return this.lireDossier.getListeFichiers();
    }

    public ArrayList<Association> getListeAssociation()
    {
        return this.lireDossier.getListeAssociation();
    }

	public HashMap<Association, ArrayList<String>> gethMAttributsAssociations()
	{
		return this.lireDossier.gethMAttributsAssociations();
	}

    public void setPosition( LireFichier lF, int x, int y )
    {
        this.lireDossier.setPosition( lF, x, y );
    }

    public String afficherAttribut( Attribut a )
    {
        return this.vue.afficherAttribut( a );
    }

    public String afficherMethode( Methode m, boolean complet )
    {
        return this.vue.afficherMethode( m, complet );
    }

    public void sauvegarder()
    {
        this.sauvegarder.sauvegarder( lireDossier );
    }

    public void charger(String path)
    {
        this.charger.charger(this, path);
    }

    public boolean nomEstDansRepertoire( String nomClasse )
    {
        return this.lireDossier.nomEstDansRepertoire( nomClasse );
    }

	public void setLireDossier(LireDossier dossier)
	{
		this.lireDossier = dossier;
	}

    public static void main ( String[] args )
    {
        new Controleur();
    }


}