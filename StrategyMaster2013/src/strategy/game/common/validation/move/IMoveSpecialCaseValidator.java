/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package strategy.game.common.validation.move;

import java.util.Collection;

import strategy.common.PlayerColor;
import strategy.game.common.Piece;
import strategy.game.common.PieceType;

/** This interface should be implemented by validators that 
 *  check for special cases that may be involved with a 
 *  potential move. 
 * 
 *  Special cases include:
 *  	- don't attack choke points
 *  	- don't attack allies
 *  	- don't move pieces that aren't yours :P
 *  	- don't say one piece then try to move another 
 *  
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public interface IMoveSpecialCaseValidator {

	/** Verify that the destination is valid in terms of what piece
	 *  may or may not be located there 
	 * 
	 * @param currentTurn the current player's turn
	 * @param atTo the piece at the destination (could be null)
	
	 * @return a string containing any error messages that may have arisen */
	String verifyDestination(PlayerColor currentTurn, Piece atTo);

	/** Verify that the piece at the start location is allowed to be moved
	 * 
	 * @param currentTurn the current player's turn
	 * @param atFrom    the piece at the starting location (could be null)
	 * @param piece The type of the piece that should atFrom
	
	 * @return a string containing any error messages that may have arisen */
	String verifyStartLocation(PlayerColor currentTurn, Piece atFrom, PieceType piece);

	
	/** Verify a move path. Mainly used when pieces can move more than 1 space or
	 *  in a non-standard direction
	 *  
	 * @param contentsOfPath any pieces on the path
	 * @return a string containing any error messages that may have arisen */
	String verifyMovePath(Collection<Piece> contentsOfPath);
	
	
	
	
}
