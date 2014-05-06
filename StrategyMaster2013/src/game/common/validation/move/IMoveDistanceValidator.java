/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game.common.validation.move;

import game.common.Location;
import game.common.Piece;

/** This interface should be implemented by any class wishing to validate
 *  the move distance of a given piece. The Piece itself is requested
 *  instead of the PieceType because future implementations of Piece
 *  may contain additional information that effect the movement
 *  capabilities of the piece. 
 * 
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public interface IMoveDistanceValidator {
	/** Determines whether the move from to to is a valid distance
	 * 
	 * @param from the place where the piece started
	 * @param to   the place where the piece might finish
	 * @param piece The piece in question
	
	 * @return any errors that indicate the move may or may not have been invalid */
	String moveDistanceIsValid(Location from, Location to, Piece piece);
	
	
	
	
}