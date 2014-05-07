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

import game.GameController;
import game.common.Location;
import game.common.MoveResult;
import game.common.Piece;
import game.common.PieceType;
import game.common.board.IBoardManager;
import common.PlayerColor;
import common.StrategyException;

/** A controller for Go, the Chinese game of strategy and skill.
 * 
 * 
 * @author Dabrowski
 * @author Catt Mosti
 */
public class GoController implements GameController {
	
	
	/** Boolean flag for whether the game has started or not */
	protected boolean gameStarted;
	/** Boolean flag for whether the game is over or not */
	protected boolean gameOver;
	/** The current turn the next move is expected by */
	protected PlayerColor currentTurn;
	/** THE BOARD */
	protected IBoardManager board;
	/** Board size */
	protected int boardSize;
	
	
	/** Makes a Go controller
	 * @param boardSize the size of the board
	 */
	public GoController(int boardSize) throws StrategyException {
		this.boardSize = boardSize;
		this.board = new GoBoard(boardSize);
	}

	/* (non-Javadoc)
	 * @see game.GameController#placePiece(game.common.Piece, game.common.Location)
	 */
	@Override
	public void placePiece(Piece piece, Location at) throws StrategyException {
		throw new StrategyException("HAHA not implemented.");	
	}

	/* (non-Javadoc)
	 * @see game.GameController#startGame()
	 */
	@Override
	public void startGame() throws StrategyException {
		if (gameStarted) throw new StrategyException("A game is already started!");
		gameStarted = true;
		gameOver = false;
	}

	/* (non-Javadoc)
	 * @see game.GameController#move(game.common.PieceType, game.common.Location, game.common.Location)
	 */
	@Override
	public MoveResult move(PieceType piece, Location from, Location to)
			throws StrategyException {
		throw new StrategyException("HAHA not implemented.");	
	}

	/* (non-Javadoc)
	 * @see game.GameController#getPieceAt(game.common.Location)
	 */
	@Override
	public Piece getPieceAt(Location location) {
		// TODO Auto-generated method stub
		return null;
	}


	
	
	
	
	
	

}
