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

import game.common.Location;
import game.common.Location2D;
import game.common.validation.configuration.location.AbstractPieceLocationValidator;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import common.PlayerColor;


/** Responsible for taking some basic information about a game of Strategy, then
 *  takes player configurations 
 * 
 * @author Dabrowski
 * @version $Revision: 1.0 $
 */
public class GammaPieceLocationValidator  extends AbstractPieceLocationValidator  {

	/** General constructor to take the number of players, as well as the X and Y 
	 *  dimensions of the field. This constructor is here because the existence
	 *  of these parameters is not a variable point, and thus should be extended
	 *  for common functionality. 
	 *  
	 *  This was made using the Strategy Pattern
	 * 
	 * @param numberOfPlayers The number of players in the game
	 * @param fieldXDim The number of units the field goes on the X axis
	 * @param fieldYDim The number of units the field goes on the Y axis 
	 */
	public GammaPieceLocationValidator(int numberOfPlayers, int fieldXDim,	int fieldYDim) {
		super(numberOfPlayers, fieldXDim, fieldYDim);
	}

	/* (non-Javadoc)
	 * @see game.strategy.common.validation.configuration.location.AbstractPieceLocationValidator#isPlayerColorExpected(common.PlayerColor)
	 */
	@Override
	protected String isPlayerColorExpected(PlayerColor givenPlayer) {
		return ""; // null string because this doesn't actually have to be implemented
	}

	/* (non-Javadoc)
	 * @see game.strategy.common.validation.configuration.location.AbstractPieceLocationValidator#generateValidStartingLocations(common.PlayerColor)
	 */
	@Override
	protected Collection<Location> generateValidStartingLocations(
			PlayerColor givenPlayer) {
		final List<Location> validLocations = new LinkedList<Location>();
		
		// Figure out the offset for Y. This offset sets the Y bounds of the starting location
		final int yOffset = (givenPlayer.equals(PlayerColor.RED)) ?  0 :  4;
		
		// Go through each possible location combination and make a location for it
		for (int x = 0; x < fieldXDim; x++){
			for (int y = yOffset; y <= yOffset + 1; y++){
				validLocations.add(new Location2D(x,y));
			}
		}		
		
		return validLocations;
	}




}
