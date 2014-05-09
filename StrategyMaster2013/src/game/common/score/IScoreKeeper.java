/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game.common.score;

import game.common.board.IBoardManager;
import common.PlayerColor;
import common.observer.GameObserver;

/** Interface for classes used to keep score of games
 * 
 * 
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public interface IScoreKeeper extends GameObserver{

	/** Gets the current score for the given player
	 * 
	 * @param player the player
	
	 * @return an integer score */
	int getPlayerScore(PlayerColor player);
	

	/** Using the final configuration on the board, calculate the 
	 *  final score.
	 * 
	 * @param finalConfiguration the final configuration
	 */
	void CalculateFinalScore(IBoardManager finalConfiguration);
	
		
}
