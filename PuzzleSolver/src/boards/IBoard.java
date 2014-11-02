package boards;

import puzzle.Piece;

public interface IBoard {

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
	
	/** Remove the piece at the given location
	 * 
	 * @param x
	 * @param y
	 * @return true for success, false otherwise
	 */
	public boolean remove(int x, int y);
}
