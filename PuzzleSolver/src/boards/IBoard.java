package boards;

import puzzle.Piece;

public interface IBoard {
	int width = 0;

	/** Gets the piece at the given location
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Piece getPiece(int x, int y);
	
	/** Places the piece at the given location. Returns the success
	 * 
	 * @param p
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean placeAt(Piece p, int x, int y);
}
