public class Disque implements Figure
{
    private Point  centre;
    private double rayon;

    public Disque ( Point centre, double rayon )
    {
        this.centre = centre;
        this.rayon  = rayon;
    }

    public double calculerAire()
    {
        return Math.PI * rayon * rayon;
    }

    public double calculerPerimetre()
    {
        return 2 * Math.PI * rayon;
    }

    public void setX( int x )
    {
        centre.setX( x );
    }

    public void setY( int y )
    {
        centre.setY( y );
    }
	
	private void setTest(int y)
	{
		centre.setY(y);
	}
}
