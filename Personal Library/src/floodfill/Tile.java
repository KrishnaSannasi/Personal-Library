package floodfill;

import java.awt.Point;

public final class Tile extends Point {
	public final int	x , y;
	Tile					owner;
	State					state;
	
	public Tile(int x , int y) {
		this(State.NONE , x , y);
	}
	
	public Tile(State state , int x , int y) {
		this.state = state;
		this.x = x;
		this.y = y;
		super.x = x;
		super.y = y;
	}
	
	public Tile(Tile tile) {
		this(tile.state , tile.x , tile.y);
		this.owner = tile.owner;
	}
	
	public enum State {
		/**
		 * <code> Tile.State.FILLED </code>signifies that this tile has been
		 * reached and is created after the use of
		 * <code> {@link Tile.State#EDGE} </code>
		 * 
		 * @see Tile.State#EDGE
		 * @see Tile.State#NONE
		 */
		FILLED ,
		/**
		 * <code> Tile.State.EDGE </code>signifies that this tile has been reached
		 * at the last tick and will create
		 * <code> {@link Tile.State#FILLED} </code> on adjacent tiles that are
		 * <code> {@link Tile.State#NONE} </code>
		 * 
		 * @see Tile.State#FILLED
		 * @see Tile.State#NONE
		 */
		EDGE ,
		/**
		 * <code> Tile.State.NONE </code>signifies that this tile has not been
		 * reached can be converted to <code> {@link Tile.State#EDGE} </code> if
		 * it is next to <code> {@link Tile.State#EDGE} </code>
		 * 
		 * @see Tile.State#FILLED
		 * @see Tile.State#EDGE
		 */
		NONE;
	}
	
	@Override
	public double getX() {
		return x;
	}
	
	@Override
	public double getY() {
		return y;
	}
	
	public Tile getOwner() {
		return owner;
	}
	
	public State getState() {
		return state;
	}
	
	@Override
	public String toString() {
		String p = String.format("[%d,%d]" , x , y);
		p = "";
		switch(state) {
			case FILLED:
				return "O" + p;
			case EDGE:
				return "." + p;
			case NONE:
				return "-" + p;
			default:
				return null;
		}
	}
}
