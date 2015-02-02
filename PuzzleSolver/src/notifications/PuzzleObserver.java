package notifications;

import puzzle.Piece;

/**
 * @author rpdabrowski
 *
 */
public interface PuzzleObserver {

	/**
	 * Notifies the observer about the placement of a piece. Assumed to be final
	 * 
	 * @param p
	 *            the piece
	 * @param x
	 *            x location (origin left)
	 * @param y
	 *            y location (origin bottom)
	 */
	public void notifyPlacement(Piece p, int x, int y);

	/**
	 * Notifies the observer about the attempted placement of a piece. Assumed
	 * to be temporary
	 * 
	 * @param p
	 *            the piece
	 * @param x
	 *            x location (origin left)
	 * @param y
	 *            y location (origin bottom)
	 */
	public void notifyAttemptedPlacement(Piece p, int x, int y);

	/**
	 * Notifies the remove of the piece at the given location. Doesn't care if
	 * there was something there or not
	 * 
	 * @param x
	 * @param y
	 */
	public void notifyRemove(int x, int y);

	/**
	 * Notifies all that a status has been updated
	 * 
	 * @param id
	 */
	public void notifyStatusUpdate(String id);

}
