public class Carre extends Rectangle
{
	private Test test;

	public Carre( Test test, Point position, int cote )
	{
		super( position, cote, cote );

		this.test = test;
	}
}
