import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

	private int size;
	private Node root;
	private SET<Point2D> range;
	private Point2D nearestPoint;
	private double nearestDistance;
	
	public KdTree() {
		// TODO Auto-generated constructor stub
		this.root = null;
		this.size = 0;
	}
	
	public boolean isEmpty()
	{
		if (this.size == 0)
			return true;
		return false;
	}
	
	public int size()
	{
		return this.size;
	}
	
	public void insert(Point2D p)
	{
		argumentTest(p);
		
		if (this.contains(p))
			return;
		
		/*  Primary Code  */
		double x = p.x();
		double y = p.y();
		
		if (this.root == null)
		{
			this.root = new Node(p, new RectHV(0, 0, 1, 1), true);
			size++;
			return;
		}
		else 
		{
			Node now = root;
			Node next = root;
			while (next != null)
			{
				now = next;
				if (now.XFlag == true)
				{
					if (x < now.p.x()) {
						next = now.lb;						
					}
					else {
						next = now.rt;
					}
				}
				else {
					if (y < now.p.y()) {
						next = now.lb;
					}
					else {
						next = now.rt;
					}						
				}
			}
			
			if (now.XFlag == true)
			{
				if (x < now.p.x()) {
					now.setLb(new Node(p, new RectHV(now.rect.xmin(), now.rect.ymin(), now.p.x(), now.rect.ymax()), !now.XFlag));
				}
				else {
					now.setRt(new Node(p, new RectHV(now.p.x(), now.rect.ymin(), now.rect.xmax(), now.rect.ymax()), !now.XFlag));
				}
			}
			else {
				if (y < now.p.y()) {
					now.setLb(new Node(p, new RectHV(now.rect.xmin(), now.rect.ymin(), now.rect.xmax(), now.p.y()), !now.XFlag));
				}
				else {
					now.setRt(new Node(p, new RectHV(now.rect.xmin(), now.p.y(), now.rect.xmax(), now.rect.ymax()), !now.XFlag));
				}
			}
		}
		
		/*  Primary Code end  */	
		
		/*  Compact Code Start  */
//		root = insert(root, p, true, new RectHV(0, 0, 1, 1));
		size++;
		/*  Compact Code End  */
	}
	
	private Node insert(Node node, Point2D p, boolean XFlag, RectHV rect)
	{
		if (node == null) return new Node(p, rect);
		if (XFlag)
		{
			XFlag = false;
			if (p.x() < node.getP().x())
			{
				node.setLb(insert(node.getLb(), p, XFlag, new RectHV(node.getXMin(), node.getYMin(), node.getP().x(), node.getYMax())));
			}
//			else if (p.x() >= node.getP().x()) 
			else
			{
				node.setRt(insert(node.getRt(), p, XFlag, new RectHV(node.getP().x(), node.getYMin(), node.getXMax(), node.getYMax())));
			}
		}
//		else if (!XFlag) 
		else
		{
			XFlag = true;
			if (p.y() < node.getP().y())
			{
				node.setLb(insert(node.getLb(), p, XFlag, new RectHV(node.getXMin(), node.getYMin(), node.getXMax(), node.getP().y())));
			}
			else if (p.y() >= node.getP().y()) {
				node.setRt(insert(node.getRt(), p, XFlag, new RectHV(node.getXMin(), node.getP().y(), node.getXMax(), node.getYMax())));
			}
		}
		return node;
	}
	
	public boolean contains(Point2D p)
	{
		argumentTest(p);
		if (p.getClass() != Point2D.class) return false;
		
		/*  Primary Code Start  */
		double x = p.x();
		double y = p.y();
		
		if (this.root == null)
			return false;
		
		Node now = root;
		while (now != null)
		{
			if (now.p.equals(p))
			{
				return true;
			}
			if (now.XFlag == true)
			{
				if (x < now.p.x())
				{
					now = now.lb;
				}
				else {
					now = now.rt;
				}
			}
			else {
				if (y < now.p.y())
				{
					now = now.lb;
				}
				else {
					now = now.rt;
				}
			}
		}

		return false;
		
		/*  Primary Code End  */
		
		/*  Compact Code Start  */
//		return get(p) != null;
		/*  Compact Code End  */
		
	}
	
	private Node get(Point2D p)
	{
		return get(root, p, true);
	}
	
	private Node get(Node node, Point2D p, boolean XFlag)
	{
		if (node == null) return null;
		if (XFlag)
		{
			XFlag = false;
			if (p.x() < node.getP().x())
				return get(node.getLb(), p, XFlag);
			else if (p.x() > node.getP().x()) {
				return get(node.getRt(), p, XFlag);
			}
			else if ((p.x() == node.getP().x()) && (p.y() != node.getP().y())) {
				return get(node.getRt(), p, XFlag);
			}
//			else if ((p.x() == node.getP().x()) && (p.y() == node.getP().y())) 
			else 
			{
				return node;
			}
		}
		else {
			XFlag = true;
			if (p.y() < node.getP().y())
				return get(node.getLb(), p, XFlag);
			else if (p.y() > node.getP().y()) {
				return get(node.getRt(), p, XFlag);
			}
			else if ((p.y() == node.getP().y()) && (p.x() != node.getP().x())){
				return get(node.getRt(), p, XFlag);
			}
//			else if ((p.y() == node.getP().y()) && (p.x() == node.getP().x())) 
			else 
			{
				return node;
			}
		}
	}
	
	public void draw()
	{
		draw(root, true);
	}
	
	private void draw(Node node, boolean XFlag) 
	{
		if (node == null) return;
		if (XFlag)
		{
			XFlag = false;
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.setPenRadius(0.01);
			node.getP().draw();
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.setPenRadius();
			StdDraw.line(node.getP().x(), node.getYMin(), node.getP().x(), node.getYMax());
			draw(node.getLb(), XFlag);
			draw(node.getRt(), XFlag);
		}
		else {
			XFlag = true;
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.setPenRadius(0.01);
			node.getP().draw();
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.setPenRadius();
			StdDraw.line(node.getXMin(), node.getP().y(), node.getXMax(), node.getP().y());
			draw(node.getLb(), XFlag);
			draw(node.getRt(), XFlag);
		}
	}
	
	public Iterable<Point2D> range(RectHV rect)
	{
		argumentTest(rect);
		range = new SET<>();
		if (size == 0)
		{
			return range;
		}
			
		range(root, rect, true);
		return range;
	}
	
	private void range(Node node, RectHV rect, boolean XFlag)
	{
		if (node == null) return;
		
		if ((node.getP().x() >= rect.xmin()) && (node.getP().x() <= rect.xmax()) &&
				(node.getP().y() >= rect.ymin()) && (node.getP().y() <= rect.ymax()))
		{
			range.add(node.getP());
		}
		if (XFlag)
		{
			XFlag = !XFlag;
			if ((rect.xmin() < node.getP().x()))
			{
//				XFlag = false;
				range(node.getLb(), rect, XFlag);
			}
			if ((rect.xmax() >= node.getP().x()))
			{
//				XFlag = false;
				range(node.getRt(), rect, XFlag);
			}
		}
		else 
		{
			XFlag = !XFlag;
			if ((rect.ymin() < node.getP().y()))
			{
//				XFlag = true;
				range(node.getLb(), rect, XFlag);
			}
			if ((rect.ymax() >= node.getP().y()))
			{
//				XFlag = true;
				range(node.getRt(), rect, XFlag);
			}
		}

	}
	
	public Point2D nearest(Point2D p)
	{
		argumentTest(p);
		
		nearestDistance = Double.POSITIVE_INFINITY;
		nearest(root, true, p);
		return this.nearestPoint;
//		return null;
	}
	
	private void nearest(Node node, boolean XFlag, Point2D p) 
	{
		if (node == null) return;
		if (nearestDistance == 0.0) return;
		Point2D point = node.getP();
		double distance = point.distanceSquaredTo(p);
		if (distance == 0.0)
		{
			nearestPoint = point;
			nearestDistance = 0.0;
			return;
		}
		
		if (distance < nearestDistance)
		{
			nearestDistance = distance;
			nearestPoint = point;
		}
		
		
		// add in 2016.12.01
		/*****************************************************/
		if (node.lb != null) {
			if (minDistanceToRect(node.lb.rect, p) < nearestDistance)
			{
				nearest(node.lb, !XFlag, p);
				if ((node.rt != null) && (minDistanceToRect(node.rt.rect, p) < nearestDistance)) {
					nearest(node.rt, !XFlag, p);
				}
			}
			else {
				if (node.rt == null)
				{
					if (minDistanceToRect(node.lb.rect, p) < nearestDistance)
					{nearest(node.lb, !XFlag, p);}
				}
				else if ((node.rt.rect.contains(p))) {
					nearest(node.rt, !XFlag, p);
					if (minDistanceToRect(node.lb.rect, p) < nearestDistance)
					{nearest(node.lb, !XFlag, p);}
				}
				else {
					if (node.XFlag == true)
					{
						if (minDistanceToRect(node.lb.rect, p) < nearestDistance)
						{nearest(node.lb, !XFlag, p);}
						if (minDistanceToRect(node.rt.rect, p) < nearestDistance)
						{nearest(node.rt, !XFlag, p);}						
					}
					else {
						if (minDistanceToRect(node.rt.rect, p) < nearestDistance)
						{nearest(node.rt, !XFlag, p);}
						if (minDistanceToRect(node.lb.rect, p) < nearestDistance)
						{nearest(node.lb, !XFlag, p);}
					}
				}
			}
		}
		else if (node.rt != null) {
			if (minDistanceToRect(node.rt.rect, p) < nearestDistance)
			{nearest(node.rt, !XFlag, p);}
		}
		/*****************************************************/

	}
	
	private double minDistanceToRect(RectHV rect, Point2D point)
	{
        double dx = 0.0, dy = 0.0;
        double x = point.x();
        double y = point.y();
        double xmin = rect.xmin();
        double xmax = rect.xmax();
        double ymin = rect.ymin();
        double ymax = rect.ymax();
        if      (x < xmin) dx = x - xmin;
        else if (x > xmax) dx = x - xmax;
        if      (y < ymin) dy = y - ymin;
        else if (y > ymax) dy = y - ymax;
        
        return dx*dx + dy*dy;
	}
	
	private void argumentTest(Object obj) {
		if (obj == null)
			throw new NullPointerException("The Argument is NULL!");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private static class Node
	{
		private Point2D p;
		private RectHV rect;
		private Node lb;
		private Node rt;
		private boolean XFlag;
		
		/**
		 * 
		 */
		public Node(Point2D p, RectHV rect) {
			// TODO Auto-generated constructor stub
			this.p = p;
			this.rect = rect;
			lb = null;
			rt = null;
		}
		
		public Node(Point2D p, RectHV rect, boolean XFlag) {
			// TODO Auto-generated constructor stub
			this.p = p;
			this.rect = rect;
			lb = null;
			rt = null;
			this.XFlag = XFlag;
		}
		
		public Point2D getP()
		{
			return this.p;
		}

		public RectHV getRect() {
			return rect;
		}

		public void setRect(RectHV rect) {
			this.rect = rect;
		}

		public Node getLb() {
			return lb;
		}

		public void setLb(Node lb) {
			this.lb = lb;
		}

		public Node getRt() {
			return rt;
		}

		public void setRt(Node rt) {
			this.rt = rt;
		}
		
		public double getXMin() {
			return this.rect.xmin();
		}
		
		public double getYMin() {
			return this.rect.ymin();
		}
		
		public double getXMax() {
			return this.rect.xmax();
		}
		
		public double getYMax() {
			return this.rect.ymax();
		}
	}
}
