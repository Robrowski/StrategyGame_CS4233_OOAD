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

import game.common.Location;
import game.common.PieceLocationDescriptor;
import game.common.PieceType;
import game.common.board.IBoardManager;
import game.common.turnResult.ITurnResult;

import java.util.Collection;
import java.util.HashMap;


import common.PlayerColor;
import common.StrategyException;
import common.StrategyRuntimeException;

/** Generic score keeper made into abstract class. Most methods not implemented
 * 
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public abstract class AbstractScoreKeeper implements IScoreKeeper {

	/** A hashmap holding the scores of all the players */
	private final HashMap<PlayerColor, Integer> scores;
	
	/** Basic constructor that puts a new hash map in to hold the scores
	 * 
	 */
	protected AbstractScoreKeeper(){
		scores = new HashMap<PlayerColor, Integer>();	
	}
	

	/** Write the player's new score
	 * 
	 * @param player the player
	 * @param toUpdate the new score
	 */
	protected void updateScore(PlayerColor player, int toUpdate){
		scores.put(player, toUpdate);
	}
	
	/** Add to the player's score
	 * 
	 * @param player the player
	 * @param toAdd points to add
	 */
	protected void addToScore(PlayerColor player, int toAdd ){
		final int oldScore = this.getPlayerScore(player);
		
		updateScore(player, toAdd + oldScore);		
	}
	
	/* (non-Javadoc)
	 * @see game.common.score.IScoreKeeper#getPlayerScore(common.PlayerColor)
	 */
	@Override
	public int getPlayerScore(PlayerColor player) {
		if (!scores.containsKey(player)){
			throw new StrategyRuntimeException(player.toString() + " isn't playing...");			
		}
		
		return scores.get(player);	
	}
	
	/* (non-Javadoc)
	 * @see common.observer.GameObserver#moveHappened(game.common.PieceType, game.common.Location, game.common.Location, game.common.turnResult.ITurnResult, common.StrategyException)
	 */
	@Override
	public void moveHappened(PieceType piece, Location from, Location to,
			ITurnResult result, StrategyException fault) {
		// Doesn't have to be used
	}

	/* (non-Javadoc)
	 * @see common.observer.GameObserver#notifyPlacement(game.common.turnResult.ITurnResult, common.StrategyException)
	 */
	@Override
	public void notifyPlacement(ITurnResult result, StrategyException fault) {
		// Doesn't have to be used
	}





	/* (non-Javadoc)
	 * @see common.observer.GameObserver#gameStart(java.util.Collection, java.util.Collection)
	 */
	@Override
	public void gameStart(Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration) {
		// Doesn't have to be used
	}



	/* (non-Javadoc)
	 * @see game.common.score.IScoreKeeper#CalculateFinalScore(game.common.board.IBoardManager)
	 */
	@Override
	public void CalculateFinalScore(IBoardManager finalConfiguration) {
		// Doesn't have to be used
	}

}
