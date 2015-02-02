package MockObjects;

import notifications.PuzzleObserver;
import puzzle.Piece;
import boards.Board;
import boards.IBoard;

public class MockObserver implements PuzzleObserver {

	IBoard b;
	
	public MockObserver(IBoard b){
		this.b = b;
	}
	
	@Override
	public void notifyPlacement(Piece p, int x, int y) {
		this.testNotifications(p, x, y);
	}

	@Override
	public void notifyAttemptedPlacement(Piece p, int x, int y) {
		this.testNotifications(p, x, y);
	}
	
	/** A set of tests to verify valid notifications
	 * 
	 * @param p
	 * @param x
	 * @param y
	 */
	private void testNotifications(Piece p, int x, int y){
		org.junit.Assert.assertNotNull(p);
		org.junit.Assert.assertEquals(x, x % ((Board) b).width);
		org.junit.Assert.assertEquals(y, y % ((Board) b).height);

	}

	@Override
	public void notifyRemove(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyStatusUpdate(String id) {
		// TODO Auto-generated method stub

	}
}
