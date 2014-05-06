/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game.common.pieceStats;

import game.common.PieceType;

/** The PieceMove interface must be extended by any class trying to provide 
 *  piece movement capabilities to any class that asks.
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public interface IPieceMoves {
	
	/** Get the number of spaces a piece may move in a straight line
	 * @param type the type of the piece. 	
	 * @return the the number of spaces a piece may move in a straight line */
	int getMovementCapability(PieceType type);
	
	
}
