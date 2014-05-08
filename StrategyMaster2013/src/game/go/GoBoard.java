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

import java.util.Iterator;
import java.util.LinkedList;

import game.common.Coordinate;
import game.common.Location;
import game.common.Location2D;
import game.common.Piece;
import game.common.PieceLocationDescriptor;
import game.common.board.AbstractBoardManager;
import game.common.turnResult.ITurnResult;
import game.common.turnResult.MoveResult;
import game.common.turnResult.MoveResultStatus;
import common.PlayerColor;
import common.StrategyException;

/** Implementation of a Go board of variable size
 * 
 * 
 * @author Dabrowski
 *
 */
public class GoBoard extends AbstractBoardManager {

	/** Square, odd number sides for board size */
	private int boardSize; 

	/** theBoard[x][y]  = piece  */
	protected final Piece[][] theBoard; 

	/** Creates a Go board. Throws exception for invalid input
	 * @param sideLength length of one side of a square Go board
	 * @throws StrategyException for invalid input
	 */
	public GoBoard(int sideLength) throws StrategyException {
		// Check that the board is bigger than 5x5 and is odd length
		if (sideLength < 5 || sideLength % 2 == 0){
			throw new StrategyException("Invalid board size");
		}

		boardSize = sideLength;

		// Initialize the board
		theBoard = new Piece[boardSize][boardSize];		
		for (int x = 0; x < boardSize; x++ ){
			for (int y = 0; y < boardSize; y++){
				theBoard[x][y] = null;
			}
		}
	}


	/* (non-Javadoc)
	 * @see game.common.board.IBoardManager#placePiece(game.common.Piece, game.common.Location)
	 */
	@Override
	public ITurnResult placePiece(Piece piece, Location l) throws StrategyException {
		// Make sure the space is empty, then place
		if (getPieceAt(l) == null){
			theBoard[l.getCoordinate(Coordinate.X_COORDINATE)][l.getCoordinate(Coordinate.Y_COORDINATE)] = piece;
		} else {
			throw new StrategyException("Cannot place pieces on top of other pieces.");
		}


		// Check for captures
		LinkedList<PieceLocationDescriptor> piecesRemoved = new LinkedList<PieceLocationDescriptor>();

		// Basic Capture in corner
		try {
			Location2D L00 = new Location2D(0,0);
			if (this.getPieceAt(new Location2D(1,0)).getOwner() == PlayerColor.BLACK &
					this.getPieceAt(new Location2D(0,1)).getOwner() == PlayerColor.BLACK &
					this.getPieceAt(new Location2D(0,0)).getOwner() == PlayerColor.WHITE){
				piecesRemoved.add(new PieceLocationDescriptor(this.getPieceAt(L00), L00 ));
			} 
		} catch (NullPointerException npe){
			// meh
		}

		// Remove the pieces
		Iterator<PieceLocationDescriptor> toRemove = piecesRemoved.iterator();
		while (toRemove.hasNext()){
			this.removePiece(toRemove.next().getLocation());
		}

		// Return!
		return new MoveResult(MoveResultStatus.OK, new PieceLocationDescriptor(piece,l), piecesRemoved );
	}


	/** Removes the piece on the board at the given location
	 * 
	 * @param l location of item to set to null
	 */
	private void removePiece(Location l){
		theBoard[l.getCoordinate(Coordinate.X_COORDINATE)][l.getCoordinate(Coordinate.Y_COORDINATE)] = null;
	}




	/* (non-Javadoc)
	 * @see game.common.board.IBoardManager#getPieceAt(game.common.Location)
	 */
	@Override
	public Piece getPieceAt(Location l) {
		return theBoard[l.getCoordinate(Coordinate.X_COORDINATE)][l.getCoordinate(Coordinate.Y_COORDINATE)];
	}
}
