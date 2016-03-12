package frc.imageprocessing;

import java.awt.Graphics;

public class Vector {
	private Point	point1 , point2;
	private double	dx , dy;
	
	public Vector(Point p1 , Point p2) {
		this(p1.getX() , p1.getY() , p2.getX() , p2.getY());
	}
	
	public Vector(double x , double y) {
		this(x , y , 0 , 0);
	}
	
	public Vector(double x1 , double y1 , double x2 , double y2) {
		this.point1 = new Point(x1 , y1);
		this.point2 = new Point(x2 , y2);
		dx = x1 - x2;
		dy = y1 - y2;
	}
	
	public double getLength() {
		return point1.distance(point2);
	}
	
	public double getAngleRadian(Vector vector) {
		return angleBetween(this , vector);
	}
	
	public double getAngleDegree(Vector vector) {
		return getAngleRadian(vector) * 180 / Math.PI;
	}
	
	public double dot(Vector v) {
		return dx * v.dx + dy * v.dy;
	}
	
	public double det(Vector v) {
		return dx * v.dy - dy * v.dx;
	}
	
	public Point getMiddle() {
		return new Point((point1.getX() + point2.getX()) / 2 , (point1.getY() + point2.getY()) / 2);
	}
	
	public void draw(Graphics g) {
		g.drawLine((int) point1.getX() , (int) point1.getY() , (int) point2.getX() , (int) point2.getY());
	}
	
	public double getMagnitude() {
		return Math.sqrt(Math.pow(dx , 2) + Math.pow(dy , 2));
	}
	
	@Override
	public String toString() {
		return String.format("<%.2f,%.2f>" , dx , dy);
	}
	
	public static boolean equalMagnitude(Vector one , double mag) {
		return equalMagnitude(one , mag , 0);
	}
	
	public static boolean equalMagnitude(Vector one , double mag , final double LENGTH_TOLERANCE) {
		return Math.abs(one.getMagnitude() - mag) <= LENGTH_TOLERANCE;
	}
	
	public static boolean equalMagnitude(Vector one , Vector two , final double LENGTH_TOLERANCE) {
		return equalMagnitude(one , two.getMagnitude() , LENGTH_TOLERANCE);
	}
	
	public static boolean equalMagnitude(Vector one , Vector two) {
		return equalMagnitude(one , two , 0);
	}
	
	public static boolean equalAngleDegree(Vector one , Vector two , double angle , final double ANGLE_TOLERANCE) {
		return Math.abs(angleBetween(one , two)) - angle * Math.PI / 180 <= ANGLE_TOLERANCE;
	}
	
	public static boolean equalAngleRadian(Vector one , Vector two , double angle , final double ANGLE_TOLERANCE) {
		return Math.abs(angleBetween(one , two)) - angle <= ANGLE_TOLERANCE;
	}
	
	public static boolean equalAngleDegree(Vector one , Vector two , double angle) {
		return equalAngleDegree(one , two , angle , 0);
	}
	
	public static boolean equalAngleRadian(Vector one , Vector two , double angle) {
		return equalAngleRadian(one , two , angle , 0);
	}
	
	public static boolean equalAngleDegree(Vector one , Vector two , Vector three , Vector four) {
		return equalAngleDegree(one , two , three , four , 0);
	}
	
	public static boolean equalAngleDegree(Vector one , Vector two , Vector three , Vector four , final double ANGLE_TOLERANCE) {
		return equalAngleDegree(one , two , three.getAngleDegree(four) , ANGLE_TOLERANCE);
	}
	
	public static boolean equalAngleRadian(Vector one , Vector two , Vector three , Vector four) {
		return equalAngleRadian(one , two , three , four , 0);
	}
	
	public static boolean equalAngleRadian(Vector one , Vector two , Vector three , Vector four , final double ANGLE_TOLERANCE) {
		return equalAngleDegree(one , two , three.getAngleRadian(four) , ANGLE_TOLERANCE);
	}
	
	public static double angleBetween(Vector v1 , Vector v2) {
		double a = Math.atan2(v2.dy , v2.dx) - Math.atan2(v1.dy , v1.dx);
		return a > 180 ? 360 - a : a;
	}
}
