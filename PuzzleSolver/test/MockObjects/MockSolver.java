/**
 * 
 */
package MockObjects;

import java.util.Collection;

import puzzle.Connection;
import puzzle.Piece;
import boards.Board;
import solver.Solver;

/**
 * @author rpdabrowski
 *
 */
public class MockSolver extends Solver {

	public MockSolver(Board b, Collection<Piece> pieces) {
		super(b, pieces);
	}

	/** Steal access to the protected method
	 * 
	 * overridden to be public
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @return
	 */
	@Override
	public Piece getPieceToFit(Connection a, Connection b,Connection c,Connection d){
		return super.getPieceToFit(a, b, c, d);
	}


}
