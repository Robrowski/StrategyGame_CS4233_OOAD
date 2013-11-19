/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package strategy.game.version.gamma;

import strategy.game.common.PieceType;
import strategy.game.common.pieceStats.IPieceMoves;
import strategy.game.common.pieceStats.IPiecePowers;

/** A class containing information about pieces featured first in the 
 *  Gamma version of Strategy
 * @author Dabrowski            
 * @version $Revision: 1.0 $
 */
public class GammaPieceStats implements IPiecePowers, IPieceMoves{ 
	

	/* (non-Javadoc)
	 * @see strategy.game.version.common.IPieceInfo#getPower(strategy.game.common.PieceType)
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
	 * @see strategy.game.version.common.IPieceInfo#getMovementCapability(strategy.game.common.PieceType)
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
	 * @see strategy.game.common.pieceStats.IPiecePowers#getAtk(strategy.game.common.PieceType)
	 */
	@Override
	public int getAtk(PieceType type) {
		return getPower(type);
	}

	/* (non-Javadoc)
	 * @see strategy.game.common.pieceStats.IPiecePowers#getDef(strategy.game.common.PieceType)
	 */
	@Override
	public int getDef(PieceType type) {
		return getPower(type);
	}
}
