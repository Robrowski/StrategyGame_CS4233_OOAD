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

import game.common.PieceLocationDescriptor;
import game.common.board.IBoardManager;
import game.common.score.AbstractScoreKeeper;
import game.common.turnResult.ITurnResult;

import java.util.Collection;

import common.PlayerColor;
import common.StrategyException;

/** Observer used to keep score for a game of Go
 * 
 * 
 * @author Dabrowski
 *
 */
public class GoScoreKeeper extends AbstractScoreKeeper {

	/** Basic constructor. Sets up data structures
	 * 
	 */
	GoScoreKeeper() {
		super();
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



	/* (non-Javadoc)
	 * @see game.common.score.IScoreKeeper#CalculateFinalScore(game.common.board.IBoardManager)
	 */
	@Override
	public void CalculateFinalScore(IBoardManager finalConfiguration) {
		// Doesn't have to be used
	}
	
	
	/* (non-Javadoc)
	 * @see common.observer.GameObserver#notifyPlacement(game.common.turnResult.ITurnResult, common.StrategyException)
	 */
	@Override
	public void notifyPlacement(ITurnResult result, StrategyException fault) {
		if (result != null &&     !result.getPiecesRemoved().isEmpty()){
			PlayerColor losingPieces = result.getPiecesRemoved().iterator().next().getPiece().getOwner();
			
			// Give points to the other player based on the number of pieces captured
			switch (losingPieces){
			default: // shhhhh
			case BLACK:
				addToScore(PlayerColor.WHITE, result.getPiecesRemoved().size());
				break;
			case WHITE:
				addToScore(PlayerColor.BLACK, result.getPiecesRemoved().size());
				break;
			}
			
			
		}
	}
	

	
	
}
