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

import strategy.common.StrategyRuntimeException;
import strategy.game.common.Coordinate;
import strategy.game.common.Location;
import strategy.game.common.Location2D;
import strategy.game.common.Piece;
import strategy.game.common.pieceStats.IPieceMoves;

/** This class is responsible for validating the movements of pieces with
 *  respect to distance only.
 * 
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public class StandardMoveDistanceValidator implements IMoveDistanceValidator {
	
	/** Units in the x direction */
	private final int fieldDimX;
	/** Units in the y direction */
	private final int fieldDimY;
	/** Can provide the movement capabilities of pieces for the given version of strategy */
	private final IPieceMoves pieceMovements;

	/** Basic constructor that takes the field dimensions
	 * 
	 * @param fieldDimX units in the x direction
	 * @param fieldDimY units in the y direction
	 * @param pieceMovements an object used to provide movement capabilities of piecs
	 */
	public StandardMoveDistanceValidator(int fieldDimX, int fieldDimY, IPieceMoves pieceMovements){
		this.fieldDimX = fieldDimX;
		this.fieldDimY = fieldDimY;	
		this.pieceMovements = pieceMovements;	
	}
	
	
	/* (non-Javadoc)
	 * @see strategy.game.version.common.validation.IMoveDistanceValidator#moveDistanceIsValid(strategy.game.common.Location2D, strategy.game.common.Location2D, strategy.game.common.Piece)
	 */
	@Override
	public String moveDistanceIsValid(Location from, Location to, Piece piece) {
		// Get the travel distance
		final int distance;
		try {
			distance = convertLocationTo2D(from).distanceTo(convertLocationTo2D(to));
		} catch (StrategyRuntimeException sre){
			// with Location2D, this particular exception being thrown indicates some sort 
			// of diagonality
			return "that move isn't even close to adjacent, ";
		}

		// A tally of the problems- in the form of an error message string
		String invalidities = "";
		if (distance > pieceMovements.getMovementCapability(piece.getType())){
			invalidities += String.valueOf(piece.getType()) + " cannot move that far, ";
		}
		
		// Check that the move is in bounds  
		final int toX = to.getCoordinate(Coordinate.X_COORDINATE);
		final int toY = to.getCoordinate(Coordinate.Y_COORDINATE);
		if (toX >= fieldDimX || toX < 0) invalidities+= "toX out of bounds, ";
		if (toY >= fieldDimY || toY < 0) invalidities+= "toY out of bounds, ";

		return invalidities;
	}
	
	/** Converts a location to Location2D. Throws a runtime exception if the given
	 *  location is not usable for such purposes. TRUSTS THE INTERFACE to force the
	 *  method "getCoordinate" to throw an exception when it can't perform its duties
	 * 
	 * @param location to convert
	 * @return a converted location
	 */
	static private Location2D convertLocationTo2D(Location location){
		// Check that the given location is usable - A runtime exception is thrown if not
		return new Location2D(location.getCoordinate(Coordinate.X_COORDINATE), 
								location.getCoordinate(Coordinate.Y_COORDINATE));
	}

}
