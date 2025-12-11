public abstract class Figure
{
	protected Point centre;

	public Figure(Point centre)
	{
		this.centre = centre;
	}

	public Point getCentre()
	{
		return this.centre;
	}

	public void setX(int x)
	{
		this.centre.setX(x);
	}

	public void setY(int y)
	{
		this.centre.setY(y);
	}
}