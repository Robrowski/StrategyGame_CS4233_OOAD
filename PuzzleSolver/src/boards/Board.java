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
	
	@Override
	public boolean placeAt(Piece p, int x, int y){
		if (this.getPiece(x,y) != null){
			return false;
		}
		this.b[x][y] = p;		
		return true;
	}
	
	@Override
	public Piece getPiece(int x, int y){
		return this.b[x][y];
	}

	@Override
	public Piece remove(int x, int y) {
		Piece p = this.getPiece(x, y);
		this.b[x][y] = null;
		return p;
	}

}
