import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

/**
 * 
 */

/**
 * @author Administrator
 *
 */
public class PointSET {

	private SET<Point2D> points;
	
	public PointSET() 
	{
		// TODO Auto-generated constructor stub
		points = new SET<>();
	}
	
	public boolean isEmpty() 
	{
		return points.isEmpty();
	}
	
	public int size()
	{
		return points.size();
	}
	
	public void insert(Point2D p) 
	{
		points.add(p);
	}
	
	public boolean contains(Point2D p) 
	{
		return points.contains(p);
	}
	
	public void draw() 
	{
		RectHV rect = new RectHV(0, 0, 2, 2);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(.01);
		rect.draw();
		for (Point2D p:points)
		{
			p.draw();
		}
	}
	
	public Iterable<Point2D> range(RectHV rect) 
	{
		SET<Point2D> rangePoints = new SET<>();
		for (Point2D p:points)
			if ((p.x() >= rect.xmin()) && (p.x() <= rect.xmax()) && 
					(p.y() >= rect.ymin()) && (p.y() <= rect.ymax()))
				rangePoints.add(p);
		return rangePoints;
	}
	
	public Point2D nearest(Point2D p) 
	{
		double distance = Double.POSITIVE_INFINITY;
		Point2D nearestPoint = null;
		for (Point2D point:points)
			if (point.distanceSquaredTo(p) < distance)
			{
				distance = point.distanceSquaredTo(p);
				nearestPoint = point;
			}
		return nearestPoint;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
