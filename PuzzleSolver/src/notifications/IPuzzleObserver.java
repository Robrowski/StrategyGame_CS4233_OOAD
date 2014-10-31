package notifications;

import puzzle.Piece;

/**
 * 
 */

/**
 * @author rpdabrowski
 *
 */
public interface IPuzzleObserver {

	/** Notifies the observer about the placement of a piece. Assumed to be final
	 * 
	 * @param p the piece
	 * @param x x location (origin left)
	 * @param y y location (origin bottom)
	 */
	public void notifyPlacement(Piece p, int x, int y);
	
	/** Notifies the observer about the attempted placement of a piece. Assumed to be temporary
	 * 
	 * @param p the piece
	 * @param x x location (origin left)
	 * @param y y location (origin bottom)
	 */
	public void notifyAttemptedPlacement(Piece p, int x, int y);
	
	
	
	
}
