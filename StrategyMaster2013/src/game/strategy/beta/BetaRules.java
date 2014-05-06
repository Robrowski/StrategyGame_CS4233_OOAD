/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game.strategy.beta;

import game.common.PieceType;

/**
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public class BetaRules {
	
	/** Get the power of the associated piece type
	 * @param type the type of the piece. Limited options in Beta
	
	 * @return the power as an int. Returns 0 if the piece type is not recognized */
	public static int getPower(PieceType type) {
		if (type.equals(PieceType.FLAG)) return 1;
		if (type.equals(PieceType.MARSHAL)) return 12;
		if (type.equals(PieceType.COLONEL)) return 10;
		if (type.equals(PieceType.CAPTAIN)) return 8;
		if (type.equals(PieceType.LIEUTENANT)) return 7;
		if (type.equals(PieceType.SERGEANT)) return 6;
		return 0;
	}

}
