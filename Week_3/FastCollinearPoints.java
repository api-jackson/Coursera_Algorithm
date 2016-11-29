import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

	private int segNum = -1;
	private Node head;
    
    public FastCollinearPoints(Point[] points) 
    {
        // TODO Auto-generated constructor stub
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
        
        
        for (int i = 0; i < temp.length; i++)
        {
        	Point[] tempPoint = new Point[temp.length-1];
        	Point min = null, max = null;
        	int collinearPoint = 0;
        	
        	// 1. copy other point except temp[i] into tempPoint
        	for (int j = 0, k = 0; j < temp.length;)
        	{
        		if (i != j) 
        			tempPoint[k++] = temp[j++];
        		else
        			j++;
        	}
        	
        	// 2. sort tempPoint according to point[i].comparator
        	Arrays.sort(tempPoint, temp[i].slopeOrder());
        	
        	// 3. find all collinear point of temp[i]         	
        	for (int k = 0; k < tempPoint.length-1; k++)
        	{
        		if (temp[i].slopeTo(tempPoint[k]) == temp[i].slopeTo(tempPoint[k+1]))
        		{
//        			System.out.println("count" + collinearPoint + ": " + temp[i] + " ------> " + tempPoint[k] + " ------> " + tempPoint[k+1]);
        			
        			if (min == null)
        			{
        				if (temp[i].compareTo(tempPoint[k]) < 0)
        				{
        					min = temp[i];
        					max = tempPoint[k];
        				}
        				else {
							min = tempPoint[k];
							max = temp[i];
						}
        				collinearPoint = 3;
        			}
        			else
        			{
        				if (tempPoint[k].compareTo(min) < 0)
        				{
        					min = tempPoint[k];
        				}
        				if (tempPoint[k+1].compareTo(min) < 0) {
							
						}
        				
        				if (tempPoint[k].compareTo(max) > 0)
        				{
        					max = tempPoint[k];
        				}
        				if (tempPoint[k+1].compareTo(max) > 0)
        				{
        					max = tempPoint[k+1];
        				}
        				
        				collinearPoint++;
        			}
        			
        			if ((collinearPoint >= 4) && (min.equals(temp[i])) && (k+1 == tempPoint.length-1))
        			{
//						System.out.println(new LineSegment(temp[i], tempPoint[k]));
        				
        				if (head == null)
        				{
        					head = new Node();
        					head.line = new LineSegment(temp[i], tempPoint[k+1]);
        					tail = head;
        					segNum = 1;

        				}
        				else {
							Node node = new Node();
							node.line = new LineSegment(temp[i], tempPoint[k+1]);
							tail.next = node;
							tail = node;
							segNum++;
						}
        				
    					collinearPoint = 0;
    					min = null;
    					max = null;
        			}
        		}
        		else {
					if ((collinearPoint >= 4) && min.equals(temp[i]))
					{
//						System.out.println(new LineSegment(temp[i], tempPoint[k]));
						
						if (head == null)
						{
							head = new Node();
							head.line = new LineSegment(temp[i], tempPoint[k]);
							tail = head;
							segNum = 1;
						}
						else {
							Node node = new Node();
							node.line = new LineSegment(temp[i], tempPoint[k]);
							tail.next = node;
							tail = node;
							segNum++;
						}
						
					}
					collinearPoint = 0;
					min = null;
					max = null;
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
//        System.out.println("segNum" + segNum);
        lineSeg = new LineSegment[segNum];
        for (int i = 0; i < segNum; i++)
        {
//        	System.out.println(node.line);
        	lineSeg[i] = node.line;
        	node = node.next;
        }
    	
        return lineSeg;
    }

    
    public static void main(String[] args) {
   
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
    	FastCollinearPoints collinear = new FastCollinearPoints(points);
    	for (LineSegment segment : collinear.segments())
    	{
    		StdOut.println(segment);
    		segment.draw();
    	}
    }
    
    private class Node
    {
    	LineSegment line;
    	Node next = null;
    }

}
