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
import game.common.PieceLocationDescriptor;
import game.common.score.AbstractScoreKeeper;
import game.common.turnResult.ITurnResult;
import game.common.turnResult.MoveResultStatus;

import java.util.Collection;

import common.PlayerColor;
import common.StrategyException;
import common.StrategyRuntimeException;

/** Observer used to keep score for a game of Go. Keeps track of 
 *  when both players pass to calculate the final score at the end 
 *  of the game. 
 * 
 *  Multiple instances may be initialized and registered, but the 
 *  instance used by an AIRunner should not be shared at all to 
 *  protect the score of the game. 
 * 
 * 
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public class GoScoreKeeper extends AbstractScoreKeeper {

	/** Keep track of "passes" */
	protected boolean previousMoveWasPass = false;	
	/** Reference to the game */
	protected GameController goGame;

	/** The number of captures each side makes */
	protected int whiteCaptures = 0;
	protected int blackCaptures = 0;
	
	
	/** basic constructor that takes a reference to the game being observed 
	 * 
	 * @param goGame the game being observed
	 */
	GoScoreKeeper(GameController goGame){
		super();
		this.goGame = goGame;
	}
	
	
	/* (non-Javadoc)
	 * @see common.observer.GameObserver#gameStart(java.util.Collection, java.util.Collection)
	 */
	@Override
	public void gameStart(Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration) {
		// Initialize scores
		updateScore(PlayerColor.BLACK, 0);
		updateScore(PlayerColor.WHITE, 0);
	}



	/** For this implementation, this function may be called at any time, but is intended for
	 *  use by an AI runner only. This function will be called automatically if the end of the
	 *  game is detected. 
	 *  
	 *  This function can be called multiple times and will be accurate after moves are made
	 *  
	 * @see game.common.score.IScoreKeeper#CalculateFinalScore()
	 */
	@Override
	public void CalculateFinalScore() {
		// Doesn't have to be used
		int blackFinalScore = 0;
		int whiteFinalScore = 0;
		
		// Calculate the score
		
		
		
		// Update the scores
		updateScore(PlayerColor.BLACK, blackCaptures + blackFinalScore);
		updateScore(PlayerColor.WHITE, whiteCaptures + whiteFinalScore);
	}


	/* (non-Javadoc)
	 * @see common.observer.GameObserver#notifyPlacement(game.common.turnResult.ITurnResult, common.StrategyException)
	 */
	@Override
	public void notifyPlacement(ITurnResult result, StrategyException fault) {
		if (result != null &&     !result.getPiecesRemoved().isEmpty()){
			final PlayerColor losingPieces = result.getPiecesRemoved().iterator().next().getPiece().getOwner();

			// Give points to the other player based on the number of pieces captured
			switch (losingPieces){
			case BLACK:
				addToScore(PlayerColor.WHITE, result.getPiecesRemoved().size());
				whiteCaptures += result.getPiecesRemoved().size();
				break;
			case WHITE:
				blackCaptures += result.getPiecesRemoved().size();
				addToScore(PlayerColor.BLACK, result.getPiecesRemoved().size());
				break;
			default:  // no-op
				throw new StrategyRuntimeException(losingPieces.toString() + " isn't playing...");			
			}
		}
		
		// Check if the game is over
		if (result != null && result.getStatus() == MoveResultStatus.PASS){
			// IF GAME OVER
			if (previousMoveWasPass){
				this.CalculateFinalScore();	
			}
			
			previousMoveWasPass = true;
		}	
	}
}
