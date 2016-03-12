package frc.imageprocessing;

public class Point {
	private double x , y;
	
	public Point(double x , double y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(Point point) {
		this(point.x , point.y);
	}
	
	public Point(int[] point) {
		this(point[0] , point[1]);
	}
	
	public Point(double[] point) {
		this(point[0] , point[1]);
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double distance(Point point2) {
		return Math.sqrt(Math.pow(x - point2.x , 2) + Math.pow(y - point2.y , 2));
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Point))
			return false;
		Point p = (Point) obj;
		return p.x == x && p.y == y;
	}
	
	@Override
	public String toString() {
		return String.format("%.2f\t%.2f" , x , y);
	}
}
