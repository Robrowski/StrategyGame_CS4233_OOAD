/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game.strategy.delta;

import game.common.PieceType;
import game.common.pieceStats.IPieceMoves;

/** The movement capabilities of the pieces used in Delta Strategy
 * 
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public class DeltaPieceMoves implements IPieceMoves {

	/* (non-Javadoc)
	 * @see game.strategy.common.IPieceInfo#getMovementCapability(game.common.PieceType)
	 */
	public int getMovementCapability(PieceType type) {
		if (type == null) return 0;
		if (type.equals(PieceType.FLAG)) return 0;
		if (type.equals(PieceType.MARSHAL)) return 1;
		if (type.equals(PieceType.COLONEL)) return 1;
		if (type.equals(PieceType.CAPTAIN)) return 1;
		if (type.equals(PieceType.LIEUTENANT)) return 1;
		if (type.equals(PieceType.SERGEANT)) return 1;
		if (type.equals(PieceType.GENERAL)) return 1;
		if (type.equals(PieceType.MINER)) return 1;
		if (type.equals(PieceType.MAJOR)) return 1;
		if (type.equals(PieceType.SPY)) return 1;
		if (type.equals(PieceType.SCOUT)) return 10;
		if (type.equals(PieceType.BOMB)) return 0;
		else return 0;
	}

}
