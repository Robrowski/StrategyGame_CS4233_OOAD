/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package strategy.game.version.delta;

import strategy.game.common.PieceType;
import strategy.game.common.pieceStats.IPiecePowers;

/** This class is responsible for providing the powers of
 *  the pieces used in Delta Strategy
 * 
 * @author Dabrowski            
 * @version $Revision: 1.0 $
 */
public class DeltaPiecePowers implements IPiecePowers{ 


	/* (non-Javadoc)
	 * @see strategy.game.version.common.IPieceInfo#getPower(strategy.game.common.PieceType)
	 */
	public int getPower(PieceType type) {
		if (type == null) return 0;
		if (type.equals(PieceType.FLAG)) return 0;
		if (type.equals(PieceType.MARSHAL)) return 12;
		if (type.equals(PieceType.COLONEL)) return 10;
		if (type.equals(PieceType.CAPTAIN)) return 8;
		if (type.equals(PieceType.LIEUTENANT)) return 7;
		if (type.equals(PieceType.SERGEANT)) return 6;
		if (type.equals(PieceType.GENERAL)) return 11;
		if (type.equals(PieceType.MINER)) return 5;
		if (type.equals(PieceType.MAJOR)) return 9;
		if (type.equals(PieceType.SPY)) return 3;
		if (type.equals(PieceType.SCOUT)) return 4;
		if (type.equals(PieceType.BOMB)) return 0;
		else return 0;
	}


}
