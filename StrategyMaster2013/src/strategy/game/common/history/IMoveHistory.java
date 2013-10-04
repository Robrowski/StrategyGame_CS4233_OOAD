/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package strategy.game.common.history;

import strategy.common.StrategyException;
import strategy.game.common.DetailedMoveResult;

/** This interface should be implemented by any class wishing to store 
 *  moves for a StrategyGameController. The two main responsibilities of 
 *  these classes are to store moves and throw exceptions when the move 
 *  repetition rule (or other history rules) have been violated.
 * 
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public interface IMoveHistory {

	/** Store a move or throw an exception because that move broke a  history rule
	 *  
	 * @param theMove the move to store
	
	 * @throws StrategyException when the move repetition rule (or other) is violated */
	void recordMove(DetailedMoveResult theMove) throws StrategyException;

	
}
