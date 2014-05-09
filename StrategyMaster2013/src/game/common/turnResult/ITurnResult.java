/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game.common.turnResult;

import java.util.Collection;

import game.common.PieceLocationDescriptor;

/**
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public interface ITurnResult {

	/** Returns the winner of a battle, if there was one
	 * 
	 * @return winner of battle
	 */
	PieceLocationDescriptor getBattleWinner();
	
	/** Gives the state of the game: Ex DRAW
	 * 
	 * @return state of game
	 */
	MoveResultStatus getStatus();
	
	
	
	/** Gets a collection of pieces to remove from the board if any were 
	 *  removed. Null otherwise
	 * 
	 * @return collection of pieces to remove
	 */
	Collection<PieceLocationDescriptor> getPiecesRemoved();
	
	
}
