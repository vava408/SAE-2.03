import java.util.ArrayList;

public class Test 
{
	private Point d;
	private ArrayList<Disque> lstPoints;
	private Test2 t2;
	
	public Test( ArrayList<Disque> lstPoints, Point d, Test2 t2 )
	{
		this.lstPoints = lstPoints;
		this.d = d;
		this.t2 = t2;
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
