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

import game.common.Coordinate;
import game.common.Location;
import game.common.Piece;
import game.common.board.AbstractBoardManager;

import common.StrategyException;

/** Implementation of a Go board of variable size
 * 
 * 
 * @author Dabrowski
 *
 */
public class GoBoard extends AbstractBoardManager {

	/** Square, odd number sides for board size */
	@SuppressWarnings("unused")
	private int boardSize; 
	
	/** theBoard[x][y]  = piece  */
	protected final Piece[][] theBoard; 
	
	/** Creates a Go board. Throws exception for invalid input
	 * @param sideLength length of one side of a square Go board
	 * @throws StrategyException for invalid input
	 */
	public GoBoard(int sideLength) throws StrategyException {
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
	public void placePiece(Piece piece, Location l) throws StrategyException {
		if (getPieceAt(l) == null){
			theBoard[l.getCoordinate(Coordinate.X_COORDINATE)][l.getCoordinate(Coordinate.Y_COORDINATE)] = piece;
		} else {
			throw new StrategyException("There is already a piece there :P");
		}
	}
	


	/* (non-Javadoc)
	 * @see game.common.board.IBoardManager#getPieceAt(game.common.Location)
	 */
	@Override
	public Piece getPieceAt(Location l) {
		return theBoard[l.getCoordinate(Coordinate.X_COORDINATE)][l.getCoordinate(Coordinate.Y_COORDINATE)];
	}
}
