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
import java.util.Iterator;

import strategy.common.PlayerColor;
import strategy.game.common.Piece;
import strategy.game.common.PieceType;

/** A standard validator for validating moves
 * 
 * @author Dabrowski
 * @version $Revision: 1.0 $
 */
public class StandardMoveSpecialCaseValidator implements
		IMoveSpecialCaseValidator {

	/* (non-Javadoc)
	 * @see strategy.game.version.common.validation.IMoveSpecialCaseValidator#verifyDestination(strategy.common.PlayerColor, strategy.game.common.Piece)
	 */
	@Override
	public String verifyDestination(PlayerColor currentTurn, Piece atTo) {
		// Standard error passing procedure
		String invalidities = "";

		// Make sure choke points and friendlies are not attacked
		if ( atTo != null && (atTo.getOwner() == currentTurn	|| atTo.getOwner() == null)){
			invalidities+= "you cannot attack your own pieces or choke points, ";
		}

		return invalidities;
	}

	/* (non-Javadoc)
	 * @see strategy.game.version.common.validation.IMoveSpecialCaseValidator#verifyStartLocation(strategy.common.PlayerColor, strategy.game.common.Piece)
	 */
	@Override
	public String verifyStartLocation(PlayerColor currentTurn, Piece atFrom, PieceType piece) {
		// Standard error passing procedure
		String invalidities = "";
		if (atFrom == null) invalidities+= "no piece found at 'from', "; // can't move nothing
		else if (atFrom.getOwner() != currentTurn) invalidities+= String.valueOf(currentTurn) + " can't move that, ";
		else if (atFrom.getType() != piece) invalidities+= "wrong piece at 'from'";  // can't move the wrong piece

		return invalidities;
	}

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
