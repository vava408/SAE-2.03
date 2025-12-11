import java.util.ArrayList;

public class Test extends Object
{
	private Point d;
	private ArrayList<Disque> lstPoints;
	
	public Test( ArrayList<Disque> lstPoints, Point d )
	{
		this.lstPoints = lstPoints;
		this.d = d;
	}

	public ArrayList<Disque> getLstPoints()
	{
		return this.lstPoints;
	}

	public Point getDisque()
	{
		return this.d;
	}
}
