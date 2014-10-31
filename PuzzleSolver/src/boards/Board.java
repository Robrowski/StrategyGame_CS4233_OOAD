package boards;

import puzzle.Piece;

/** A representation of a board, intended to be a final configuration
 * 
 * @author rpdabrowski
 *
 */
public class Board implements IBoard {
	public int height, width;
	Piece[][] b; //[x][y], ( 0,0 ) = bottom left = origin
	
	
	public Board(int width, int height){
		this.height = height;
		this.width = width;
		this.b = new Piece[width][height]; 
	}
	
	/** Places the piece at the given location. Returns the success
	 * 
	 * @param p
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean placeAt(Piece p, int x, int y){
		if (this.getPiece(x,y) != null){
			return false;
		}
		this.b[x][y] = p;		
		return true;
	}
	
	/** Gets the piece at the given location
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Piece getPiece(int x, int y){
		return this.b[x][y];
	}

}
