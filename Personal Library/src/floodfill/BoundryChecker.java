package floodfill;

import java.awt.geom.Point2D;

public abstract class BoundryChecker {
	public final boolean is1Parameter;
	
	protected BoundryChecker() {
		int methods = 0;
		try {
			getClass().getDeclaredMethod("isBoundaryType" , Tile.class);
			methods++;
		}
		catch(IllegalArgumentException | NoSuchMethodException | SecurityException e1) {
		}
		try {
			getClass().getDeclaredMethod("isBoundaryType" , Tile.class , Integer.TYPE , Integer.TYPE);
			methods += 2;
		}
		catch(IllegalArgumentException | NoSuchMethodException | SecurityException e) {
		}
		if(methods != 1 && methods != 2) {
			throw new IllegalArgumentException("Boundry Checkers must have ONLY one isBoundaryType method");
		}
		is1Parameter = methods == 1;
	}
	
	/**
	 * Check if point is in bounds
	 * 
	 * @param point - point coordinate
	 */
	public final boolean isInBounds(Point2D point) {
		return isInBounds((int) point.getX() , (int) point.getY());
	}
	
	/**
	 * Check if location is in bounds
	 * 
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public abstract boolean isInBounds(int x , int y);
	
	/**
	 * Check if point is a boundary defining type
	 * 
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public boolean isBoundaryType(Tile tile) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Check if point is a boundary defining type
	 * 
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @param dx - delta x coordinate (x + dx is coordinate to check)
	 * @param dy - delta y coordinate (y + dy is coordinate to check)
	 */
	public boolean isBoundaryType(Tile tile , int dx , int dy) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
}
