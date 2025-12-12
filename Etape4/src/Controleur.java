package src;

import src.ihm.Vue;
import src.metier.LireDossier;

public class Controleur
{
	LireDossier lireDossier;
	Vue         vue;

	public Controleur( String chemin )
	{
		this.lireDossier = new LireDossier( this, chemin );
		
		this.vue         = new Vue( this );
	}

	public LireDossier getLireDossier()
	{
		return this.lireDossier;
	}

	public Vue getVue()
	{
		return this.vue;
	}

	public static void main(String[] args)
	{
		Controleur ctrl = new Controleur ( args[0] );
		
		System.out.println( ctrl.getVue().afficher() );
	}
}