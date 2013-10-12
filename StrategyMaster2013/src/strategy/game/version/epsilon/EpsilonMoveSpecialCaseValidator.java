/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package strategy.game.version.epsilon;

import java.util.Collection;
import java.util.Iterator;

import strategy.game.common.Piece;
import strategy.game.common.PieceType;
import strategy.game.common.validation.move.StandardMoveSpecialCaseValidator;

/** This is the move special case validator for the game of Epsilon Strategy
 * 
 * 
 * @author Dabrowski
 * @version $Revision: 1.0 $
 */
public class EpsilonMoveSpecialCaseValidator extends StandardMoveSpecialCaseValidator {

	/* (non-Javadoc)
	 * @see strategy.game.common.validation.move.IMoveSpecialCaseValidator#verifyMovePath(strategy.game.common.Piece, java.util.Collection)
	 */
	@Override
	public String verifyMovePath(Collection<Piece> contentsOfPath) {
		// Have an error string ready
		String errors = "";

		// Only do work if the move is more than one space
		if (contentsOfPath.size() > 1){
			// Time to iterate
			final Iterator<Piece> pieces = contentsOfPath.iterator();

			// Check first entry for a Scout, continue if so
			Piece first = pieces.next();
			if (first.getType() == PieceType.SCOUT ){
				while (pieces.hasNext()){
					first = pieces.next();
					errors += "scouts can't jump " + first.getType() +"'s, ";	
				}
			}
		}
		return errors;
	}
}
