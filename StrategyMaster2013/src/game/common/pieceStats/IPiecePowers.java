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

/** The PiecePowers interface must be extended by any class trying to provide 
 *  piece powers to any class that asks.
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public interface IPiecePowers {
	
	/** Get the atk power of the associated piece type
	 * @param type the type of the piece. 	
	 * @return the atk power as an int. Returns 0 if the piece type is not recognized or does not have a power */
	int getAtk(PieceType type);

	/** Get the def power of the associated piece type
	 * @param type the type of the piece. 	
	 * @return the def power as an int. Returns 0 if the piece type is not recognized or does not have a power */
	int getDef(PieceType type);
	
}
