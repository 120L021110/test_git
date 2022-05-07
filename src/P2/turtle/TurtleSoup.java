/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.List;
import java.util.Set;
import java.util.Stack;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
    	turtle.forward(sideLength);
    	turtle.turn(90.0);
    	turtle.forward(sideLength);
    	turtle.turn(90.0);
    	turtle.forward(sideLength);
    	turtle.turn(90.0);
    	turtle.forward(sideLength);
    	turtle.turn(90.0);
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        return (sides-2)*180.0/sides;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
    	double sides = (360.0/(180.0-angle));
    	if(sides - (int)(sides) > 0.5) return (int) (sides+1);
    	else return (int) (sides);
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
    	double degree = calculateRegularPolygonAngle(sides);
    	for(int i = 0; i < sides; i++)
    	{
    		turtle.forward(sideLength);
        	turtle.turn(degree);
    	}
    	
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {
        double degree1 = 0.0, degree2;
        if(currentBearing < 270.0 && currentBearing >= 0.0) degree1 = (90.0 - currentBearing) * Math.PI /180.0;
        else if (currentBearing < 360.0 && currentBearing >= 270.0) degree1 = (450.0 - currentBearing) * Math.PI /180.0;
        degree2 = Math.atan2(targetY - currentY, targetX - currentX);
        
        if(degree1 >= degree2) return (degree1 - degree2) * 180 / Math.PI;
        else return (2 * Math.PI - degree2 + degree1) * 180 / Math.PI;
        
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
    	int size = xCoords.size();
    	double currentBearing = 0.0;
		List<Double> list = new ArrayList<>();
		
    	for(int i = 0; i < size-1; i++)
    	{
    		list.add(calculateBearingToPoint(currentBearing, xCoords.get(i), yCoords.get(i), xCoords.get(i+1), yCoords.get(i+1)));
    		currentBearing = (currentBearing + list.get(i)) % 360; 
    	}
    	return list;
    }
    
    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points) {
    	Set<Point> BeginPoints = new HashSet<Point>();
    	Set<Point> FinalPoints = new HashSet<Point>();
    	List<Point> TempPoints2 = new ArrayList<>();
    	
    	BeginPoints.addAll(points);
    	int Number = BeginPoints.size();
    	
    	if(Number <= 3)//���ڻ�ֻ�����������ֱ�ӷ���
    		return BeginPoints;
    	else
    	{
    		Iterator<Point> it1 = BeginPoints.iterator();
    		Point yLowest = it1.next();
			TempPoints2.add(yLowest);
    		Point TempPoint = null;
    		while(it1.hasNext())//ȡ��������С�ĵ�
    		{
    			TempPoint = it1.next();
    			TempPoints2.add(TempPoint);
    			if(TempPoint.y() < yLowest.y())
    				yLowest = TempPoint;
    		}
    		
    		List<Point> TempPoints1 = new ArrayList<>();
    		double[] alpha = new double[Number];//��ŷ���
    		double[] distance = new double[Number];//��ž���
    		int[] index = new int[Number];//�������������
    		for (int i = 0; i < Number ; i++)//�����е������ƽ�ƣ�ʹ����������С�ĵ�Ϊԭ��
    		{
    			TempPoint = new Point(TempPoints2.get(i).x() - yLowest.x(), TempPoints2.get(i).y() - yLowest.y());
    			
    			TempPoints1.add(TempPoint);
    		}
    		
    		for (int i = 0; i < Number ; i++)//�������alpha����ԭ�����distance
    		{
    			distance[i] = Math.pow(Math.pow(TempPoints1.get(i).x(),2)+Math.pow(TempPoints1.get(i).y(),2),0.5);
    			alpha[i] = Math.atan2(TempPoints1.get(i).y(), TempPoints1.get(i).x());
    			index[i] = i;
    		}
    		
    		for (int i = 0; i < Number - 1 ; i++)//����С����˳������
    		{
    			for(int j = 0; j < Number - i - 1; j++)
    			{
    				if(alpha[j] > alpha[j+1])
    				{
    					int tempIndex = index[j];
    					index[j] = index[j+1];
    					index[j+1] = tempIndex;
    					
    					double tempdegree = alpha[j];
    					alpha[j] = alpha [j+1];
    					alpha[j+1] = tempdegree;
    				}
    				else if(alpha[j] == alpha[j+1])
    				{
    					if(distance[j] > distance[j+1])//������ȣ��Ѿ������������ǰ��
    					{
    						int tempIndex = index[j];
        					index[j] = index[j+1];
        					index[j+1] = tempIndex;
        					
        					double tempdegree = alpha[j];
        					alpha[j] = alpha [j+1];
        					alpha[j+1] = tempdegree;
    					}
    				}
    			}
    		}//�õ�����alpha��С�������е�˳�򣬴����index[Number]��
    		
    		//��ʼȷ��͹���ĸ�����
    		Stack<Integer> stack = new Stack<>();
    		Stack<Double> DegreeStack = new Stack<>();
    		double beta, CurrentBearing;
    		
    		CurrentBearing = alpha[1];//��ʼ����
    		stack.push(index[0]);//ǰ��������ջ
    		stack.push(index[1]);
    		for (int i = 2; i < Number ; i++)
    		{
    			TempPoint = TempPoints1.get(index[i]);//ȡ���ж�ջ��Ҫ�õ���һ����
    			//��ջ������Ϊԭ�㣬������һ����ķ���
    			beta =  Math.atan2(TempPoint.y() -  TempPoints1.get(stack.peek()).y(), TempPoint.x() -  TempPoints1.get(stack.peek()).x());
    			if(beta < 0) beta = 360.0 + beta / Math.PI *180;//�޸ķ��Ǵ�СΪ����
    			
    			if(beta < CurrentBearing)//��һ�������Ҳ�
    				{
    					stack.pop();//��ջ������͹���ڣ���ջ
    					//���µ�ջ����Ϊԭ�㣬������һ����ķ���
    					beta = Math.atan2(TempPoint.y() -  TempPoints1.get(stack.peek()).y(), TempPoint.x() -  TempPoints1.get(stack.peek()).x());
    					while(beta < DegreeStack.peek())//��һ���㻹���Ҳ�
    					{
    						stack.pop();//����ջ���Բ���͹���ڣ���ջ
    						DegreeStack.pop();//�޸ĽǶ�ջջ��
    						//���µ�ջ����Ϊԭ�㣬������һ����ķ���
        					beta = Math.atan2(TempPoint.y() -  TempPoints1.get(stack.peek()).y(), TempPoint.x() -  TempPoints1.get(stack.peek()).x());
    					}
    					stack.push(index[i]);//���жϵ���ջ
        				CurrentBearing = beta;//�޸��µķ���
    				}	
    			else if(beta > CurrentBearing) //��һ���������
    				{
    					stack.push(index[i]);//���жϵ���ջ
    					DegreeStack.push(CurrentBearing);//���з��Ǳ�Ϊ��һ������
    					CurrentBearing = beta;//�޸��µķ���
    				
    				}
    			else //��һ������ֱ����
    				{
    					stack.pop();//ȡ��ֱ���ϵĵ�
    					stack.push(index[i]);//���жϵ���ջ
    				}
    		}
    		while(stack.empty() == false)//ȡ��ջ�����еĵ�
    		{
    			FinalPoints.add(TempPoints2.get(stack.pop()));
    		}
    		return FinalPoints;
    	}
    }
    
    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
    	for(int i = 0;i < 50; i++)
    	{
    		for(PenColor color:PenColor.values())
    		{
    			turtle.color(color);
    			turtle.forward(30);
       	        turtle.turn(300.0);
       	        turtle.forward(30);
       	        turtle.turn(120.0);
       	       	turtle.forward(30);
       	        turtle.turn(300.0);
       	        turtle.forward(30);
        			
               	turtle.turn(120.0);
               	
               	turtle.forward(30);
               	turtle.turn(300.0);
               	turtle.forward(30);
        	    turtle.turn(120.0);
        	    turtle.forward(30);
        	    turtle.turn(300.0);
        	    turtle.forward(30);
        	        
                turtle.turn(300.0);
    		}
    		
    	}
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();
        drawPersonalArt(turtle);
        // draw the window
        turtle.draw();
    }

}
