package src;

import java.util.ArrayList;

import src.ihm.FrameUML;
import src.ihm.Vue;

import src.membres.Association;
import src.membres.Attribut;
import src.membres.Methode;

import src.metier.LireFichier;
import src.metier.Sauvegarder;
import src.metier.LireDossier;

public class Controleur
{
    LireDossier lireDossier;
    FrameUML    frameUML   ;
    Vue         vue        ;
    Sauvegarder sauvegarder;


    public Controleur()
    {
        this.frameUML    = new FrameUML( this );
        this.vue         = new Vue     ( this );
        this.sauvegarder = new Sauvegarder();

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

    public void setPosition( LireFichier lF, int x, int y )
    {
        this.lireDossier.setPosition( lF, x, y );
    }

    public String afficherAttribut( Attribut a )
    {
        return this.vue.afficherAttribut( a );
    }

    public String afficherMethode( Methode m )
    {
        return this.vue.afficherMethode( m );
    }

    public void sauvegarder()
    {
        this.sauvegarder.sauvegarder(lireDossier);
    }

    public static void main ( String[] args )
    {
        new Controleur();
    }


}