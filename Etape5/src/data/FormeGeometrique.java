public abstract class FormeGeometrique implements IForme
{
	protected Point position;

	public FormeGeometrique( Point position )
	{
		this.position = position;
	}

	public Point getPosition()
	{
		return position;
	}

	public void deplacer( int dx, int dy )
	{
		position.deplacer( dx, dy );
	}
}
