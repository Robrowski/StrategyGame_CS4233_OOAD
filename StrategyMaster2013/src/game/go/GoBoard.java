/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game.go;

import java.util.Collection;

import common.StrategyException;
import game.common.DetailedMoveResult;
import game.common.Location;
import game.common.Piece;
import game.common.PieceLocationDescriptor;
import game.common.board.IBoardManager;

/** Implementation of a Go board of variable size
 * 
 * 
 * @author Dabrowski
 *
 */
public class GoBoard implements IBoardManager {

	// Square, odd number sides 
	@SuppressWarnings("unused")
	private int boardSize; 
	
	
	
	
	
	
	
	
	/** Creates a Go board. Throws exception for invalid input
	 * @param sideLength length of one side of a square Go board
	 * @throws StrategyException for invalid input
	 */
	public GoBoard(int sideLength) throws StrategyException {
		if (sideLength < 5 || sideLength % 2 == 0){
			throw new StrategyException("Invalid board size");
		}
		
		boardSize = sideLength;
	}

	/* (non-Javadoc)
	 * @see game.common.board.IBoardManager#addToConfiguration(java.util.Collection)
	 */
	@Override
	public void addToConfiguration(
			Collection<PieceLocationDescriptor> redConfiguration) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see game.common.board.IBoardManager#getPieceAt(game.common.Location)
	 */
	@Override
	public Piece getPieceAt(Location location) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see game.common.board.IBoardManager#updateField(game.common.DetailedMoveResult)
	 */
	@Override
	public DetailedMoveResult updateField(DetailedMoveResult theDMove) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see game.common.board.IBoardManager#getPiecesInPath(game.common.Location, game.common.Location)
	 */
	@Override
	public Collection<Piece> getPiecesInPath(Location from, Location to) {
		// TODO Auto-generated method stub
		return null;
	}

}
