/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game.strategy.gamma;

import game.common.PieceType;
import game.common.pieceStats.IPieceMoves;
import game.common.pieceStats.IPiecePowers;

/** A class containing information about pieces featured first in the 
 *  Gamma version of Strategy
 * @author Dabrowski            
 * @version $Revision: 1.0 $
 */
public class GammaPieceStats implements IPiecePowers, IPieceMoves{ 
	

	/* (non-Javadoc)
	 * @see game.strategy.common.IPieceInfo#getPower(game.common.PieceType)
	 */
	public int getPower(PieceType type) {
		if (type.equals(PieceType.FLAG)) return 1;
		if (type.equals(PieceType.MARSHAL)) return 12;
		if (type.equals(PieceType.COLONEL)) return 10;
		if (type.equals(PieceType.CAPTAIN)) return 8;
		if (type.equals(PieceType.LIEUTENANT)) return 7;
		if (type.equals(PieceType.SERGEANT)) return 6;
		return 0;
	}

	/* (non-Javadoc)
	 * @see game.strategy.common.IPieceInfo#getMovementCapability(game.common.PieceType)
	 */
	public int getMovementCapability(PieceType type) {
		if (type.equals(PieceType.FLAG)) return 0;
		if (type.equals(PieceType.MARSHAL)) return 1;
		if (type.equals(PieceType.COLONEL)) return 1;
		if (type.equals(PieceType.CAPTAIN)) return 1;
		if (type.equals(PieceType.LIEUTENANT)) return 1;
		if (type.equals(PieceType.SERGEANT)) return 1;
		return 0;
	}

	/* (non-Javadoc)
	 * @see game.common.pieceStats.IPiecePowers#getAtk(game.common.PieceType)
	 */
	@Override
	public int getAtk(PieceType type) {
		return getPower(type);
	}

	/* (non-Javadoc)
	 * @see game.common.pieceStats.IPiecePowers#getDef(game.common.PieceType)
	 */
	@Override
	public int getDef(PieceType type) {
		return getPower(type);
	}
}
