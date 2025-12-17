public record RectangleSimple( int largeur, int hauteur )
{
	public int aire()
	{
		return largeur * hauteur;
	}

	public int perimetre()
	{
		return 2 * ( largeur + hauteur );
	}
}
