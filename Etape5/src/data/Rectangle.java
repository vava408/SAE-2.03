public class Rectangle extends FormeGeometrique
{
	protected int largeur;
	protected int hauteur;

	public Rectangle( Point position, int largeur, int hauteur )
	{
		super( position );
		this.largeur = largeur;
		this.hauteur = hauteur;
	}

	public int getLargeur()
	{
		return largeur;
	}

	public int getHauteur()
	{
		return hauteur;
	}

	@Override
	public double aire()
	{
		return largeur * hauteur;
	}

	@Override
	public double perimetre()
	{
		return 2 * ( largeur + hauteur );
	}
}
