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
import java.util.HashSet;
import java.util.Iterator;

import game.GameController;
import game.common.Location;
import game.common.Piece;
import game.common.PieceLocationDescriptor;
import game.common.PieceType;
import game.common.board.IBoardManager;
import game.common.turnResult.ITurnResult;
import game.common.turnResult.MoveResult;
import game.common.turnResult.MoveResultStatus;
import common.PlayerColor;
import common.StrategyException;
import common.StrategyRuntimeException;
import common.observer.GameObservable;
import common.observer.GameObserver;

/** A controller for Go, the Chinese game of strategy and skill.
 * 
 * 
 * @author Dabrowski
 * @author Catt Mosti
 * @version $Revision: 1.0 $
 */
public class GoController implements GameController, GameObservable {


	/** Boolean flag for whether the game has started or not */
	protected boolean gameStarted = false;
	/** Boolean flag for whether the game is over or not */
	protected boolean gameOver = false;
	/** The current turn the next move is expected by. Black goes first */
	protected PlayerColor currentTurn = PlayerColor.BLACK;
	/** Keep track of "passes" */
	protected boolean previousMoveWasPass = false;		
	/** THE BOARD */
	protected IBoardManager board;
	/** Board size */
	protected int boardSize;
	/** The objects observer this observable */
	private final Collection<GameObserver> observers = new HashSet<GameObserver>();

	/** Makes a Go controller
	 * @param boardSize the size of the board
	 * @throws StrategyException
	 */
	public GoController(int boardSize) throws StrategyException {
		this.boardSize = boardSize;
		board = new GoBoard(boardSize);
	}

	/* (non-Javadoc)
	 * @see game.GameController#placePiece(game.common.Piece, game.common.Location)
	 */
	@Override
	public ITurnResult placePiece(Piece piece, Location at) throws StrategyException {
		try {
			// Check game states to see if a piece can even be played by this player

			if (!gameStarted) throw new StrategyException("Cannot place pieces before the game has started.");
			if (gameOver) throw new StrategyException("Cannot place pieces after the game has ended.");
			if (currentTurn != piece.getOwner()) throw new StrategyException("Cannot place pieces during the other player's turn.");

			// Place it
			ITurnResult result;
			if (piece.getType() == PieceType.PASS){
				if (previousMoveWasPass){
					gameOver = true;
					result =  new MoveResult(MoveResultStatus.PASS, null);
				} else {
					previousMoveWasPass = true;
					result = new MoveResult(MoveResultStatus.OK, null);
				}
			} else {
				result = board.placePiece(piece, at);
				previousMoveWasPass = false;
			}

			// Set up next turn
			if (currentTurn == PlayerColor.BLACK){
				currentTurn = PlayerColor.WHITE;
			} else {
				currentTurn = PlayerColor.BLACK;
			}

			notifyPlacement(result, null);
			return result;	
		}catch (StrategyException se){
			notifyPlacement(null, se);
			throw se;
		}
	}

	/* (non-Javadoc)
	 * @see game.GameController#startGame()
	 */
	@Override
	public void startGame() throws StrategyException {
		if (gameStarted) throw new StrategyException("A game is already started!");
		gameStarted = true;
		gameOver = false;
		currentTurn = PlayerColor.BLACK;
		notifyGameStart();
	}

	/* (non-Javadoc)
	 * @see game.GameController#move(game.common.PieceType, game.common.Location, game.common.Location)
	 */
	@Override
	public ITurnResult move(PieceType piece, Location from, Location to)
			throws StrategyException {
		throw new StrategyException("HAHA not implemented.");	
	}

	/* (non-Javadoc)
	 * @see game.GameController#getPieceAt(game.common.Location)
	 */
	@Override
	public Piece getPieceAt(Location location) {
		return board.getPieceAt(location);
	}


	/* (non-Javadoc)
	 * @see common.observer.GameObservable#register(common.observer.GameObserver)
	 */
	@Override
	public void register(GameObserver observer) {
		observers.add(observer);
	}

	/* (non-Javadoc)
	 * @see common.observer.GameObservable#unregister(common.observer.GameObserver)
	 */
	@Override
	public void unregister(GameObserver observer) {
		if (!observers.remove(observer)){
			throw new StrategyRuntimeException("That observer was never registered - USE THE SAME INSTANCE");
		}		
	}


	/** Notifies the observers that the game has started  */
	private void notifyGameStart() {
		final Iterator<GameObserver> obsIter = observers.iterator();
		while (obsIter.hasNext()){
			obsIter.next().gameStart(new HashSet<PieceLocationDescriptor>(), new HashSet<PieceLocationDescriptor>());		
		}
	}

	/** Notifies the observers that the game has started  */
	private void notifyPlacement(ITurnResult res, StrategyException fault) {
		final Iterator<GameObserver> obsIter = observers.iterator();
		while (obsIter.hasNext()){
			obsIter.next().notifyPlacement(res, fault);		
		}
	}
}
