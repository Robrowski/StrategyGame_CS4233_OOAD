/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package strategy.game.common;

import java.util.Collection;

import strategy.common.StrategyException;

/** Any class that wishes to observer another should implement this
 * 
 * @author Dabrowski
 * @version $Revision: 1.0 $
 */
public interface StrategyGameObserver {

	/** Called at the beginning of the game with the initial
	 * configurations.
	 * 
	 * @param redConfiguration the configuration for red
	 * @param blueConfiguration the configuration for blue
	 */
	void gameStart(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration);

	/** Called whenever a move is made by the game controller. If
	 * the controller caught an exception, it returns null for the
	 * result, but the exception in the fault; otherwise, fault
	 * is null.
	 * 
	 * @param piece piece that moved
	 * @param from  where it started
	 * @param to    where its going
	 * @param result the result 
	 * @param fault  any faults 
	 */
	void moveHappened(PieceType piece, Location from, Location to,
			MoveResult result, StrategyException fault);

}
