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

import java.util.Collection;

import common.StrategyException;

/**
 * @author Dabrowski
 *
 */
public class GoControllerTestDouble extends GoController {

	/**
	 * @param boardSize
	 * @throws StrategyException
	 */
	public GoControllerTestDouble(int boardSize) throws StrategyException {
		super(boardSize);
	}

	
	/** Sets the current field configuration to the given config in
	 *  the form of a board manager
	 * 
	 * @param toSetTo the board manager to set to 
	 */
	public void setBoardConfiguration(IBoardManager toSetTo){
		board = toSetTo;
	}
	
	/** Sets the current field configuration to the given config in
	 *  the form of a board manager
	 * 
	 * @param toSetTo collection of pieces to put in the board manager
	 * @throws StrategyException 
	 */
	public void setBoardConfiguration(Collection<PieceLocationDescriptor> toSetTo) throws StrategyException{
		// Make a new manager
		IBoardManager newManager = new GoBoard(this.boardSize);
		// Add the collection to the manager
		newManager.addToConfiguration(toSetTo);
		// Sets the field configuration		
		setBoardConfiguration(newManager);
	}
	
	
	
}
