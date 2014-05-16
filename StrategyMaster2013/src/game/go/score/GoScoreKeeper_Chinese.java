/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game.go.score;

import game.GameController;
import game.common.PieceLocationDescriptor;

import java.util.Collection;

import common.PlayerColor;

/** Observer used to keep score for a game of Go. Keeps track of 
 *  when both players pass to calculate the final score at the end 
 *  of the game. 
 * 
 *  Multiple instances may be initialized and registered, but the 
 *  instance used by an AIRunner should not be shared at all to 
 *  protect the score of the game. 
 * 
 * 
 * Chinese version!
 * 
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public class GoScoreKeeper_Chinese extends AbstractGoScoreKeeper {

	public GoScoreKeeper_Chinese(GameController goGame){
		super(goGame);
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

	/** Finds and returns a collection of dead pieces
	 * 
	 * @return a collection of dead pieces
	 */
	protected Collection<PieceLocationDescriptor> findDeadPieces(){
		return null;
	}
}
