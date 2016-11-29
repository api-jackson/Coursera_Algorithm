import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints
{
    private int segNum = -1;
    private Node head;

    public BruteCollinearPoints(Point[] points)
    {

    	if (segNum != -1) return;
    	
        if (points == null)
            throw new java.lang.NullPointerException("The argument POINTS can not be null!");
        
        Point[] temp = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new java.lang.NullPointerException("The element of the argument POINTS can not be null!");
            
            for (int j = 0; j < i; j++)
            	if (points[i].equals(points[j]))
            		throw new IllegalArgumentException("The point "+points[i].toString()+" is "+i+", "+j+" is duplicated!");
            
            temp[i] = points[i];
        }
        
        Arrays.sort(temp);
        segNum = 0;
        head = null;
        Node tail = null;

        if(points.length < 4)
        {
        	segNum = 0;
        	return;
        }
		for (int i = 0; i < temp.length; i++) {
			for (int j = i + 1; j < temp.length; j++) {
				for (int k = j + 1; k < temp.length; k++) {
					double slopeIJ = temp[i].slopeTo(temp[j]);
					double slopeIK = temp[i].slopeTo(temp[k]);
					if (slopeIJ == slopeIK) {
						for (int l = k + 1; l < temp.length; l++) {
							double slopeIL = temp[i].slopeTo(temp[l]);
							if (slopeIJ == slopeIL) {
								if (head == null) {
									head = new Node();
									head.line = new LineSegment(temp[i], temp[l]);
									tail = head;
								} else {
									Node node = new Node();
									node.line = new LineSegment(temp[i], temp[l]);
									tail.next = node;
									tail = node;
								}
								segNum++;
							}
						}
					}
				}
			}
		}  
    }
    
    public int numberOfSegments()
    {
        return segNum;
    }
    
    public LineSegment[] segments()
    {
        Node node = head;
        LineSegment[] lineSeg;
        lineSeg = new LineSegment[segNum];
        for (int i = 0; i < segNum; i++)
        {
        	lineSeg[i] = node.line;
        	node = node.next;
        }
    	
        return lineSeg;
    }
    
    private class Node
    {
    	LineSegment line;
    	Node next = null;
    }
    
    public static void main(String[] args)
    {
    	// read the N points from a file
    	In in = new In(args[0]);
    	int N = in.readInt();
    	Point[] points = new Point[N];
    	
    	for (int i = 0; i < N; i++){
    		int x = in.readInt();
    		int y = in.readInt();
    		points[i] = new Point(x, y);
    	}
    	
    	// draw the points
    	StdDraw.show();
    	StdDraw.setXscale(0, 32768);
    	StdDraw.setYscale(0, 32768);
    	for (Point point : points)
    		point.draw();
    	StdDraw.show();
    	
    	// print and draw the line segments
    	BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    	for (LineSegment segment : collinear.segments())
    	{
    		StdOut.println(segment);
    		segment.draw();
    	}
    			
    }
    
}
